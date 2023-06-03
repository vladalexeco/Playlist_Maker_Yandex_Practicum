package ru.vladalexeco.playlistmaker.data.repository

import android.media.MediaPlayer
import ru.vladalexeco.playlistmaker.domain.interfaces.AudioPlayerRepository
import ru.vladalexeco.playlistmaker.domain.models.TrackUrl

class AudioPlayerRepositoryImpl: AudioPlayerRepository {

    private val mediaPlayer = MediaPlayer()

    override fun play() {
        mediaPlayer.start()
    }

    override fun pause() {
        mediaPlayer.pause()
    }

    override fun release() {
        mediaPlayer.release()
    }

    override fun currentPos(): Int {
        return mediaPlayer.currentPosition
    }

    override fun prepare(trackUrl: TrackUrl, callbackOnPrepared: () -> Unit, callbackOnCompletion: () -> Unit) {
        mediaPlayer.setDataSource(trackUrl.url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            callbackOnPrepared.invoke()
        }
        mediaPlayer.setOnCompletionListener {
            callbackOnCompletion.invoke()
        }
    }
}