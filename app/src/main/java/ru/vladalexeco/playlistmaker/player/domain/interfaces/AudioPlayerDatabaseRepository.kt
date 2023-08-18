package ru.vladalexeco.playlistmaker.player.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.player.data.dto.PlayerTrackDto

interface AudioPlayerDatabaseRepository {
    suspend fun addPlayerTrackToDatabase(playerTrackDto: PlayerTrackDto)
    suspend fun deletePlayerTrackFromDatabase(playerTrackDto: PlayerTrackDto)
    suspend fun getTracksIdFromDatabase(): Flow<List<Int>>
}