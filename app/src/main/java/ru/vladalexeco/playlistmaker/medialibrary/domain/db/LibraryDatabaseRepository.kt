package ru.vladalexeco.playlistmaker.medialibrary.domain.db

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.medialibrary.data.dto.LibraryTrackDto

interface LibraryDatabaseRepository {
    suspend fun getPlayerTracksFromDatabase(): Flow<List<LibraryTrackDto>>
}