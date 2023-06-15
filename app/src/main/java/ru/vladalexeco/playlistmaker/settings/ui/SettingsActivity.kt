package ru.vladalexeco.playlistmaker.settings.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.switchmaterial.SwitchMaterial
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.App
import ru.vladalexeco.playlistmaker.KEY_FOR_APP_THEME
import ru.vladalexeco.playlistmaker.SHARED_PREFERENCES
import ru.vladalexeco.playlistmaker.settings.presentation.SettingsViewModel
import ru.vladalexeco.playlistmaker.settings.presentation.SettingsViewModelFactory

class SettingsActivity : AppCompatActivity() {

    private lateinit var backArrowImageView: ImageView
    private lateinit var shareAppFrameLayout: FrameLayout
    private lateinit var supportFrameLayout: FrameLayout
    private lateinit var agreementFrameLayout: FrameLayout
    private lateinit var themeSwitcher: SwitchMaterial

    private val viewModel: SettingsViewModel by viewModel()

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

//        viewModel = ViewModelProvider(this, SettingsViewModelFactory(this))[SettingsViewModel::class.java]

        backArrowImageView = findViewById(R.id.backArrowImageView)
        shareAppFrameLayout = findViewById(R.id.shareAppFrameLayout)
        supportFrameLayout = findViewById(R.id.supportFrameLayout)
        agreementFrameLayout = findViewById(R.id.agreementFrameLayout)
        themeSwitcher = findViewById(R.id.themeSwitcher)

        themeSwitcher.isChecked = viewModel.getThemeState()

        shareAppFrameLayout.setOnClickListener {
            val message = viewModel.getLinkToCourse()

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, message)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, null)
            startActivity(shareIntent)
        }

        supportFrameLayout.setOnClickListener {
            val message = viewModel.getEmailMessage()
            val subject = viewModel.getEmailSubject()
            val mailArray = viewModel.getArrayOfEmailAddresses()

            val shareIntent = Intent(Intent.ACTION_SENDTO)
            shareIntent.data = Uri.parse("mailto:")
            shareIntent.putExtra(Intent.EXTRA_EMAIL, mailArray)
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, subject)
            shareIntent.putExtra(Intent.EXTRA_TEXT, message)
            startActivity(shareIntent)
        }

        agreementFrameLayout.setOnClickListener {
            val url = viewModel.getPracticumOffer()

            val browserIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
            startActivity(browserIntent)
        }

        backArrowImageView.setOnClickListener {
            finish()
        }

        themeSwitcher.setOnCheckedChangeListener { switcher, checked ->
            viewModel.saveAndChangeThemeState(checked)
        }
    }
}