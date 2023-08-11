package ru.vladalexeco.playlistmaker.medialibrary.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.medialibrary.domain.converters.LibraryTrackToTrackConverter
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack
import ru.vladalexeco.playlistmaker.medialibrary.presentation.state_classes.LibraryTracksState
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class MedialibraryFavouritesViewModel(
    private val libraryDatabaseInteractor: LibraryDatabaseInteractor,
    private val libraryTrackToTrackConverter: LibraryTrackToTrackConverter
) : ViewModel() {

    private val _databaseTracksState = MutableLiveData<LibraryTracksState>()
    val databaseTracksState: LiveData<LibraryTracksState> = _databaseTracksState

    fun convertLibraryTrackToTrack(libraryTrack: LibraryTrack): Track {
        return libraryTrackToTrackConverter.map(libraryTrack)
    }

    fun fillData() {

        _databaseTracksState.postValue(
            LibraryTracksState(
                libraryTracks = emptyList(),
                isLoading = true
            )
        )

        viewModelScope.launch {
            libraryDatabaseInteractor
                .getPlayerTracksFromDatabase()
                .collect { libraryTracks ->
                    processResult(libraryTracks)
                }
        }

    }

    private fun processResult(libraryTracks: List<LibraryTrack>) {

        if (libraryTracks.isEmpty()) {
            _databaseTracksState.postValue(
                LibraryTracksState(
                    libraryTracks = emptyList(),
                    isLoading = false
                )
            )
        } else {
            _databaseTracksState.postValue(
                LibraryTracksState(
                    libraryTracks = libraryTracks,
                    isLoading = false
                )
            )
        }
    }

}