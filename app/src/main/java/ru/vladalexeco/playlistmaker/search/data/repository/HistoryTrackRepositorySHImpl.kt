package ru.vladalexeco.playlistmaker.search.data.repository

import ru.vladalexeco.playlistmaker.search.data.dto.TrackDto
import ru.vladalexeco.playlistmaker.search.data.storage.TrackSearchHistoryStorage
import ru.vladalexeco.playlistmaker.search.domain.interfaces.HistoryTrackRepositorySH
import ru.vladalexeco.playlistmaker.search.domain.models.Track

class HistoryTrackRepositorySHImpl(private val trackSearchHistoryStorage: TrackSearchHistoryStorage):
    HistoryTrackRepositorySH {

    override fun getTrackListFromSH(): Array<Track> {
        val tracksDto = trackSearchHistoryStorage.getTracksFromStorage()
        val tracks: Array<Track> =  tracksDto.map {
            Track(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTime = it.trackTime,
                artworkUrl = it.artworkUrl,
                artworkUrl60 = it.artworkUrl60,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName,
                country = it.country,
                previewUrl = it.previewUrl
            )
        }.toTypedArray()

        return tracks
    }

    override fun saveTrackListToSH(historyList: ArrayList<Track>) {
        val tracksDto = historyList.map {
            TrackDto(
                trackId = it.trackId,
                trackName = it.trackName,
                artistName = it.artistName,
                trackTime = it.trackTime,
                artworkUrl = it.artworkUrl,
                artworkUrl60 = it.artworkUrl60,
                collectionName = it.collectionName,
                releaseDate = it.releaseDate,
                primaryGenreName = it.primaryGenreName,
                country = it.country,
                previewUrl = it.previewUrl
            )
        }

        trackSearchHistoryStorage.saveTracksToStorage(ArrayList(tracksDto))
    }

}