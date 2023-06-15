package ru.vladalexeco.playlistmaker.player.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vladalexeco.playlistmaker.player.domain.models.PlayerTrack
import ru.vladalexeco.playlistmaker.util.Creator

class PlayerViewModelFactory(private val playerTrack: PlayerTrack): ViewModelProvider.Factory {

//    private val audioPlayerInteractor = Creator.provideAudioPlayerInteractor(playerTrack)
//
//    override fun <T : ViewModel> create(modelClass: Class<T>): T {
//        return PlayerViewModel(playerTrack, audioPlayerInteractor) as T
//    }
}