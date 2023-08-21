package ru.vladalexeco.playlistmaker.new_playlist.ui

import android.Manifest
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.android.material.textfield.TextInputEditText
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.Dispatcher
import ru.vladalexeco.playlistmaker.databinding.FragmentNewPlaylistBinding
import ru.vladalexeco.playlistmaker.new_playlist.presentation.NewPlaylistViewModel
import ru.vladalexeco.playlistmaker.root.listeners.BottomNavigationListener
import org.koin.androidx.viewmodel.ext.android.viewModel
import ru.vladalexeco.playlistmaker.new_playlist.domain.models.Playlist
import java.io.File
import java.io.FileOutputStream

class NewPlaylistFragment : Fragment() {

    private var bottomNavigationListener: BottomNavigationListener? = null

    private lateinit var binding: FragmentNewPlaylistBinding

    private val viewModel: NewPlaylistViewModel by viewModel()

    private val requester = PermissionRequester.instance()

    private var imageIsLoaded = false

    private var uriOfImage: Uri? = null

    private lateinit var backArrowImageView: ImageView
    private lateinit var loadImageImageView: ImageView
    private lateinit var editNameEditText: TextInputEditText
    private lateinit var editDescriptionEditText: TextInputEditText
    private lateinit var newPlayListButton: Button

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BottomNavigationListener) {
            bottomNavigationListener = context
        } else {
            throw IllegalArgumentException("Activity must implement BottomNavigationListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        bottomNavigationListener = null
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentNewPlaylistBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {

            override fun handleOnBackPressed() {
                checkForDialogOutput()
            }

        })

        backArrowImageView = binding.backArrowNewPlaylist
        loadImageImageView = binding.loadImageImageview
        editNameEditText = binding.editNameNewPlaylist
        editDescriptionEditText = binding.editDescriptionNewPlaylist
        newPlayListButton = binding.newPlaylistButton

        newPlayListButton.isEnabled = false

        editNameEditText.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                //
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                enableNewPlaylistButton(p0.toString())
            }

            override fun afterTextChanged(p0: Editable?) {
                //
            }

        })

        val pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                //обрабатываем событие выбора пользователем фотографии
                if (uri != null) {
                    loadImageImageView.setImageURI(uri)
                    imageIsLoaded = true
                    uriOfImage = uri
                } else {
                    Log.d("PhotoPicker", "No media selected")
                }
            }


        backArrowImageView.setOnClickListener {
            checkForDialogOutput()
        }

        loadImageImageView.setOnClickListener {

            viewLifecycleOwner.lifecycleScope.launch {
                requester.request(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ).collect { result ->
                    when(result) {
                        is PermissionResult.Granted -> {} // Пользователь дал разрешение, можно продолжать работу

                        is PermissionResult.Denied -> {

                            Toast.makeText(
                                requireContext(),
                                "Разрешение требуется, что вы могли загружать обложки для своих плейлистов",
                                Toast.LENGTH_SHORT
                            ).show()

//                            val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
//                            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//                            intent.data= Uri.fromParts("package", context?.packageName ?: "", null)
//                            context?.startActivity(intent)

                        }// Пользователь отказал в предоставлении разрешения

                        is PermissionResult.Cancelled -> {

                            Toast.makeText(requireContext(), "Canceled", Toast.LENGTH_SHORT).show()

                        }// Запрос на разрешение отменён
                    }
                }
            }

            val granted = requester.areGranted(
                Manifest.permission.READ_EXTERNAL_STORAGE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE
            )

            if (granted) {
                pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))
            }

        }

        newPlayListButton.setOnClickListener {

            val name = editNameEditText.text.toString()

            val filepath = if (uriOfImage != null) viewModel.getNameForFile(editNameEditText.text.toString()) else ""

            val playlist = Playlist(
                name = name,
                description = editDescriptionEditText.text.toString(),
                filePath = filepath,
                listOfTracksId = "",
                amountOfTracks = 0
            )

            viewLifecycleOwner.lifecycleScope.launch {
                viewModel.insertPlaylistToDatabase(playlist)
            }

            uriOfImage?.let { saveImageToPrivateStorage(uri = it, nameOfFile = filepath) }

            Toast.makeText(requireContext(), "Плейлист $name успешно добавлен", Toast.LENGTH_SHORT).show()

            findNavController().navigateUp()
        }

    }

    override fun onResume() {
        super.onResume()
        hideBottomNavigation(true)
    }

    override fun onStop() {
        super.onStop()
        hideBottomNavigation(false)
    }

    private fun hideBottomNavigation(isHide: Boolean) {
        bottomNavigationListener?.toggleBottomNavigationViewVisibility(!isHide)
    }

    private fun enableNewPlaylistButton(text: String?) {
        newPlayListButton.isEnabled = !text.isNullOrEmpty()
    }

    private fun checkForDialogOutput() {
        if (imageIsLoaded ||
            editNameEditText.text.toString().isNotEmpty() ||
            editDescriptionEditText.text.toString().isNotEmpty()
        ) {
            showDialog()
        } else {
            findNavController().navigateUp()
        }
    }

    private fun showDialog() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Завершить создание плейлиста?")
            .setMessage("Все несохраненные данные будут потеряны")
            .setNeutralButton("Отмена") { dialog, which ->

            }
            .setPositiveButton("Завершить") { dialog, which ->
                findNavController().navigateUp()
            }
            .show()
    }

    private fun showDialogExistName() {
        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Уведомление")
            .setMessage("Плейлист с таким именем уже существует. Поменяйте имя.")
            .setPositiveButton("Ок") { dialog, which ->

            }
            .show()
    }

    private fun saveImageToPrivateStorage(uri: Uri, nameOfFile: String) {
        //создаём экземпляр класса File, который указывает на нужный каталог
        val filePath = File(requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES), "myalbum")
        //создаем каталог, если он не создан
        if (!filePath.exists()){
            filePath.mkdirs()
        }
        //создаём экземпляр класса File, который указывает на файл внутри каталога
        val file = File(filePath, nameOfFile)

        // создаём входящий поток байтов из выбранной картинки
        val inputStream = requireActivity().contentResolver.openInputStream(uri)
        // создаём исходящий поток байтов в созданный выше файл
        val outputStream = FileOutputStream(file)
        // записываем картинку с помощью BitmapFactory
        BitmapFactory
            .decodeStream(inputStream)
            .compress(Bitmap.CompressFormat.JPEG, 30, outputStream)

        Log.d("d", "Сохранено в по адресу ${file.absolutePath}")
    }

}