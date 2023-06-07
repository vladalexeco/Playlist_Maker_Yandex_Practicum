 package ru.vladalexeco.playlistmaker.search.ui

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ru.vladalexeco.playlistmaker.*
import ru.vladalexeco.playlistmaker.util.ServerResponse
import ru.vladalexeco.playlistmaker.search.domain.models.Track
import ru.vladalexeco.playlistmaker.player.ui.PlayerActivity
import ru.vladalexeco.playlistmaker.search.presentation.SearchingViewModel
import ru.vladalexeco.playlistmaker.search.presentation.SearchingViewModelFactory
import ru.vladalexeco.playlistmaker.search.ui.models.TracksState

const val KEY_FOR_PLAYER = "key_for_player"

class SearchingActivity : AppCompatActivity() {

    var textFromSearchWidget = ""

    private lateinit var viewModel: SearchingViewModel

    companion object {
        const val EDIT_TEXT_VALUE = "EDIT_TEXT_VALUE"
        private const val CLICK_DEBOUNCE_DELAY = 1000L
    }

    private val handler = Handler(Looper.getMainLooper())

    private var isClickAllowed = true

    private val adapter = TrackAdapter {
        if (clickDebounce()) {
            clickToTrackList(it)
        }
    }

    private val historyAdapter = TrackAdapter {
        if (clickDebounce()) {
            clickToHistoryTrackList(it)
        }
    }

    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backArrowImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var notFoundWidget: LinearLayout
    private lateinit var badConnectionWidget: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var badConnectionTextView: TextView
    private lateinit var historyWidget: LinearLayout
    private lateinit var historyRecyclerView: RecyclerView
    private lateinit var clearHistoryButton: Button
    private lateinit var progressBar: ProgressBar

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        viewModel = ViewModelProvider(this, SearchingViewModelFactory(this))[SearchingViewModel::class.java]

        viewModel.tracksState.observe(this) { tracksState ->
            render(tracksState)
        }

         val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        historyRecyclerView = findViewById(R.id.history_recycle_view)
        historyRecyclerView.layoutManager = LinearLayoutManager(this)
        historyRecyclerView.adapter = historyAdapter

        viewModel.historyList.observe(this) { historyList ->
            historyAdapter.tracks = historyList
        }

        inputEditText = findViewById(R.id.inputEditText)
        clearButton = findViewById(R.id.clearIcon)
        backArrowImageView = findViewById(R.id.backArrowImageView)
        notFoundWidget = findViewById(R.id.not_found_widget)
        badConnectionWidget = findViewById(R.id.bad_connection_widget)
        updateButton = findViewById(R.id.update_button)
        badConnectionTextView = findViewById(R.id.bad_connection)
        historyWidget = findViewById(R.id.history_widget)
        clearHistoryButton = findViewById(R.id.clear_history_button)
        progressBar = findViewById(R.id.progressBar)

        clearHistoryButton.setOnClickListener {
//            viewModel.trackHistoryInteractor.clearHistoryList()
            viewModel.clearHistoryList()
            adapter.notifyDataSetChanged()
            historyWidget.visibility = View.GONE
        }

        // On Focus Actions
        inputEditText.setOnFocusChangeListener { view, hasFocus ->
            historyWidget.visibility = if (hasFocus && inputEditText.text.isEmpty() && viewModel.getHistoryList().isNotEmpty()) View.VISIBLE else View.GONE
        }

        clearButton.setOnClickListener {
            inputEditText.setText("")
            adapter.tracks.clear()
            adapter.notifyDataSetChanged()
            inputMethodManager.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        updateButton.setOnClickListener {
            viewModel.searchRequest(inputEditText.text.toString())
        }

        backArrowImageView.setOnClickListener {
            finish()
        }

        val textWatcher = object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                // some code
            }

            override fun onTextChanged(s: CharSequence?, p1: Int, p2: Int, p3: Int) {
                clearButton.visibility = clearButtonVisibility(s)
                textFromSearchWidget = inputEditText.text.toString()

                // On Focus Actions
                historyWidget.visibility = if (inputEditText.hasFocus() && s?.isEmpty() == true && viewModel.getHistoryList().isNotEmpty()) View.VISIBLE else View.GONE

                viewModel.searchDebounce(
                    changedText = s?.toString() ?: ""
                )
            }

            override fun afterTextChanged(p0: Editable?) {
                // some code
            }
        }

        inputEditText.addTextChangedListener(textWatcher)

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                viewModel.searchRequest(inputEditText.text.toString())
                true
            }
            false
        }
    }

    override fun onStop() {
        super.onStop()
        viewModel.saveHistoryList()
    }

    override fun onDestroy() {
        viewModel.onDestroy()
        super.onDestroy()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(EDIT_TEXT_VALUE, textFromSearchWidget)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        inputEditText.setText(savedInstanceState.getString(EDIT_TEXT_VALUE, ""))
    }

    private fun clearButtonVisibility(s:CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private fun clickToTrackList(track: Track) {
        viewModel.addTrackToHistoryList(track)
        historyAdapter.notifyDataSetChanged()

        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(KEY_FOR_PLAYER, track)
        startActivity(intent)
    }

    private fun clickToHistoryTrackList(track: Track) {
        val intent = Intent(this, PlayerActivity::class.java)
        intent.putExtra(KEY_FOR_PLAYER, track)
        startActivity(intent)
    }

    private fun showPlaceholder(flag: Boolean?, message: String = "") {
        if (flag != null) {
            if (flag == true) {
                badConnectionWidget.visibility = View.GONE
                notFoundWidget.visibility = View.VISIBLE
            } else {
                notFoundWidget.visibility = View.GONE
                badConnectionWidget.visibility = View.VISIBLE
                badConnectionTextView.text = message
            }
            adapter.tracks.clear()
            adapter.notifyDataSetChanged()
        } else {
            notFoundWidget.visibility = View.GONE
            badConnectionWidget.visibility = View.GONE
        }
    }

    private fun clickDebounce(): Boolean {
        val current = isClickAllowed
        if (isClickAllowed) {
            isClickAllowed = false
            handler.postDelayed({isClickAllowed = true}, CLICK_DEBOUNCE_DELAY)
        }

        return current
    }


    private fun render(tracksState: TracksState) {
        when {

            tracksState.isLoading -> showLoading(true)

            else -> {

                showLoading(false)

                when (tracksState.serverResponse) {

                    ServerResponse.SUCCESS -> {
                        adapter.tracks.clear()
                        adapter.tracks.addAll(tracksState.tracks)
                        adapter.notifyDataSetChanged()
                        showPlaceholder(null)
                    }

                    ServerResponse.EMPTY -> {
                        showPlaceholder(true)
                    }

                    ServerResponse.DISCONNECT -> {
                        showPlaceholder(false, getString(R.string.bad_connection))
                    }

                    ServerResponse.FAILED -> {
                        showPlaceholder(false, getString(R.string.server_error))
                    }

                    else -> {
                        Log.d("SERVER", "Something went wrong")
                    }

                }
            }
        }
    }

    private fun showLoading(isLoaded: Boolean) {
        progressBar.visibility = if (isLoaded) View.VISIBLE else View.GONE
    }
}