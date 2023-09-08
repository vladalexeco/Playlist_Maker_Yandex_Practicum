package ru.vladalexeco.playlistmaker.playlist_info.ui

import android.content.Context
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import ru.vladalexeco.playlistmaker.databinding.FragmentPlaylistInfoBinding
import ru.vladalexeco.playlistmaker.playlist_info.presentation.PlaylistInfoViewModel
import ru.vladalexeco.playlistmaker.root.listeners.BottomNavigationListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import ru.vladalexeco.playlistmaker.playlist_info.presentation.containers.PlaylistInfoContainer
import ru.vladalexeco.playlistmaker.search.ui.TrackAdapter
import java.io.File
import java.io.Serializable

class PlaylistInfoFragment : Fragment() {

    private var bottomNavigationListener: BottomNavigationListener? = null

    private lateinit var binding: FragmentPlaylistInfoBinding

    var playlist: Playlist? = null

    val adapter = TrackAdapter {

    }

    private val viewModel: PlaylistInfoViewModel by viewModel()

    // Виджеты основного экрана
    private lateinit var backArrowImageView: ImageView
    private lateinit var playlistCoverImageView: ImageView
    private lateinit var nameOfPlaylistTextView: TextView
    private lateinit var yearOfPlaylistCreationTextView: TextView
    private lateinit var totalAmountOfMinutesTextView: TextView
    private lateinit var totalNumberOfTracksTextView: TextView
    private lateinit var sharePlaylistImageView: ImageView
    private lateinit var menuOfPlaylistImageView: ImageView

    // Виджеты плейлиста Bottom Sheet
    private lateinit var playlistInfoBottomSheetLinearLayout: LinearLayout
    private lateinit var playlistInfoBottomSheetRecyclerView: RecyclerView

    // Виджеты меню Bottom Sheet
    private lateinit var playlistMenuBottomSheetLinearLayout: LinearLayout
    private lateinit var playlistCoverBottomSheetImageView: ImageView
    private lateinit var nameOfPlaylistBottomSheetTextView: TextView
    private lateinit var totalNumberOfTracksBottomSheetTextView: TextView
    private lateinit var sharePlaylistBottomSheetFrameLayout: FrameLayout
    private lateinit var editPlaylistBottomSheetFrameLayout: FrameLayout
    private lateinit var deletePlaylistBottomSheetFrameLayout: FrameLayout

    // Lifecycle Callbacks
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

    override fun onResume() {
        super.onResume()
        hideBottomNavigation(true)
    }

    override fun onStop() {
        super.onStop()
        hideBottomNavigation(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPlaylistInfoBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Виджеты основного экрана
        backArrowImageView = binding.playlistInfoBackarrowImageview
        playlistCoverImageView = binding.playlistInfoCoverImageview
        nameOfPlaylistTextView = binding.nameOfPlaylistInfoTextview
        yearOfPlaylistCreationTextView = binding.yearOfPlaylistInfoTextview
        totalAmountOfMinutesTextView = binding.totalMinutesPlaylistInfoTextview
        totalNumberOfTracksTextView = binding.amountOfTracksPlaylistInfoTextview
        sharePlaylistImageView = binding.playlistInfoShareImageview
        menuOfPlaylistImageView = binding.playlistInfoMenuImageview


        // Виджеты плейлиста Bottom Sheet
        playlistInfoBottomSheetLinearLayout = binding.playlistInfoBottomSheet
        playlistInfoBottomSheetRecyclerView = binding.playlistInfoBottomSheetRecyclerview


        // Виджеты меню Bottom Sheet
        playlistMenuBottomSheetLinearLayout = binding.playlistMenuBottomSheet
        playlistCoverBottomSheetImageView = binding.playlistInfoCoverMinImageview
        nameOfPlaylistBottomSheetTextView = binding.nameOfPlaylistInfoMinTextview
        totalNumberOfTracksBottomSheetTextView = binding.amountOfTracksPlaylistInfoMinTextview
        sharePlaylistBottomSheetFrameLayout = binding.sharePlaylistFramelayout
        editPlaylistBottomSheetFrameLayout = binding.editPlaylistFramelayout
        deletePlaylistBottomSheetFrameLayout = binding.deletePlaylistFramelayout

        // recycler view
        playlistInfoBottomSheetRecyclerView.adapter = adapter
        playlistInfoBottomSheetRecyclerView.layoutManager = LinearLayoutManager(requireContext())

        val bottomSheetBehavior = BottomSheetBehavior.from(playlistMenuBottomSheetLinearLayout).apply {
            state = BottomSheetBehavior.STATE_HIDDEN
        }

        playlist = requireArguments().getSerializableExtra(CURRENT_PLAYLIST, Playlist::class.java)


        //rendering
        renderWithSerializableData()
        viewModel.getTracksFromDatabaseForCurrentPlaylist(viewModel.convertStringToList(playlist!!.listOfTracksId))


        // Слушатели кнопок
        backArrowImageView.setOnClickListener {
            findNavController().navigateUp()
        }

        sharePlaylistImageView.setOnClickListener {

        }

        menuOfPlaylistImageView.setOnClickListener {

        }

        sharePlaylistBottomSheetFrameLayout.setOnClickListener {

        }

        editPlaylistBottomSheetFrameLayout.setOnClickListener {

        }

        deletePlaylistBottomSheetFrameLayout.setOnClickListener {

        }

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }

        })

        //Observers
        viewModel.tracksForCurrentPlaylist.observe(viewLifecycleOwner) {playlistInfoContainer ->
            renderWithDatabaseData(playlistInfoContainer)
        }

    }

    //Private functions
    private fun renderWithSerializableData() {
        if (playlist != null) {

            if (playlist!!.filePath.isNotEmpty()) {
                playlistCoverImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                playlistCoverImageView.setImageURI(getUriOfImageFromStorage(playlist!!.filePath))

                playlistCoverBottomSheetImageView.setImageURI(getUriOfImageFromStorage(playlist!!.filePath))
            } else {
                playlistCoverImageView.setImageResource(R.drawable.track_placeholder_max)
                playlistCoverBottomSheetImageView.setImageResource(R.drawable.placeholder_playlist)
            }

            nameOfPlaylistTextView.text = playlist!!.name
            yearOfPlaylistCreationTextView.text = viewModel.getYearFromPlaylist(playlist!!.insertTimeStamp)
            totalNumberOfTracksTextView.text = viewModel.pluralizeWord(playlist!!.amountOfTracks, "трек")

            nameOfPlaylistBottomSheetTextView.text = playlist!!.name
            totalNumberOfTracksBottomSheetTextView.text = viewModel.pluralizeWord(playlist!!.amountOfTracks, "трек")

        } else {
            Log.d("DEBUG", "playlist is null")
        }
    }

    private fun renderWithDatabaseData(playlistInfoContainer: PlaylistInfoContainer) {

        adapter.tracks.clear()
        adapter.tracks.addAll(playlistInfoContainer.playlistTracks)
        adapter.notifyDataSetChanged()

        totalAmountOfMinutesTextView.text = playlistInfoContainer.totalTime
    }

    private fun <T : Serializable?> Bundle.getSerializableExtra(key: String, m_class: Class<T>): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.getSerializable(key, m_class)!!
        else
            this.getSerializable(key) as T
    }

    private fun getUriOfImageFromStorage(fileName: String): Uri {
        val filePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        val file = File(filePath, fileName)
        return file.toUri()
    }

    private fun hideBottomNavigation(isHide: Boolean) {
        bottomNavigationListener?.toggleBottomNavigationViewVisibility(!isHide)
    }

    companion object {
        private const val CURRENT_PLAYLIST = "CURRENT_PLAYLIST"

        fun createArgs(playlist: Playlist): Bundle {
            return bundleOf(CURRENT_PLAYLIST to playlist)
        }
    }

}