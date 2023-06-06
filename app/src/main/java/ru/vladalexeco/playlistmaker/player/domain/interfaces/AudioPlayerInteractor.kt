package ru.vladalexeco.playlistmaker.player.domain.interfaces

import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl

interface AudioPlayerInteractor {

    fun play()
    fun pause()
    fun release()
    fun getCurrentPos(): Int
    fun prepare(callbackPrep: () -> Unit, callbackComp: () -> Unit)

}