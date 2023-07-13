package ru.vladalexeco.playlistmaker.medialibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.databinding.FragmentPlaylistsMedialibraryBinding
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryPlaylistsViewModel

class MedialibraryPlaylistsFragment : Fragment() {

    private val viewModel: MedialibraryPlaylistsViewModel by viewModel()

    private var _binding: FragmentPlaylistsMedialibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPlaylistsMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): MedialibraryPlaylistsFragment {
            return MedialibraryPlaylistsFragment()
        }
    }

}