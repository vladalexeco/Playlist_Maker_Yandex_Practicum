package ru.vladalexeco.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.widget.FrameLayout
import android.widget.ImageSwitcher
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsActivity : AppCompatActivity() {

    private lateinit var backArrowImageView: ImageView
    private lateinit var shareAppFrameLayout: FrameLayout
    private lateinit var supportFrameLayout: FrameLayout
    private lateinit var agreementFrameLayout: FrameLayout
    private lateinit var themeSwitcher: SwitchMaterial

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, MODE_PRIVATE)

        backArrowImageView = findViewById(R.id.backArrowImageView)
        shareAppFrameLayout = findViewById(R.id.shareAppFrameLayout)
        supportFrameLayout = findViewById(R.id.supportFrameLayout)
        agreementFrameLayout = findViewById(R.id.agreementFrameLayout)
        themeSwitcher = findViewById(R.id.themeSwitcher)

        themeSwitcher.isChecked = sharedPreferences.getBoolean(KEY_FOR_APP_THEME, false)

        shareAppFrameLayout.setOnClickListener {
            val message = getString(R.string.course_android_developer)

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        supportFrameLayout.setOnClickListener {
            val message = getString(R.string.mail_message)
            val subject = getString(R.string.mail_subject)
            val mailArray = arrayOf("vladalexeco@yandex.ru")

            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, mailArray)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }

        agreementFrameLayout.setOnClickListener {
            val url = getString(R.string.practicum_offer)

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        backArrowImageView.setOnClickListener {
            finish()
        }

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            (applicationContext as App).switchTheme(checked)

            sharedPreferences.edit()
                .putBoolean(KEY_FOR_APP_THEME, checked)
                .apply()
        }
    }
}