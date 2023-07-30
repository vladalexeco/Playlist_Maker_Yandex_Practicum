package ru.vladalexeco.playlistmaker.player.presentation


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import java.text.SimpleDateFormat
import java.util.*

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3

class PlayerViewModel(private val playerTrack: PlayerTrack, private val audioPlayerInteractor: AudioPlayerInteractor): ViewModel() {

    private var timerJob: Job? = null

    private val _playerTrackForRender = MutableLiveData<PlayerTrack>()
    val playerTrackForRender: LiveData<PlayerTrack> = _playerTrackForRender

    init {
        preparePlayer()
        assignValToPlayerTrackForRender()
    }

    private val _isCompleted = MutableLiveData(false)
    val isCompleted: LiveData<Boolean> = _isCompleted

    private val _playerState = MutableLiveData(STATE_DEFAULT)
    val playerState: LiveData<Int> = _playerState

    private val _formattedTime =  MutableLiveData("00:00")
    val formattedTime: LiveData<String> = _formattedTime

    private fun assignValToPlayerTrackForRender() {
        val playerTrackTo = playerTrack.copy(
            artworkUrl = playerTrack.artworkUrl.replaceAfterLast('/', "512x512bb.jpg"),
            releaseDate = playerTrack.releaseDate.split("-", limit=2)[0],
            trackTime = getTimeFormat(playerTrack.trackTime.toLong())
        )

        _playerTrackForRender.postValue(playerTrackTo)
    }

    private fun play() {
        audioPlayerInteractor.play()
        _playerState.postValue(STATE_PLAYING)
        startTimer()
        _isCompleted.postValue(false)
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

    private fun preparePlayer() {
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

    companion object {
        private const val UPDATE_TIME_INFO_MS = 300L
    }
}