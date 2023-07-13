package ru.vladalexeco.playlistmaker.medialibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.databinding.FragmentFavouritesMedialibraryBinding
import ru.vladalexeco.playlistmaker.medialibrary.presentation.MedialibraryFavouritesViewModel

class MedialibraryFavouritesFragment : Fragment() {

    private val viewModel: MedialibraryFavouritesViewModel by viewModel()

    private var _binding: FragmentFavouritesMedialibraryBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavouritesMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    companion object {
        fun newInstance(): MedialibraryFavouritesFragment {
            return MedialibraryFavouritesFragment()
        }
    }

}