package ru.vladalexeco.playlistmaker.search.data.repository

import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchRequest
import ru.vladalexeco.playlistmaker.search.data.dto.TrackSearchResponse
import ru.vladalexeco.playlistmaker.search.data.network.NetworkClient
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksSearchRepository
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.util.Resource
import ru.vladalexeco.playlistmaker.util.ServerResponse

class TracksSearchSearchRepositoryImpl(private val networkClient: NetworkClient): TracksSearchRepository {

    override fun searchTracks(expression: String): Resource<List<Track>> {
        val response = networkClient.doRequest(TrackSearchRequest(expression))

        return when (response.resultCode) {
            -1 -> {
                Resource.Error(ServerResponse.DISCONNECT)
            }
            200 -> {
                Resource.Success((response as TrackSearchResponse).tracks.map {
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
                    )})
            }
            else -> {
                Resource.Error(ServerResponse.FAILED)
            }
        }
    }
}