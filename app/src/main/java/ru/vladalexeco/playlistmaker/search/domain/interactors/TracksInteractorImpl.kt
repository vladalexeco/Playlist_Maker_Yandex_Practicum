package ru.vladalexeco.playlistmaker.search.domain.interactors

import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksRepository
import ru.vladalexeco.playlistmaker.util.Resource
import java.util.concurrent.Executors

class TracksInteractorImpl(private val tracksRepository: TracksRepository): TracksInteractor {

    private val executor = Executors.newCachedThreadPool()

    override fun searchTracks(expression: String, tracksConsumer: TracksInteractor.TracksConsumer) {
        executor.execute {
            when(val resource = tracksRepository.searchTracks(expression)) {
                is Resource.Success -> { tracksConsumer.consume(resource.data, null) }
                is Resource.Error -> { tracksConsumer.consume(null, resource.response) }
            }
        }

    }
}