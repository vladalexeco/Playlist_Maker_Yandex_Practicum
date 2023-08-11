package ru.vladalexeco.playlistmaker.player.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack

interface AudioPlayerDatabaseInteractor {
    suspend fun addPlayerTrackToDatabase(playerTrack: PlayerTrack, insertionTimeStamp: Long)
    suspend fun deletePlayerTrackFromDatabase(playerTrack: PlayerTrack)
    suspend fun getTracksIdFromDatabase(): Flow<List<Int>>
}