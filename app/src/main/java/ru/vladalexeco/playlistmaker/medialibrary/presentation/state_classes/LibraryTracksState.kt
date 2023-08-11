package ru.vladalexeco.playlistmaker.medialibrary.presentation.state_classes

import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack

data class LibraryTracksState(
    val libraryTracks: List<LibraryTrack>,
    val isLoading: Boolean
)
