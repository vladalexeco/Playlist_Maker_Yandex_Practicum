package ru.vladalexeco.playlistmaker.medialibrary.data.converters

import ru.vladalexeco.playlistmaker.database.entity.TrackEntity
import ru.vladalexeco.playlistmaker.medialibrary.data.dto.LibraryTrackDto

class TrackDbConverter {

    fun map(libraryTrackDto: LibraryTrackDto): TrackEntity {
        return TrackEntity(
            trackId = libraryTrackDto.trackId,
            trackName = libraryTrackDto.trackName,
            artistName = libraryTrackDto.artistName,
            trackTime = libraryTrackDto.trackTime,
            artworkUrl = libraryTrackDto.artworkUrl,
            collectionName = libraryTrackDto.collectionName,
            releaseDate = libraryTrackDto.releaseDate,
            primaryGenreName = libraryTrackDto.primaryGenreName,
            country = libraryTrackDto.country,
            previewUrl = libraryTrackDto.previewUrl
        )
    }

    fun map(trackEntity: TrackEntity): LibraryTrackDto {
        return LibraryTrackDto(
            trackId = trackEntity.trackId,
            trackName = trackEntity.trackName,
            artistName = trackEntity.artistName,
            trackTime = trackEntity.trackTime,
            artworkUrl = trackEntity.artworkUrl,
            collectionName = trackEntity.collectionName,
            releaseDate = trackEntity.releaseDate,
            primaryGenreName = trackEntity.primaryGenreName,
            country = trackEntity.country,
            previewUrl = trackEntity.previewUrl
        )
    }

}