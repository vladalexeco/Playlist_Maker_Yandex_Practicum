package ru.vladalexeco.playlistmaker.search.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface TracksSearchInteractor {

    fun searchTracks(expression: String, tracksConsumer: TracksConsumer )

    interface TracksConsumer {
        fun consume(foundTracks: List<Track>?, isFailed: Boolean?)
    }

}