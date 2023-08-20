package ru.vladalexeco.playlistmaker.new_playlist.data.repository

import ru.vladalexeco.playlistmaker.database.PlaylistDatabase
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseRepository
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.mapToPlaylistEntity

class PlaylistDatabaseRepositoryImpl(private val playlistDatabase: PlaylistDatabase) : PlaylistDatabaseRepository {
    override suspend fun insertPlaylistToDatabase(playlist: Playlist) {
        playlistDatabase.playlistDao().insertPlaylist(playlist.mapToPlaylistEntity())
    }
}