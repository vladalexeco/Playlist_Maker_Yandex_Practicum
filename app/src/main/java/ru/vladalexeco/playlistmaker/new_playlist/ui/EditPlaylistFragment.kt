package ru.vladalexeco.playlistmaker.new_playlist.ui

import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.launch
import ru.vladalexeco.playlistmaker.R
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import ru.vladalexeco.playlistmaker.playlist_info.ui.PlaylistInfoFragment
import java.io.File
import java.io.Serializable

class EditPlaylistFragment: BaseFragment() {

    var playlist: Playlist? = null

    private lateinit var newPlaylistHeader: TextView

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                findNavController().navigateUp()
            }

        })

        initViews()

        newPlaylistHeader = binding.newPlaylistHeader

        editNameEditText.addTextChangedListener(textWatcher)

        val pickMedia = pickMediaCommon

        playlist = requireArguments().getSerializableExtra(EDIT_PLAYLIST, Playlist::class.java)

        renderWithSerializableData()

        backArrowImageView.setOnClickListener {
            findNavController().navigateUp()
        }

        loadImageImageView.setOnClickListener {
            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
        }

        newPlayListButton.setOnClickListener {
            editPlaylist()
        }

    }

    private fun renderWithSerializableData() {
        if (playlist != null) {

            newPlaylistHeader.text = getString(R.string.edit_current_playlist)

            if (playlist!!.filePath.isNotEmpty()) {
                loadImageImageView.scaleType = ImageView.ScaleType.CENTER_CROP
                loadImageImageView.setImageURI(getUriOfImageFromStorage(playlist!!.filePath))
            } else {
                loadImageImageView.scaleType = ImageView.ScaleType.CENTER
                loadImageImageView.setImageResource(R.drawable.load_picture_placeholder)
            }

            editNameEditText.setText(playlist!!.name)
            editDescriptionEditText.setText(playlist!!.description)
            newPlayListButton.text = getString(R.string.save_button_edit)

        }


    }

    private fun editPlaylist() {

        val filepath = if (uriOfImage != null) viewModel.getNameForFile(editNameEditText.text.toString()) else playlist!!.filePath

        val updatedPlaylist = playlist?.copy(
            name = editNameEditText.text.toString(),
            description = editDescriptionEditText.text.toString(),
            filePath = filepath,
            insertTimeStamp = System.currentTimeMillis()
        )

        viewLifecycleOwner.lifecycleScope.launch {
            if (updatedPlaylist != null) {
                viewModel.insertPlaylistToDatabase(updatedPlaylist)
            }
        }

        uriOfImage?.let { saveImageToPrivateStorage(uri = it, nameOfFile = filepath) }

        if (uriOfImage != null) {
            deleteOldFile(playlist!!.filePath)
        }

        if (updatedPlaylist != null) {
            findNavController().navigate(
                R.id.action_editPlaylistFragment_to_playlistInfoFragment,
                PlaylistInfoFragment.createArgs(updatedPlaylist)
            )
        }
    }

    private fun <T : Serializable?> Bundle.getSerializableExtra(key: String, m_class: Class<T>): T {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU)
            this.getSerializable(key, m_class)!!
        else
            this.getSerializable(key) as T
    }

    private fun getUriOfImageFromStorage(fileName: String): Uri {
        val filePath = File(requireContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        val file = File(filePath, fileName)
        return file.toUri()
    }

    private fun deleteOldFile(nameOfFile: String) {
        val filePath = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        val file = File(filePath, nameOfFile)

        file.delete()
    }

    companion object {
        const val EDIT_PLAYLIST = "EDIT_PLAYLIST"

        fun createArgs(playlist: Playlist): Bundle {
            return bundleOf(EDIT_PLAYLIST to playlist)
        }
    }

}