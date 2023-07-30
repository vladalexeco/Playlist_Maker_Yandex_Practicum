package ru.vladalexeco.playlistmaker.search.domain.interfaces

import kotlinx.coroutines.flow.Flow
import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface TracksSearchInteractor {

    fun searchTracks(expression: String): Flow<Pair<List<Track>?, Boolean?>>

}