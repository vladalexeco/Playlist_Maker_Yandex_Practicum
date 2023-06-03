package ru.vladalexeco.playlistmaker.domain.interfaces

interface AudioPlayerInteractor {

    var playerState: Int

    fun play()
    fun pause()
    fun release()
}