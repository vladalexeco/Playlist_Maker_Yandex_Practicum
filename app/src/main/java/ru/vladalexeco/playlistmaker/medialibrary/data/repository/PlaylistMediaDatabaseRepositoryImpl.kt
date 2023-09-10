package ru.vladalexeco.playlistmaker.medialibrary.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.database.PlaylistDatabase
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseRepository

import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.mapToPlaylist
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.mapToPlaylistEntity

class PlaylistMediaDatabaseRepositoryImpl(private val playlistDatabase: PlaylistDatabase) :
    PlaylistMediaDatabaseRepository {
    override suspend fun getPlaylistsFromDatabase(): Flow<List<Playlist>> = flow {
        val playlistEntityList = playlistDatabase.playlistDao().getPlaylists()
        emit(playlistEntityList.map { playlistEntity -> playlistEntity.mapToPlaylist() })
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistDatabase.playlistDao().deletePlaylist(playlist.mapToPlaylistEntity())
    }

}