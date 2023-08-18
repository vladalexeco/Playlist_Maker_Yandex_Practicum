package ru.vladalexeco.playlistmaker.medialibrary.domain.db

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack

interface LibraryDatabaseInteractor {
    suspend fun getPlayerTracksFromDatabase(): Flow<List<LibraryTrack>>
}