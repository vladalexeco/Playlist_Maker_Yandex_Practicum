package ru.vladalexeco.playlistmaker.search.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.util.Resource

interface TracksRepository {
    fun searchTracks(expression: String):Resource<List<Track>>
}