package ru.vladalexeco.playlistmaker

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class TrackAdapter(private val trackList: ArrayList<Track>): RecyclerView.Adapter<TrackAdapter.TrackHolder>() {


    class TrackHolder(view: View): RecyclerView.ViewHolder(view) {
        var artwork = view.findViewById<ImageView>(R.id.artwork)
        var artistName = view.findViewById<TextView>(R.id.artistName)
        var trackName = view.findViewById<TextView>(R.id.trackName)
        var trackTime = view.findViewById<TextView>(R.id.trackTime)


        fun bind(track: Track) {
            Glide.with(itemView).load(track.artworkUrl).placeholder(R.drawable.placeholder).into(artwork)
            artistName.text = track.artistName
            trackName.text = track.trackName
            trackTime.text = track.trackTime
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TrackHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TrackHolder(view)
    }

    override fun onBindViewHolder(holder: TrackHolder, position: Int) {
        holder.bind(trackList[position])
    }

    override fun getItemCount(): Int = trackList.size

}



