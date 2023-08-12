package ru.vladalexeco.playlistmaker.search.domain.interactors

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.util.Resource

class TracksSearchSearchInteractorImpl(private val tracksSearchRepository: TracksSearchRepository): TracksSearchInteractor {

    override fun searchTracks(expression: String): Flow<Pair<List<Track>?, Boolean?>> {
        return tracksSearchRepository.searchTracks(expression).map { result ->
            when(result) {
                is Resource.Success -> {
                    Pair(result.data, null)
                }

                is Resource.Error -> {
                    Pair(null, result.isFailed)
                }
            }
        }
    }
}