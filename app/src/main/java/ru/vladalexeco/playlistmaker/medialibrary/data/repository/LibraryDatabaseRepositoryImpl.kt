package ru.vladalexeco.playlistmaker.medialibrary.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.medialibrary.data.converters.TrackDbConverter
import ru.vladalexeco.playlistmaker.database.AppDatabase
import ru.vladalexeco.playlistmaker.database.entity.TrackEntity
import ru.vladalexeco.playlistmaker.medialibrary.data.dto.LibraryTrackDto
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseRepository

class LibraryDatabaseRepositoryImpl(
    private val appDatabase: AppDatabase,
    private val trackDbConverter: TrackDbConverter
) : LibraryDatabaseRepository {

    override suspend fun getPlayerTracksFromDatabase(): Flow<List<LibraryTrackDto>> = flow {
        val trackEntityList: List<TrackEntity> = appDatabase.trackDao().getTracks()
        emit(trackEntityList.map { trackEntity -> trackDbConverter.map(trackEntity) })
    }

}