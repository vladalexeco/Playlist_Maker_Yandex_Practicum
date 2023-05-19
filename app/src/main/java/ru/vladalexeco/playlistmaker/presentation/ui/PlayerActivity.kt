package ru.vladalexeco.playlistmaker.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import ru.vladalexeco.playlistmaker.KEY_FOR_PLAYER
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.Track
import ru.vladalexeco.playlistmaker.domain.interfaces.TrackTimeEventListener
import ru.vladalexeco.playlistmaker.domain.interfaces.UiEventListener
import ru.vladalexeco.playlistmaker.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.domain.usecases.AudioPlayerInteractorImpl
import ru.vladalexeco.playlistmaker.domain.usecases.STATE_PAUSED
import ru.vladalexeco.playlistmaker.domain.usecases.STATE_PLAYING
import ru.vladalexeco.playlistmaker.domain.usecases.STATE_PREPARED
import ru.vladalexeco.playlistmaker.presentation.interfaces.AudioPlayer
import java.text.SimpleDateFormat
import java.util.*

class PlayerActivity : AppCompatActivity(), UiEventListener, TrackTimeEventListener {

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

    private lateinit var audioPlayer: AudioPlayer


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.player_screen)

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

            audioPlayer = AudioPlayerInteractorImpl(TrackUrl(url))
            (audioPlayer as AudioPlayerInteractorImpl).setUiEventListener(this)
            (audioPlayer as AudioPlayerInteractorImpl).setTrackTimeEventListener(this)

        }

    }

    override fun onPause() {
        super.onPause()
        pausePlayer()
    }

    override fun onDestroy() {
        audioPlayer.release()
        (audioPlayer as AudioPlayerInteractorImpl).removeUiEventListener()
        (audioPlayer as AudioPlayerInteractorImpl).removerTrackTimeEventListener()

        super.onDestroy()
    }

    private fun startPlayer() {
        audioPlayer.play()
        playButton.setImageResource(R.drawable.pause_button)
    }

    private fun pausePlayer() {
        audioPlayer.pause()
        playButton.setImageResource(R.drawable.play_button)
    }

    private fun playbackControl() {
        when(audioPlayer.playerState) {
             STATE_PLAYING -> pausePlayer()
            STATE_PAUSED, STATE_PREPARED -> startPlayer()
        }
    }

    override fun onEventOccurred() {
        durationInTime.text = getString(R.string.initial_time)
        playButton.setImageResource(R.drawable.play_button)
    }

    override fun onTrackTimeEventOccurred(trackTime: String) {
        durationInTime.text = trackTime
    }
}


