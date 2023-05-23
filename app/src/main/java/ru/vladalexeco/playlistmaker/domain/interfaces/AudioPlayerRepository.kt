package ru.vladalexeco.playlistmaker.domain.interfaces

import ru.vladalexeco.playlistmaker.domain.models.TrackUrl

interface AudioPlayerRepository {
    fun play()
    fun pause()
    fun release()
    fun currentPos(): Int
    fun prepare(trackUrl: TrackUrl, callbackOnPrepared: () -> Unit, callbackOnCompletion: () -> Unit)
}