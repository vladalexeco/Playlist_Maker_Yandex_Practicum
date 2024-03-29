package ru.vladalexeco.playlistmaker.medialibrary.domain.converters

import ru.vladalexeco.playlistmaker.medialibrary.data.dto.LibraryTrackDto
import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack

class LibraryTrackDataConverter {

    fun map(libraryTrack: LibraryTrack): LibraryTrackDto {
        return LibraryTrackDto(
            trackId = libraryTrack.trackId,
            trackName = libraryTrack.trackName,
            artistName = libraryTrack.artistName,
            trackTime = libraryTrack.trackTime,
            artworkUrl = libraryTrack.artworkUrl,
            artworkUrl60 = libraryTrack.artworkUrl60,
            collectionName = libraryTrack.collectionName,
            releaseDate = libraryTrack.releaseDate,
            primaryGenreName = libraryTrack.primaryGenreName,
            country = libraryTrack.country,
            previewUrl = libraryTrack.previewUrl,
            insertTimeStamp = libraryTrack.insertTimeStamp
        )
    }

    fun map(libraryTrackDto: LibraryTrackDto): LibraryTrack {
        return LibraryTrack(
            trackId = libraryTrackDto.trackId,
            trackName = libraryTrackDto.trackName,
            artistName = libraryTrackDto.artistName,
            trackTime = libraryTrackDto.trackTime,
            artworkUrl = libraryTrackDto.artworkUrl,
            artworkUrl60 = libraryTrackDto.artworkUrl60,
            collectionName = libraryTrackDto.collectionName,
            releaseDate = libraryTrackDto.releaseDate,
            primaryGenreName = libraryTrackDto.primaryGenreName,
            country = libraryTrackDto.country,
            previewUrl = libraryTrackDto.previewUrl,
            insertTimeStamp = libraryTrackDto.insertTimeStamp
        )
    }
}