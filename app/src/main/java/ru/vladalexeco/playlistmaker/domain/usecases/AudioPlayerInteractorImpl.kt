package ru.vladalexeco.playlistmaker.domain.usecases

import android.media.MediaPlayer
import android.os.Handler
import android.os.Looper
import ru.vladalexeco.playlistmaker.domain.interfaces.TrackTimeEventListener
import ru.vladalexeco.playlistmaker.domain.interfaces.UiEventListener
import ru.vladalexeco.playlistmaker.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.presentation.interfaces.AudioPlayer
import java.text.SimpleDateFormat
import java.util.*

const val STATE_DEFAULT = 0
const val STATE_PREPARED = 1
const val STATE_PLAYING = 2
const val STATE_PAUSED = 3

class AudioPlayerInteractorImpl(private val trackUrl: TrackUrl): AudioPlayer {

    private val mediaPlayer = MediaPlayer()
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
        mediaPlayer.start()
        playerState = STATE_PLAYING
        mainThreadHandler.postDelayed(cycleRunnable, UPDATE_TIME_INFO_MS)
    }

    override fun pause() {
        mediaPlayer.pause()
        playerState = STATE_PAUSED
        mainThreadHandler.removeCallbacks(cycleRunnable)
    }

    override fun release() {
        mainThreadHandler.removeCallbacks(cycleRunnable)
        mediaPlayer.release()
    }

    private fun prepare() {
        mediaPlayer.setDataSource(trackUrl.url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED
            mainThreadHandler.removeCallbacks(cycleRunnable)
            listener?.onEventOccurred()
        }
    }

    private fun getTimeFormat() = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)

    companion object {
        private const val UPDATE_TIME_INFO_MS = 300L
    }

}