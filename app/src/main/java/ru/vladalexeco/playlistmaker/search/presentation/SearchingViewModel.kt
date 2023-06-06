package ru.vladalexeco.playlistmaker.search.presentation

import android.os.Handler
import android.os.Looper
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vladalexeco.playlistmaker.search.data.dto.ServerResponse
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TrackHistoryInteractor
import ru.vladalexeco.playlistmaker.search.domain.interfaces.TracksInteractor
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.search.ui.models.TracksState

class SearchingViewModel(
    val tracksInteractor: TracksInteractor,
    val trackHistoryInteractor: TrackHistoryInteractor
): ViewModel() {

    private val _tracksState = MutableLiveData<TracksState>()
    val tracksState: LiveData<TracksState> = _tracksState

    private var lastSearchText: String? = null

    private val tracks = ArrayList<Track>()

    private val handler = Handler(Looper.getMainLooper())

    private val searchRunnable = Runnable {
        val newSearchText = lastSearchText ?: ""
        searchRequest(newSearchText)
    }

    fun onDestroy() {
        handler.removeCallbacks(searchRunnable)
    }

    fun searchDebounce(changedText: String) {

        this.lastSearchText = changedText
        handler.removeCallbacks(searchRunnable)
        handler.postDelayed(searchRunnable, SEARCH_DEBOUNCE_DELAY)
    }

    fun searchRequest(newSearchText: String) {
        if (newSearchText.isNotEmpty()) {

            _tracksState.postValue(
                TracksState(
                    tracks = tracks,
                    isLoading = true,
                    serverResponse = null
                )
            )

            tracksInteractor.searchTracks(
                newSearchText,
                object : TracksInteractor.TracksConsumer {
                    override fun consume(foundTracks: List<Track>?, errorMessage: ServerResponse?) {

                        handler.post {

                            if (foundTracks != null) {
                                tracks.clear()
                                tracks.addAll(foundTracks)
                            }

                            when {
                                errorMessage != null -> {
                                    _tracksState.postValue(
                                        TracksState(
                                            tracks = emptyList(),
                                            isLoading = false,
                                            serverResponse = errorMessage
                                        )
                                    )
                                }

                                tracks.isEmpty() -> {
                                    _tracksState.postValue(
                                        TracksState(
                                            tracks = emptyList(),
                                            isLoading = false,
                                            serverResponse = ServerResponse.EMPTY
                                        )
                                    )
                                }

                                else -> {
                                    _tracksState.postValue(
                                        TracksState(
                                            tracks = tracks,
                                            isLoading = false,
                                            serverResponse = ServerResponse.SUCCESS
                                        )
                                    )
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    companion object {
        private const val SEARCH_DEBOUNCE_DELAY = 2000L
    }
}