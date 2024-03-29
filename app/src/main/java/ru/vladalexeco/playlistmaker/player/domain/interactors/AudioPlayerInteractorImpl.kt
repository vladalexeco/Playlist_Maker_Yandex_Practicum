package ru.vladalexeco.playlistmaker.player.domain.interactors

import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.player.domain.interfaces.AudioPlayerRepository
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack

class AudioPlayerInteractorImpl(private val playerTrack: PlayerTrack, private val audioPlayerRepository: AudioPlayerRepository):
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
        playerTrack.previewUrl?.let {
            audioPlayerRepository.prepare(
                previewUrl = it,
                callbackOnPrepared = {
                    callbackPrep.invoke()
                },
                callbackOnCompletion = {
                    callbackComp.invoke()
                }
            )
        }
    }

    override fun isPlaying(): Boolean {
        return audioPlayerRepository.isPlaying()
    }
}