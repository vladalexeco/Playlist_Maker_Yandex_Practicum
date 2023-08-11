package ru.vladalexeco.playlistmaker.player.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.database.AppDatabase
import ru.vladalexeco.playlistmaker.player.data.converters.PlayerTrackDbConverter
import ru.vladalexeco.playlistmaker.player.data.dto.PlayerTrackDto
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerDatabaseRepository

class AudioPlayerDatabaseRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val playerTrackDbConverter: PlayerTrackDbConverter
) : AudioPlayerDatabaseRepository {

    override suspend fun addPlayerTrackToDatabase(playerTrackDto: PlayerTrackDto) {
        appDatabase.trackDao().insertTrack(playerTrackDbConverter.map(playerTrackDto))
    }

    override suspend fun deletePlayerTrackFromDatabase(playerTrackDto: PlayerTrackDto) {
        appDatabase.trackDao().deleteTrack(playerTrackDbConverter.map(playerTrackDto))
    }

    override suspend fun getTracksIdFromDatabase(): Flow<List<Int>> = flow {
        emit(appDatabase.trackDao().getTracksId())
    }

}