package ru.vladalexeco.playlistmaker.medialibrary.domain.converters

import ru.vladalexeco.playlistmaker.medialibrary.domain.models.LibraryTrack
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class LibraryTrackToTrackConverter {

    fun map(libraryTrack: LibraryTrack): Track {
        return Track(
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
            previewUrl = libraryTrack.previewUrl
        )
    }

}