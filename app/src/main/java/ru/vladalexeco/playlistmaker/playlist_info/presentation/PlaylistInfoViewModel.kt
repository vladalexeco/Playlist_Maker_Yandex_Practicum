package ru.vladalexeco.playlistmaker.playlist_info.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseInteractor
import ru.vladalexeco.playlistmaker.playlist_info.presentation.containers.PlaylistInfoContainer
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import java.text.SimpleDateFormat
import java.util.ArrayList
import java.util.Date

class PlaylistInfoViewModel(
    private val currentPlaylistTracksDatabaseInteractor: CurrentPlaylistTracksDatabaseInteractor
) : ViewModel() {

    private val _tracksForCurrentPlaylist = MutableLiveData<PlaylistInfoContainer>()
    val tracksForCurrentPlaylist: LiveData<PlaylistInfoContainer> = _tracksForCurrentPlaylist

    fun getTracksFromDatabaseForCurrentPlaylist(ids: List<Int>) {
        viewModelScope.launch {
            currentPlaylistTracksDatabaseInteractor
                .getTracksForCurrentPlaylist(ids)
                .collect {tracksForCurrentPlaylist ->
                    val listOfTotalTime: List<Int?> = tracksForCurrentPlaylist.map { track: Track -> track.trackTime?.toInt() }
                    var sum = 0
                    for (i in listOfTotalTime) {
                        if (i != null) sum += i
                    }
                    val sumInMinutes: Int = sum / 60000


                    _tracksForCurrentPlaylist.postValue(
                        PlaylistInfoContainer(
                            totalTime = pluralizeWord(sumInMinutes, "минута"),
                            playlistTracks = tracksForCurrentPlaylist
                        )
                    )
                }
        }
    }

    fun getYearFromPlaylist(millis: Long?): String {
        return if (millis != null) {
            val dateFormat = SimpleDateFormat("yyyy")
            dateFormat.format(Date(millis))
        } else {
            ""
        }
    }

    fun convertStringToList(string: String): ArrayList<Int> {
        if (string.isEmpty()) return ArrayList<Int>()

        return ArrayList<Int>(string.split(",").map { item -> item.toInt() })
    }

    fun pluralizeWord(number: Int, word: String): String {
        return when {
            number % 10 == 1 && number % 100 != 11 -> "$number $word"
            number % 10 in 2..4 && (number % 100 < 10 || number % 100 >= 20) -> "$number $word${if (word.endsWith('а')) "и" else "а"}"
            else -> if (word.endsWith('а')) "$number ${word.dropLast(1)}" else "$number ${word}ов"
        }
    }

}