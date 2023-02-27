package ru.vladalexeco.playlistmaker

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val searchButton = findViewById<Button>(R.id.searchButton)
        val mediaLibraryButton = findViewById<Button>(R.id.mediaLibraryButton)
        val settingsButton = findViewById<Button>(R.id.settingsButton)

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