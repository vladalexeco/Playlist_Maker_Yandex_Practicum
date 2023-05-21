package ru.vladalexeco.playlistmaker.presentation.interfaces

import ru.vladalexeco.playlistmaker.domain.models.Track

interface TrackHistoryInteractor {
    fun getHistoryList(): ArrayList<Track>
    fun saveHistoryList()
    fun addTrackToHistoryList(track: Track)
    fun clearHistoryList()
}