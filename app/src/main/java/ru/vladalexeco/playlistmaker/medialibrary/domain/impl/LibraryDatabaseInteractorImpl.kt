package ru.vladalexeco.playlistmaker.medialibrary.domain.impl

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vladalexeco.playlistmaker.medialibrary.data.dto.LibraryTrackDto
import ru.vladalexeco.playlistmaker.medialibrary.domain.converters.LibraryTrackDataConverter
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseInteractor
import ru.vladalexeco.playlistmaker.medialibrary.domain.db.LibraryDatabaseRepository
import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack

class LibraryDatabaseInteractorImpl(
    private val libraryDatabaseRepository: LibraryDatabaseRepository,
    private val libraryTrackDataConverter: LibraryTrackDataConverter
) : LibraryDatabaseInteractor {

    override suspend fun getPlayerTracksFromDatabase(): Flow<List<LibraryTrack>> {
        return libraryDatabaseRepository.getPlayerTracksFromDatabase().map { list ->
            convertListLibraryTrackDtoToListLibraryTrack(list)
        }
    }

    private fun convertListLibraryTrackDtoToListLibraryTrack(libraryTrackDtoList: List<LibraryTrackDto>): List<LibraryTrack> {
        return libraryTrackDtoList.map { track -> libraryTrackDataConverter.map(track) }
    }
}