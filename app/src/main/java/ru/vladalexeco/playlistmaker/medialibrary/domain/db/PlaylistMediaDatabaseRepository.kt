package ru.vladalexeco.playlistmaker.medialibrary.domain.db

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

interface PlaylistMediaDatabaseRepository {
    suspend fun getPlaylistsFromDatabase(): Flow<List<Playlist>>
}