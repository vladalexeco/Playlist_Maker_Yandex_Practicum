package ru.vladalexeco.playlistmaker.search.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.util.Resource

interface TracksSearchRepository {
    fun searchTracks(expression: String):Resource<List<Track>>
}