package ru.vladalexeco.playlistmaker.search.domain.interactors

import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
import ru.vladalexeco.playlistmaker.util.Resource
import java.util.concurrent.Executors

class TracksSearchSearchInteractorImpl(private val tracksSearchRepository: TracksSearchRepository): TracksSearchInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, tracksConsumer: TracksSearchInteractor.TracksConsumer) {
        executor.execute {
            when(val resource = tracksSearchRepository.searchTracks(expression)) {
                is Resource.Success -> { tracksConsumer.consume(resource.data, null) }
                is Resource.Error -> { tracksConsumer.consume(null, resource.response) }
            }
        }

    }
}