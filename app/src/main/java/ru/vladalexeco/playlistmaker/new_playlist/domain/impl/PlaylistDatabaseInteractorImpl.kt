package ru.vladalexeco.playlistmaker.new_playlist.domain.impl

import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseInteractor
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseRepository
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

class PlaylistDatabaseInteractorImpl(
    private val playlistDatabaseRepository: PlaylistDatabaseRepository
    ) : PlaylistDatabaseInteractor {
    override suspend fun insertPlaylistToDatabase(playlist: Playlist) {
        playlistDatabaseRepository.insertPlaylistToDatabase(playlist)
    }

}