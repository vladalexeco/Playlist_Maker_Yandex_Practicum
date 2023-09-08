package ru.vladalexeco.playlistmaker.playlist_info.data.db

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.database.PlaylistTrackDatabase
import ru.vladalexeco.playlistmaker.playlist_info.domain.db.CurrentPlaylistTracksDatabaseRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.search.domain.models.mapToTrack

class CurrentPlaylistTracksDatabaseRepositoryImpl(private val playlistTrackDatabase: PlaylistTrackDatabase) :
    CurrentPlaylistTracksDatabaseRepository {
    override suspend fun getTracksForCurrentPlaylist(ids: List<Int>): Flow<List<Track>> {
        return flow {
            val currentPlaylistTracks = playlistTrackDatabase.playlistTrackDao().getTracksByListIds(ids)
            emit(currentPlaylistTracks.map { playlistTrackEntity -> playlistTrackEntity.mapToTrack() })
        }
    }
}