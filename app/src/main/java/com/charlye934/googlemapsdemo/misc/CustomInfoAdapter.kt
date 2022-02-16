package com.charlye934.googlemapsdemo.misc

import android.app.Activity
import android.content.Context
import android.view.View
import android.widget.TextView
import com.charlye934.googlemapsdemo.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class CustomInfoAdapter(context: Context): GoogleMap.InfoWindowAdapter {

    private val contentView = (context as Activity).layoutInflater.inflate(R.layout.custome_info_window, null)

    private fun renderViews(marker: Marker?, contentView: View){
        val title = marker?.title
        val description = marker?.snippet

        val titleTextView = contentView.findViewById<TextView>(R.id.title_textView)
        titleTextView.text = title

        val descriptionTextView = contentView.findViewById<TextView>(R.id.description_textView)
        descriptionTextView.text = description
    }

    override fun getInfoContents(p0: Marker): View? {
        renderViews(p0, contentView)
        return contentView
    }

    override fun getInfoWindow(p0: Marker): View? {
        renderViews(p0, contentView)
        return contentView
    }
}