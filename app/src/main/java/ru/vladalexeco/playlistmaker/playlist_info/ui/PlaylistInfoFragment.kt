package ru.vladalexeco.playlistmaker.playlist_info.ui

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView
import ru.vladalexeco.playlistmaker.databinding.FragmentPlaylistInfoBinding
import ru.vladalexeco.playlistmaker.playlist_info.presentation.PlaylistInfoViewModel
import ru.vladalexeco.playlistmaker.root.listeners.BottomNavigationListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist

class PlaylistInfoFragment : Fragment() {

    private var bottomNavigationListener: BottomNavigationListener? = null

    private lateinit var binding: FragmentPlaylistInfoBinding

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

    }

    companion object {
        private const val CURRENT_PLAYLIST = "CURRENT_PLAYLIST"

        fun createArgs(playlist: Playlist): Bundle {
            return bundleOf(CURRENT_PLAYLIST to playlist)
        }
    }

}