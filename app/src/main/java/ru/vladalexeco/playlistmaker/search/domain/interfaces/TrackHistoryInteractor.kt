package ru.vladalexeco.playlistmaker.search.domain.interfaces

import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface TrackHistoryInteractor {
    fun getHistoryList(): ArrayList<Track>
    fun saveHistoryList()
    fun addTrackToHistoryList(track: Track)
    fun clearHistoryList()
}