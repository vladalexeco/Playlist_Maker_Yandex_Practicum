package ru.vladalexeco.playlistmaker.main.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.medialibrary.ui.MediaLibraryActivity
import ru.vladalexeco.playlistmaker.search.ui.SearchingActivity
import ru.vladalexeco.playlistmaker.settings.ui.SettingsActivity

class MainActivity : AppCompatActivity() {

    private lateinit var searchButton: Button
    private lateinit var mediaLibraryButton: Button
    private lateinit var settingsButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        searchButton = findViewById(R.id.searchButton)
        mediaLibraryButton = findViewById(R.id.mediaLibraryButton)
        settingsButton = findViewById(R.id.settingsButton)

        val searchButtonClickListener: View.OnClickListener = object : View.OnClickListener {
            override fun onClick(p0: View?) {
                val intent = Intent(this@MainActivity, SearchingActivity::class.java)
                startActivity(intent)
            }
        }

        searchButton.setOnClickListener(searchButtonClickListener)

        mediaLibraryButton.setOnClickListener {
            val intent = Intent(this@MainActivity, MediaLibraryActivity::class.java)
            startActivity(intent)
        }

        settingsButton.setOnClickListener {
            val intent = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(intent)
        }
    }
}