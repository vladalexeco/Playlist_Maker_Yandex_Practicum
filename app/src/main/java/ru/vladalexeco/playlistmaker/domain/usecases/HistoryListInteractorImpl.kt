package ru.vladalexeco.playlistmaker.domain.usecases

import ru.vladalexeco.playlistmaker.domain.models.Track
import ru.vladalexeco.playlistmaker.domain.repository.HistoryTrackRepositorySH
import ru.vladalexeco.playlistmaker.presentation.interfaces.TrackHistoryInteractor

class HistoryListInteractorImpl(private val historyTrackRepositorySH: HistoryTrackRepositorySH?): TrackHistoryInteractor {

    private val historyList: ArrayList<Track> = ArrayList(historyTrackRepositorySH?.getTrackListFromSH()?.toList())

    override fun getHistoryList(): ArrayList<Track> {
        return historyList
    }

    override fun saveHistoryList() {
        historyTrackRepositorySH?.saveTrackListToSH(historyList)
    }

    override fun addTrackToHistoryList(track: Track) {
        val index = historyList.indexOfFirst { it.trackId == track.trackId }

        if (historyList.size < 10) {
            if (index == -1) {
                historyList.add(0, track)
            } else {
                shiftElementToTopOfHistoryList(index)
            }
        } else {
            if (index == -1) {
                historyList.removeAt(historyList.lastIndex)
                historyList.add(0, track)
            } else {
                shiftElementToTopOfHistoryList(index)
            }
        }
    }

    override fun clearHistoryList() {
        historyList.clear()
    }


    private fun shiftElementToTopOfHistoryList(index: Int) {
        val trackToMove = historyList[index]
        historyList.removeAt(index)
        historyList.add(0, trackToMove)
    }

}