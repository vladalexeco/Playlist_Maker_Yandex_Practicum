package ru.vladalexeco.playlistmaker.medialibrary.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayoutMediator
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.databinding.FragmentMedialibraryBinding

class MedialibraryFragment : Fragment() {

    private lateinit var binding: FragmentMedialibraryBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMedialibraryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.viewPager.adapter = MedialibraryViewPageAdapter(childFragmentManager, lifecycle)

        tabLayoutMediator =
            TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
                when (position) {
                    0 -> tab.text = getString(R.string.favourites_tracks)
                    1 -> tab.text = getString(R.string.playlists)
                }
            }

        tabLayoutMediator.attach()

    }


    override fun onDestroyView() {
        tabLayoutMediator.detach()
        super.onDestroyView()
    }


}
