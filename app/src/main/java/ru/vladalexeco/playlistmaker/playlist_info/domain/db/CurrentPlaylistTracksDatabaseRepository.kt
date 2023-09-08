package ru.vladalexeco.playlistmaker.playlist_info.domain.db

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface CurrentPlaylistTracksDatabaseRepository {

    suspend fun getTracksForCurrentPlaylist(ids: List<Int>): Flow<List<Track>>

}