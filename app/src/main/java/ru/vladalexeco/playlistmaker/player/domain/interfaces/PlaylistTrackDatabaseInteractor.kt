package ru.vladalexeco.playlistmaker.player.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface PlaylistTrackDatabaseInteractor {
    suspend fun insertTrackToPlaylistTrackDatabase(track: Track)
}