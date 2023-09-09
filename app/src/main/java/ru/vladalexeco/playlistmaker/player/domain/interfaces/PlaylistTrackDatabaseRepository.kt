package ru.vladalexeco.playlistmaker.player.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface PlaylistTrackDatabaseRepository {
    suspend fun insertTrackToPlaylistTrackDatabase(track: Track)

    suspend fun deletePlaylistTrackFromDatabase(track: Track)
}