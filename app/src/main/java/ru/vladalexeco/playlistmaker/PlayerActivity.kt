package ru.vladalexeco.playlistmaker

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity() {

    private var playerState = STATE_DEFAULT
    private var mediaPlayer = MediaPlayer()

    private var mainThreadHandler: Handler? = null

    private val cycleRunnable = object: Runnable {
        override fun run() {
            val formattedTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(mediaPlayer.currentPosition)
            Log.d("TIME", formattedTime)
            durationInTime.text = formattedTime
            mainThreadHandler?.postDelayed(this, UPDATE_TIME_INFO)
        }
    }

    private var track: Track? = null
    private var url: String? = null

    private lateinit var backArrow: ImageView
    private lateinit var coverImage: ImageView
    private lateinit var trackName: TextView
    private lateinit var artistName: TextView
    private lateinit var duration: TextView
    private lateinit var collectionName: TextView
    private lateinit var year: TextView
    private lateinit var genre: TextView
    private lateinit var country: TextView
    private lateinit var playButton: ImageButton
    private lateinit var durationInTime: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_screen)

        mainThreadHandler = Handler(Looper.getMainLooper())

        backArrow = findViewById(R.id.backArrowPlaylist)
        coverImage = findViewById(R.id.coverMax)
        trackName = findViewById(R.id.trackName)
        artistName = findViewById(R.id.artistName)
        duration = findViewById(R.id.durationName)
        collectionName = findViewById(R.id.albumName)
        year = findViewById(R.id.yearName)
        genre = findViewById(R.id.genreName)
        country = findViewById(R.id.countryName)
        playButton = findViewById(R.id.playButton)
        durationInTime = findViewById(R.id.durationInTime)

        backArrow.setOnClickListener {
            finish()
        }

        playButton.setOnClickListener {
            playbackControl()
        }

        val value: String? = intent.getStringExtra(KEY_FOR_PLAYER)
        track = Gson().fromJson(value, Track::class.java)

        if (track != null) {

            val formattedTime = SimpleDateFormat("mm:ss", Locale.getDefault()).format(track!!.trackTime.toLong())
            val artworkHiResolution = track!!.artworkUrl.replaceAfterLast('/', "512x512bb.jpg")

            Glide.with(this)
                .load(artworkHiResolution)
                .placeholder(R.drawable.track_placeholder_max)
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius)))
                .into(coverImage)

            trackName.text = track!!.trackName
            artistName.text = track!!.artistName
            duration.text = formattedTime

            if (track!!.collectionName != null && track!!.collectionName?.isNotEmpty() == true) {
                collectionName.text = track!!.collectionName
            } else {
                collectionName.text = "n/a"
            }

            year.text = track!!.releaseDate.split("-", limit=2)[0]
            genre.text = track!!.primaryGenreName
            country.text = track!!.country

            url = track!!.previewUrl

            preparePlayer()

        }

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        mainThreadHandler?.removeCallbacks(cycleRunnable)
        mediaPlayer.release()
        super.onDestroy()
    }

    private fun preparePlayer() {

        durationInTime.text = getString(R.string.initial_time)

        mediaPlayer.setDataSource(url)
        mediaPlayer.prepareAsync()
        mediaPlayer.setOnPreparedListener {
            playerState = STATE_PREPARED
        }
        mediaPlayer.setOnCompletionListener {
            playerState = STATE_PREPARED

            mainThreadHandler?.removeCallbacks(cycleRunnable)
            durationInTime.text = getString(R.string.initial_time)

            playButton.setImageResource(R.drawable.play_button)
        }
    }

    private fun startPlayer() {
        mediaPlayer.start()
        playButton.setImageResource(R.drawable.pause_button)
        playerState = STATE_PLAYING

        mainThreadHandler?.postDelayed(cycleRunnable, UPDATE_TIME_INFO)
    }

    private fun pausePlayer() {
        mediaPlayer.pause()
        playButton.setImageResource(R.drawable.play_button)
        playerState = STATE_PAUSED

        mainThreadHandler?.removeCallbacks(cycleRunnable)

    }

    private fun playbackControl() {
        when(playerState) {
            STATE_PLAYING -> pausePlayer()
            STATE_PAUSED, STATE_PREPARED -> startPlayer()
        }
    }

    companion object {
        private const val STATE_DEFAULT = 0
        private const val STATE_PREPARED = 1
        private const val STATE_PLAYING = 2
        private const val STATE_PAUSED = 3

        private const val UPDATE_TIME_INFO = 500L
    }
}

