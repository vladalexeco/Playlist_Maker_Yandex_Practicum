package ru.vladalexeco.playlistmaker

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity

class SettingsActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        val backArrowImageView = findViewById<ImageView>(R.id.backArrowImageView)
        val shareAppFrameLayout = findViewById<FrameLayout>(R.id.shareAppFrameLayout)
        val supportFrameLayout = findViewById<FrameLayout>(R.id.supportFrameLayout)
        val agreementFrameLayout = findViewById<FrameLayout>(R.id.agreementFrameLayout)


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
    }
}