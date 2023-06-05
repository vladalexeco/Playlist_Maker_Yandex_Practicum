package ru.vladalexeco.playlistmaker.search.presentation

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.vladalexeco.playlistmaker.util.Creator

class SearchingViewModelFactory(context: Context): ViewModelProvider.Factory {

    private val tracksInteractor = Creator.provideTracksInteractor(context)
    private val trackHistoryInteractor = Creator.provideTrackHistoryInteractor(context)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SearchingViewModel(tracksInteractor, trackHistoryInteractor) as T
    }
}