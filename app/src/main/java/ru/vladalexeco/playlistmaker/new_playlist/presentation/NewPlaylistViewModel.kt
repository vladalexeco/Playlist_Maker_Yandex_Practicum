package ru.vladalexeco.playlistmaker.new_playlist.presentation

import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ru.vladalexeco.playlistmaker.new_playlist.domain.db.PlaylistDatabaseInteractor
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class NewPlaylistViewModel(
    private val playlistDatabaseInteractor: PlaylistDatabaseInteractor
) : ViewModel() {

    suspend fun insertPlaylistToDatabase(playlist: Playlist) {
        playlistDatabaseInteractor.insertPlaylistToDatabase(playlist)
    }

    fun getNameForFile(nameOfPlaylist: String): String {

        val currentDateTime = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HH:mm:ss")
        val formattedDateTime = currentDateTime.format(formatter)

        val result = nameOfPlaylist.replace(" ", "_")
        return "${result}_${formattedDateTime}.jpg"
    }

}