package ru.vladalexeco.playlistmaker.search.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchRequest
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchResponse
import ru.vladalexeco.playlistmaker.search.data.network.NetworkClient
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.util.Resource


class TracksSearchSearchRepositoryImpl(private val networkClient: NetworkClient): TracksSearchRepository {

    override fun searchTracks(expression: String): Flow<Resource<List<Track>>> = flow {
        val response = networkClient.doRequest(TrackSearchRequest(expression))

        when(response.resultCode) {

            -1 -> {
                emit(Resource.Error(isFailed = false))
            }

            200 -> {
                emit(Resource.Success((response as TrackSearchResponse).tracks.map {
                    Track(
                        trackId = it.trackId,
                        trackName = it.trackName,
                        artistName = it.artistName,
                        trackTime = it.trackTime,
                        artworkUrl = it.artworkUrl,
                        collectionName = it.collectionName,
                        releaseDate = it.releaseDate,
                        primaryGenreName = it.primaryGenreName,
                        country = it.country,
                        previewUrl = it.previewUrl
                    )}))
            }

            else -> {
                emit(Resource.Error(isFailed = true))
            }
        }
    }
}