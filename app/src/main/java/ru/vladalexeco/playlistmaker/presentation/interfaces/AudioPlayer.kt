package ru.vladalexeco.playlistmaker.presentation.interfaces

interface AudioPlayer {

    var playerState: Int

    fun play()
    fun pause()
    fun release()
}