package ru.vladalexeco.playlistmaker.medialibrary.domain.impl

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.PlaylistMediaDatabaseRepository
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

class PlaylistMediaDatabaseInteractorImpl(
    private val playlistMediaDatabaseRepository: PlaylistMediaDatabaseRepository
    ) : PlaylistMediaDatabaseInteractor {
    override suspend fun getPlaylistsFromDatabase(): Flow<List<Playlist>> {
        return playlistMediaDatabaseRepository.getPlaylistsFromDatabase()
    }

    override suspend fun deletePlaylist(playlist: Playlist) {
        playlistMediaDatabaseRepository.deletePlaylist(playlist)
    }
}