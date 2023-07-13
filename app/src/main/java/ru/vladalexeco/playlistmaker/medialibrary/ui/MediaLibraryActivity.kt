package ru.vladalexeco.playlistmaker.medialibrary.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.tabs.TabLayoutMediator
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.databinding.ActivityMedialibraryBinding

class MediaLibraryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMedialibraryBinding
    private lateinit var tabLayoutMediator: TabLayoutMediator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMedialibraryBinding.inflate(layoutInflater)

        setContentView(binding.root)

        binding.backArrowImageView.setOnClickListener {
            finish()
        }

        binding.viewPager.adapter = MedialibraryViewPageAdapter(supportFragmentManager, lifecycle)

        tabLayoutMediator = TabLayoutMediator(binding.tabLayout, binding.viewPager) {tab, position ->
            when (position) {
                0 -> tab.text = getString(R.string.favourites_tracks)
                1 -> tab.text = getString(R.string.playlists)
            }
        }

        tabLayoutMediator.attach()
    }

    override fun onDestroy() {
        super.onDestroy()
        tabLayoutMediator.detach()
    }
}