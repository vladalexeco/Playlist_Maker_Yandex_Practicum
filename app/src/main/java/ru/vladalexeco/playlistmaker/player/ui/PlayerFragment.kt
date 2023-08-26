package ru.vladalexeco.playlistmaker.player.ui

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.databinding.FragmentPlayerBinding
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import ru.vladalexeco.playlistmaker.player.presentation.PlayerViewModel
import ru.vladalexeco.playlistmaker.player.presentation.STATE_PAUSED
import ru.vladalexeco.playlistmaker.player.presentation.STATE_PLAYING
import ru.vladalexeco.playlistmaker.player.presentation.state_classes.FavouriteTrackState
import ru.vladalexeco.playlistmaker.root.listeners.BottomNavigationListener
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import java.io.Serializable

class PlayerFragment : Fragment() {

    private var bottomNavigationListener: BottomNavigationListener? = null

    private lateinit var binding: FragmentPlayerBinding

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
    private lateinit var favouriteButton: ImageButton

    private lateinit var playerTrack: PlayerTrack

    private val viewModel: PlayerViewModel by viewModel {
        parametersOf(playerTrack)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationListener) {
            bottomNavigationListener = context
        } else {
            throw IllegalArgumentException("Activity must implement BottomNavigationListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPlayerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        backArrow = binding.backArrowPlaylist
        coverImage = binding.coverMax
        trackName = binding.trackName
        artistName = binding.artistName
        duration = binding.durationName
        collectionName = binding.albumName
        year = binding.yearName
        genre = binding.genreName
        country = binding.countryName
        playButton = binding.playButton
        durationInTime = binding.durationInTime
        favouriteButton = binding.favouriteButton

        backArrow.setOnClickListener {
            findNavController().navigateUp()
        }

        playButton.setOnClickListener {
            viewModel.playbackControl()
        }

        favouriteButton.setOnClickListener {
            if (viewModel.checkValueFromIsFavourite()) {
                favouriteButton.setImageResource(R.drawable.like_button)
                viewModel.assignValueToIsFavourite(false)
                lifecycleScope.launch {
                    viewModel.deletePlayerTrackFromFavourites()
                }
            } else {
                favouriteButton.setImageResource(R.drawable.like_button_marked)
                viewModel.assignValueToIsFavourite(true)
                lifecycleScope.launch {
                    viewModel.addPlayerTrackToFavourites()
                }
            }
        }

        val track = requireArguments().getSerializableExtra(CURRENT_TRACK, Track::class.java)

        playerTrack = convertTrackToPlayerTrack(track)

        if (playerTrack.previewUrl == null) {
            playButton.isEnabled = false
        }

        viewModel.checkTrackIsFavourite()

        viewModel.playerTrackForRender.observe(viewLifecycleOwner) { playerTrack ->
            render(playerTrack)
        }

        viewModel.playerState.observe(viewLifecycleOwner) { statePlaying ->

            when (statePlaying) {
                STATE_PLAYING -> playButton.setImageResource(R.drawable.pause_button)
                STATE_PAUSED -> playButton.setImageResource(R.drawable.play_button)
            }

        }

        viewModel.isCompleted.observe(viewLifecycleOwner) { isCompleted ->
            if (isCompleted) {
                durationInTime.text = getString(R.string.initial_time)
                playButton.setImageResource(R.drawable.play_button)
            }
        }

        viewModel.formattedTime.observe(viewLifecycleOwner) { trackTime ->
            durationInTime.text = trackTime
        }

        viewModel.favouriteTrack.observe(viewLifecycleOwner) { favouriteTrackState ->
            renderFavouriteButton(favouriteTrackState)
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

    override fun onResume() {
        super.onResume()
        hideBottomNavigation(true)
    }

    override fun onStop() {
        super.onStop()
        hideBottomNavigation(false)
    }

    private fun renderFavouriteButton(favouriteTrackState: FavouriteTrackState) {

        when {

            favouriteTrackState.isLoading -> {
                favouriteButton.isEnabled = false
            }

            else -> {
                favouriteButton.isEnabled = true

                if (favouriteTrackState.isFavourite == true) {
                    favouriteButton.setImageResource(R.drawable.like_button_marked)
                } else {
                    favouriteButton.setImageResource(R.drawable.like_button)
                }
            }
        }

    }

    private fun hideBottomNavigation(isHide: Boolean) {
        bottomNavigationListener?.toggleBottomNavigationViewVisibility(!isHide)
    }

    private fun convertTrackToPlayerTrack(track: Track): PlayerTrack {
        return PlayerTrack(
            trackId = track.trackId,
            trackName = track.trackName,
            artistName = track.artistName,
            trackTime = track.trackTime,
            artworkUrl = track.artworkUrl,
            collectionName = track.collectionName,
            releaseDate = track.releaseDate,
            primaryGenreName = track.primaryGenreName,
            country = track.country,
            previewUrl = track.previewUrl,
            insertionTimeStamp = null
        )
    }

    private fun render(track: PlayerTrack) {
        trackName.text = viewModel.checkEmptinessOrNull(track.trackName)
        artistName.text = viewModel.checkEmptinessOrNull(track.artistName)
        duration.text = viewModel.checkEmptinessOrNull(track.trackTime)
        collectionName.text = viewModel.checkEmptinessOrNull(track.collectionName)
        year.text = viewModel.checkEmptinessOrNull(track.releaseDate)
        genre.text = viewModel.checkEmptinessOrNull(track.primaryGenreName)
        country.text = viewModel.checkEmptinessOrNull(track.country)

        Glide.with(this)
            .load(track.artworkUrl)
            .placeholder(R.drawable.track_placeholder_max)
            .transform(RoundedCorners(resources.getDimensionPixelSize(R.dimen.corner_radius)))
            .into(coverImage)
    }

    private fun <T : Serializable?> Bundle.getSerializableExtra(key: String, m_class: Class<T>): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.getSerializable(key, m_class)!!
        else
            this.getSerializable(key) as T
    }

    companion object {
        private const val CURRENT_TRACK = "CURRENT_TRACK"

        fun createArgs(track: Track): Bundle {
            return bundleOf(CURRENT_TRACK to track)
        }
    }

}