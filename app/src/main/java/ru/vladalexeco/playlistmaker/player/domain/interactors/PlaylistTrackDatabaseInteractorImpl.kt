package ru.vladalexeco.playlistmaker.player.domain.interactors

import ru.vladalexeco.playlistmaker.player.domain.interfaces.PlaylistTrackDatabaseInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.PlaylistTrackDatabaseRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class PlaylistTrackDatabaseInteractorImpl(
    private val playlistTrackDatabaseRepository: PlaylistTrackDatabaseRepository
) : PlaylistTrackDatabaseInteractor {
    override suspend fun insertTrackToPlaylistTrackDatabase(track: Track) {
        playlistTrackDatabaseRepository.insertTrackToPlaylistTrackDatabase(track)
    }

    override suspend fun deletePlaylistTrackFromDatabase(track: Track) {
        playlistTrackDatabaseRepository.deletePlaylistTrackFromDatabase(track)
    }

    override suspend fun deletePlaylistTrackFromDatabaseById(id: Int) {
        playlistTrackDatabaseRepository.deletePlaylistTrackFromDatabaseById(id)
    }
}