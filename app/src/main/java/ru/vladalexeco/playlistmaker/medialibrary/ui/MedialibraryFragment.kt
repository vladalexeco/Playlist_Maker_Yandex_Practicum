package ru.vladalexeco.playlistmaker.medialibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import ru.vladalexeco.playlistmaker.databinding.FragmentMedialibraryBinding

class MedialibraryFragment : Fragment() {

    private lateinit var binding: FragmentMedialibraryBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }
}