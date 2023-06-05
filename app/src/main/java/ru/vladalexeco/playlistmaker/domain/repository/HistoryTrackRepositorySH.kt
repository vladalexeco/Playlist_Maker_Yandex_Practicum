package ru.vladalexeco.playlistmaker.domain.repository

import ru.vladalexeco.playlistmaker.search.domain.models.Track

interface HistoryTrackRepositorySH {
    fun getTrackListFromSH(): Array<Track>
    fun saveTrackListToSH(historyList: ArrayList<Track>)
}