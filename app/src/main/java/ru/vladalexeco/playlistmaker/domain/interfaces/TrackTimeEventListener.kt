package ru.vladalexeco.playlistmaker.domain.interfaces

interface TrackTimeEventListener {
    fun onTrackTimeEventOccurred(trackTime: String)
}