package ru.vladalexeco.playlistmaker.player.domain.interfaces

import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl

interface AudioPlayerRepository {
    fun play()
    fun pause()
    fun release()
    fun currentPos(): Int
    fun prepare(trackUrl: TrackUrl, callbackOnPrepared: () -> Unit, callbackOnCompletion: () -> Unit)
}