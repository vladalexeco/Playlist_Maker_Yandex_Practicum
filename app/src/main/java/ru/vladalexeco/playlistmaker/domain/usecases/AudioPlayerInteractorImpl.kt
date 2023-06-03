package ru.vladalexeco.playlistmaker.domain.usecases

import android.os.Handler
import android.os.Looper
import ru.vladalexeco.playlistmaker.domain.interfaces.TrackTimeEventListener
import ru.vladalexeco.playlistmaker.domain.interfaces.UiEventListener
import ru.vladalexeco.playlistmaker.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.domain.interfaces.AudioPlayerInteractor
import ru.vladalexeco.playlistmaker.domain.interfaces.AudioPlayerRepository
import java.text.SimpleDateFormat
import java.util.*

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3

class AudioPlayerInteractorImpl(private val trackUrl: TrackUrl, private val audioPlayerRepository: AudioPlayerRepository): AudioPlayerInteractor {

    private val mainThreadHandler = Handler(Looper.getMainLooper())

    override var playerState = STATE_DEFAULT

    private var listener: UiEventListener? = null
    private var trackTimeListener: TrackTimeEventListener? = null

    private val cycleRunnable = object: Runnable {
        override fun run() {
            val formattedTime = getTimeFormat()
            trackTimeListener?.onTrackTimeEventOccurred(formattedTime)
            mainThreadHandler.postDelayed(this, UPDATE_TIME_INFO_MS)
        }
    }

    init {
        prepare()
    }

    fun setTrackTimeEventListener(listener: TrackTimeEventListener) {
        this.trackTimeListener = listener
    }

    fun removerTrackTimeEventListener() {
        this.trackTimeListener = null
    }

    fun setUiEventListener(listener: UiEventListener) {
        this.listener = listener
    }

    fun removeUiEventListener() {
        this.listener = null
    }

    override fun play() {
        audioPlayerRepository.play()
        playerState = STATE_PLAYING
        mainThreadHandler.postDelayed(cycleRunnable, UPDATE_TIME_INFO_MS)
    }

    override fun pause() {
        audioPlayerRepository.pause()
        playerState = STATE_PAUSED
        mainThreadHandler.removeCallbacks(cycleRunnable)
    }

    override fun release() {
        mainThreadHandler.removeCallbacks(cycleRunnable)
        audioPlayerRepository.release()
    }

    private fun prepare() {
        audioPlayerRepository.prepare(
            trackUrl = trackUrl,
            callbackOnPrepared = {
                playerState = STATE_PREPARED
            },
            callbackOnCompletion = {
                playerState = STATE_PREPARED
                mainThreadHandler.removeCallbacks(cycleRunnable)
                listener?.onEventOccurred()
            }
        )
    }

    private fun getTimeFormat() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(audioPlayerRepository.currentPos())

    companion object {
        private const val UPDATE_TIME_INFO_MS = 300L
    }

}