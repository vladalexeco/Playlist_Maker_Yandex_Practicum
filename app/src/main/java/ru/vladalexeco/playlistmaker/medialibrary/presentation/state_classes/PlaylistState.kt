package ru.vladalexeco.playlistmaker.medialibrary.presentation.state_classes

import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

sealed class PlaylistState {
    object Loading : PlaylistState()
    class Success(val data: List<Playlist>) : PlaylistState()
}
