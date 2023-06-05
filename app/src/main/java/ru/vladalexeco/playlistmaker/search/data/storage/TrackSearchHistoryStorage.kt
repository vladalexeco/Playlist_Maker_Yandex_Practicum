package ru.vladalexeco.playlistmaker.search.data.storage

import ru.vladalexeco.playlistmaker.search.data.dto.TrackDto

interface TrackSearchHistoryStorage {

    fun getTracksFromStorage(): Array<TrackDto>

    fun saveTracksToStorage(tracks: ArrayList<TrackDto>)

}