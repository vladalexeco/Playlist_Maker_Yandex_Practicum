package ru.vladalexeco.playlistmaker.search.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.search.data.dto.ServerResponse

interface TracksInteractor {

    fun searchTracks(expression: String, tracksConsumer: TracksConsumer )

    interface TracksConsumer {
        fun consume(foundTracks: List<Track>?, errorMessage: ServerResponse?)
    }

}