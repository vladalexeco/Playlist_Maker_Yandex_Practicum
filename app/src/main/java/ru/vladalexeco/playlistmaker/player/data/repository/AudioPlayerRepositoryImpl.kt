package ru.vladalexeco.playlistmaker.player.data.repository

import android.media.MediaPlayer
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerRepository


class AudioPlayerRepositoryImpl(private var mediaPlayer: MediaPlayer): AudioPlayerRepository {

    override fun play() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        mediaPlayer.release()
        mediaPlayer = MediaPlayer()
    }

    override fun currentPos(): Int {
        return mediaPlayer.currentPosition
    }

    override fun prepare(previewUrl: String, callbackOnPrepared: () -> Unit, callbackOnCompletion: () -> Unit) {
        mediaPlayer.setDataSource(previewUrl)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            callbackOnPrepared.invoke()
        }
        mediaPlayer.setOnCompletionListener {
            callbackOnCompletion.invoke()
        }
    }

    override fun isPlaying(): Boolean {
        return mediaPlayer.isPlaying
    }
}