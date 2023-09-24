package ru.vladalexeco.playlistmaker.playlist_info.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseInteractor
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class CurrentPlaylistTracksDatabaseInteractorImpl(
    private val currentPlaylistTracksDatabaseRepository: CurrentPlaylistTracksDatabaseRepository
) : CurrentPlaylistTracksDatabaseInteractor {
    override suspend fun getTracksForCurrentPlaylist(ids: List<Int>): Flow<List<Track>> {
        return currentPlaylistTracksDatabaseRepository.getTracksForCurrentPlaylist(ids)
    }
}