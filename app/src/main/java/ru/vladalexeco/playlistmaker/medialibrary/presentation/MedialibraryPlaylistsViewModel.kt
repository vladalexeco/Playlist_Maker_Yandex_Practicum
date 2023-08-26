package ru.vladalexeco.playlistmaker.medialibrary.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.presentation.state_classes.PlaylistState
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

class MedialibraryPlaylistsViewModel(
    private val playlistMediaDatabaseInteractor: PlaylistMediaDatabaseInteractor
) : ViewModel() {

    private var _databasePlaylistState = MutableLiveData<PlaylistState>()
    var databasePlaylistState: LiveData<PlaylistState> = _databasePlaylistState

    fun fillData() {

        _databasePlaylistState.postValue(
            PlaylistState.Loading
        )

        viewModelScope.launch {
            playlistMediaDatabaseInteractor
                .getPlaylistsFromDatabase()
                .collect {listOfPlaylists ->
                    processResult(listOfPlaylists)
                }
        }

    }
    private fun processResult(listOfPlaylists: List<Playlist>) {
        _databasePlaylistState.postValue(
            PlaylistState.Success(listOfPlaylists)
        )
    }

}