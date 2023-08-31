package ru.vladalexeco.playlistmaker.player.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseInteractor
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseInteractor
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.PlaylistTrackDatabaseInteractor
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import ru.vladalexeco.playlistmaker.player.presentation.state_classes.FavouriteTrackState
import ru.vladalexeco.playlistmaker.player.presentation.state_classes.PlaylistTrackState
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.*

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3

class PlayerViewModel(
    private val playerTrack: PlayerTrack,
    private val audioPlayerInteractor: AudioPlayerInteractor,
    private val audioPlayerDatabaseInteractor: AudioPlayerDatabaseInteractor,
    private val playlistMediaDatabaseInteractor: PlaylistMediaDatabaseInteractor,
    private val playlistTrackDatabaseInteractor: PlaylistTrackDatabaseInteractor,
    private val playlistDatabaseInteractor: PlaylistDatabaseInteractor
    ): ViewModel() {

    var allowToCleanTimer = true

    private val _checkIsTrackInPlaylist = MutableLiveData<PlaylistTrackState>()
    val checkIsTrackInPlaylist: LiveData<PlaylistTrackState> = _checkIsTrackInPlaylist

    private var _playlistsFromDatabase = MutableLiveData<List<Playlist>>()
    var playlistsFromDatabase: LiveData<List<Playlist>> = _playlistsFromDatabase

    private var isFavourite = false

    private var timerJob: Job? = null

    private val _playerTrackForRender = MutableLiveData<PlayerTrack>()
    val playerTrackForRender: LiveData<PlayerTrack> = _playerTrackForRender

    private val _favouriteTrack = MutableLiveData<FavouriteTrackState>()
    val favouriteTrack: LiveData<FavouriteTrackState> = _favouriteTrack

    private val _isCompleted = MutableLiveData(false)
    val isCompleted: LiveData<Boolean> = _isCompleted

    private val _playerState = MutableLiveData(STATE_DEFAULT)
    val playerState: LiveData<Int> = _playerState

    private val _formattedTime =  MutableLiveData("00:00")
    val formattedTime: LiveData<String> = _formattedTime

    fun assignValToPlayerTrackForRender() {
        val playerTrackTo = playerTrack.copy(
            artworkUrl = playerTrack.artworkUrl?.replaceAfterLast('/', "512x512bb.jpg"),
            releaseDate = playerTrack.releaseDate?.split("-", limit=2)?.get(0),
            trackTime = playerTrack.trackTime?.let { getTimeFormat(it.toLong()) }
        )

        _playerTrackForRender.postValue(playerTrackTo)
    }

    private fun play() {
        audioPlayerInteractor.play()
        _playerState.postValue(STATE_PLAYING)
        startTimer()
        _isCompleted.postValue(false)
        allowToCleanTimer = false
    }

    fun pause() {
        audioPlayerInteractor.pause()
        _playerState.postValue(STATE_PAUSED)
        timerJob?.cancel()
    }

    fun release() {
        audioPlayerInteractor.release()
        timerJob?.cancel()
    }

    fun playbackControl() {
        when(_playerState.value) {
            STATE_PLAYING -> pause()
            STATE_PAUSED, STATE_PREPARED -> play()
        }
    }

    fun preparePlayer() {
        audioPlayerInteractor.prepare(
            callbackPrep = {
                _playerState.postValue(STATE_PREPARED)
            },
            callbackComp = {
                _playerState.postValue(STATE_PREPARED)
                timerJob?.cancel()
                _isCompleted.postValue(true)
            }
        )
    }

    private fun startTimer() {
        timerJob = viewModelScope.launch {
            while (audioPlayerInteractor.isPlaying()) {
                delay(UPDATE_TIME_INFO_MS)
                _formattedTime.postValue(getTimeFormat(audioPlayerInteractor.getCurrentPos().toLong()))
            }
        }
    }

    private fun getTimeFormat(value: Long): String = SimpleDateFormat("mm:ss", Locale.getDefault()).format(value)

    fun checkEmptinessOrNull(text: String?): String {
        return if (!text.isNullOrEmpty()) text else "n/a"
    }

    fun checkTrackIsFavourite() {

        _favouriteTrack.postValue(
            FavouriteTrackState(
                isFavourite = null,
                isLoading = true
            )
        )

        viewModelScope.launch {
            audioPlayerDatabaseInteractor
                .getTracksIdFromDatabase()
                .collect { listOfIds ->
                    val trackIsFavourite = checkTrackId(listOfIds)

                    if (trackIsFavourite) {
                        assignValueToIsFavourite(true)
                        _favouriteTrack.postValue(
                            FavouriteTrackState(
                                isFavourite = true,
                                isLoading = false
                            )
                        )
                    } else {
                        assignValueToIsFavourite(false)
                        _favouriteTrack.postValue(
                            FavouriteTrackState(
                                isFavourite = false,
                                isLoading = false
                            )
                        )
                    }
                }
        }
    }

    suspend fun deletePlayerTrackFromFavourites() {
        audioPlayerDatabaseInteractor.deletePlayerTrackFromDatabase(playerTrack)
    }

    suspend fun addPlayerTrackToFavourites() {
        val insertionTimestamp = System.currentTimeMillis()
        audioPlayerDatabaseInteractor.addPlayerTrackToDatabase(playerTrack, insertionTimestamp)
    }

    private fun checkTrackId(listOfIds: List<Int>): Boolean {
        return listOfIds.contains(playerTrack.trackId)
    }

    fun assignValueToIsFavourite(value: Boolean) {
        isFavourite = value
    }

    fun checkValueFromIsFavourite(): Boolean = isFavourite

    fun getPlaylists() {

       viewModelScope.launch {
           playlistMediaDatabaseInteractor
               .getPlaylistsFromDatabase()
               .collect { listOfPlaylists ->
                   _playlistsFromDatabase.postValue(listOfPlaylists)
               }
       }

    }

    private fun insertTrackToDatabase(track: Track) {

        viewModelScope.launch {
            playlistTrackDatabaseInteractor.insertTrackToPlaylistTrackDatabase(track)
        }

    }

    private fun returnPlaylistToDatabase(playlist:Playlist) {
        viewModelScope.launch {
            playlistDatabaseInteractor.insertPlaylistToDatabase(playlist)
        }
    }

    private fun convertListToString(list: List<Int>): String {
        if (list.isEmpty()) return ""

        return list.joinToString(separator = ",")
    }

    private fun convertStringToList(string: String): ArrayList<Int> {
        if (string.isEmpty()) return ArrayList<Int>()

        return ArrayList<Int>(string.split(",").map { item -> item.toInt() })
    }

    fun checkAndAddTrackToPlaylist(playlist: Playlist, track: Track?) {
        val listIdOfPlaylistTracks: ArrayList<Int> = convertStringToList(playlist.listOfTracksId)

        if (!listIdOfPlaylistTracks.contains(track?.trackId)) {
            track?.let { listIdOfPlaylistTracks.add(it.trackId) }
            val listString = convertListToString(listIdOfPlaylistTracks)
            val modifiedPlaylist: Playlist = playlist.copy(listOfTracksId = listString, amountOfTracks = playlist.amountOfTracks + 1)
            returnPlaylistToDatabase(modifiedPlaylist)
            track?.let { insertTrackToDatabase(it) }

            _checkIsTrackInPlaylist.postValue(
                PlaylistTrackState(
                    nameOfPlaylist = playlist.name,
                    trackIsInPlaylist = false
                )
            )
        } else {

            _checkIsTrackInPlaylist.postValue(
                PlaylistTrackState(
                    nameOfPlaylist = playlist.name,
                    trackIsInPlaylist = true
                )
            )

        }

    }

    companion object {
        private const val UPDATE_TIME_INFO_MS = 300L
    }
}