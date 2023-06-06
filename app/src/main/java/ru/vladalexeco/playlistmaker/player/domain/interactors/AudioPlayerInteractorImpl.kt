package ru.vladalexeco.playlistmaker.player.domain.interactors

import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerRepository

class AudioPlayerInteractorImpl(private val trackUrl: TrackUrl, private val audioPlayerRepository: AudioPlayerRepository):
    AudioPlayerInteractor {

    override fun play() {
        audioPlayerRepository.play()
    }

    override fun pause() {
        audioPlayerRepository.pause()
    }

    override fun release() {
        audioPlayerRepository.release()
    }

    override fun getCurrentPos(): Int {
        return audioPlayerRepository.currentPos()
    }

    override fun prepare(callbackPrep: () -> Unit, callbackComp: () -> Unit) {
        audioPlayerRepository.prepare(
            trackUrl = trackUrl,
            callbackOnPrepared = {
                callbackPrep.invoke()
            },
            callbackOnCompletion = {
                callbackComp.invoke()
            }
        )
    }
}