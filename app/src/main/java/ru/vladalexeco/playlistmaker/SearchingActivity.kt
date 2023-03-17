package ru.vladalexeco.playlistmaker

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchingActivity : AppCompatActivity() {

    var textFromSearchWidget = ""

    private val baseUrl = "https://itunes.apple.com"

        companion object {
        const val EDIT_TEXT_VALUE = "EDIT_TEXT_VALUE"
    }

    val tracks = ArrayList<Track>()

    private val retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val trackService = retrofit.create(ItunesApi::class.java)

    private val adapter = TrackAdapter {
        showInfo(it)
    }

    private lateinit var inputEditText: EditText
    private lateinit var clearButton: ImageView
    private lateinit var backArrowImageView: ImageView
    private lateinit var recyclerView: RecyclerView
    private lateinit var notFoundWidget: LinearLayout
    private lateinit var badConnectionWidget: LinearLayout
    private lateinit var updateButton: Button
    private lateinit var badConnectionTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_searching)

        val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        inputEditText = findViewById(R.id.inputEditText)
        clearButton = findViewById(R.id.clearIcon)
        backArrowImageView = findViewById(R.id.backArrowImageView)
        notFoundWidget = findViewById(R.id.not_found_widget)
        badConnectionWidget = findViewById(R.id.bad_connection_widget)
        updateButton = findViewById(R.id.update_button)
        badConnectionTextView = findViewById(R.id.bad_connection)

        adapter.tracks = tracks

        clearButton.setOnClickListener {
            inputEditText.setText("")
            tracks.clear()
            adapter.notifyDataSetChanged()
            inputMethodManager.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }

        updateButton.setOnClickListener {
            search()
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
            }

            override fun afterTextChanged(p0: Editable?) {
                // some code
            }
        }

        inputEditText.addTextChangedListener(textWatcher)

        inputEditText.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                search()
                true
            }
            false
        }
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

    private fun showInfo(track: Track) {
        Toast.makeText(applicationContext, "Track ${track.trackName} on ${tracks.indexOf(track)} position", Toast.LENGTH_SHORT).show()
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
            tracks.clear()
            adapter.notifyDataSetChanged()
        } else {
            notFoundWidget.visibility = View.GONE
            badConnectionWidget.visibility = View.GONE
        }
    }

    private fun search() {
        trackService.search(text=inputEditText.text.toString())
            .enqueue(object: Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>,
                    response: Response<TrackResponse>
                ) {
                    when(response.code()) {
                        200 -> {
                            if (response.body()?.tracks?.isNotEmpty() == true) {
                                tracks.clear()
                                tracks.addAll(response.body()?.tracks!!)
                                adapter.notifyDataSetChanged()
                                showPlaceholder(null)
                            } else {
                                showPlaceholder(true)
                            }
                        }
                        else -> {
                            showPlaceholder(false, getString(R.string.server_error))
                        }
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    showPlaceholder(false, getString(R.string.bad_connection))
                }

            })
    }
}