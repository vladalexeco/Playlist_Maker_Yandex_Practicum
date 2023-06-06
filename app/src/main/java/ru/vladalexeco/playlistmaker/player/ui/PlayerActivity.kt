package ru.vladalexeco.playlistmaker.player.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.gson.Gson
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.player.presentation.PlayerViewModel
import ru.vladalexeco.playlistmaker.player.presentation.PlayerViewModelFactory
import ru.vladalexeco.playlistmaker.player.presentation.STATE_PAUSED
import ru.vladalexeco.playlistmaker.player.presentation.STATE_PLAYING
import ru.vladalexeco.playlistmaker.search.ui.KEY_FOR_PLAYER

class  PlayerActivity : AppCompatActivity() {

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

    private lateinit var viewModel: PlayerViewModel

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
            viewModel.playbackControl()
        }

        val value: String? = intent.getStringExtra(KEY_FOR_PLAYER)
        val track = Gson().fromJson(value, Track::class.java)

        if (track != null) {

            val url = track.previewUrl

            viewModel = ViewModelProvider(this, PlayerViewModelFactory(TrackUrl(url)))[PlayerViewModel::class.java]

            val formattedTime = viewModel.getTimeFormat(track.trackTime.toLong())
            val artworkHiResolution = track.artworkUrl.replaceAfterLast('/', "512x512bb.jpg")

            Glide.with(this)
                .load(artworkHiResolution)
                .placeholder(R.drawable.track_placeholder_max)
                .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius)))
                .into(coverImage)

            trackName.text = track.trackName
            artistName.text = track.artistName
            duration.text = formattedTime

            if (track.collectionName != null && track.collectionName.isNotEmpty()) {
                collectionName.text = track.collectionName
            } else {
                collectionName.text = "n/a"
            }

            year.text = track.releaseDate.split("-", limit=2)[0]
            genre.text = track.primaryGenreName
            country.text = track.country


            viewModel.playerState.observe(this) { statePlaying ->

                when (statePlaying) {
                    STATE_PLAYING -> playButton.setImageResource(R.drawable.pause_button)
                    STATE_PAUSED -> playButton.setImageResource(R.drawable.play_button)
                }

            }

            viewModel.isCompleted.observe(this) { isCompleted ->
                if (isCompleted) {
                    durationInTime.text = getString(R.string.initial_time)
                    playButton.setImageResource(R.drawable.play_button)
                }
            }

            viewModel.formattedTime.observe(this) { trackTime ->
                durationInTime.text = trackTime
            }

        }

    }

    override fun onPause() {
        super.onPause()
        viewModel.pause()
    }

    override fun onDestroy() {
        viewModel.release()
        super.onDestroy()
    }

}


