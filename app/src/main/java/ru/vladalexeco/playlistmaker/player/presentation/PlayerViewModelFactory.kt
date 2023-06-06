package ru.vladalexeco.playlistmaker.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vladalexeco.playlistmaker.player.domain.models.TrackUrl
import ru.vladalexeco.playlistmaker.util.Creator

class PlayerViewModelFactory(private val trackUrl: TrackUrl): ViewModelProvider.Factory {

    val audioPlayerInteractor = Creator.provideAudioPlayerInteractor(trackUrl)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return PlayerViewModel(audioPlayerInteractor) as T
    }
}