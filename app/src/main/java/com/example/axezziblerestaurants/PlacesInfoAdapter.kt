package com.example.mapsintro

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.example.axezziblerestaurants.PlaceInfo
import com.example.axezziblerestaurants.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker

class PlacesInfoAdapter(val context : Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)

    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    override fun getInfoWindow(marker : Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.restaurant_map_info, null)

        val imageView = infoWindow.findViewById<ImageView>(R.id.mapInfoGuideDogsAllowedImageView)
        val titleView = infoWindow.findViewById<TextView>(R.id.infoNameTextView)
        val infoView = infoWindow.findViewById<TextView>(R.id.infoTextView)

        val place = marker.tag as? PlaceInfo

        titleView.text = place?.name
        infoView.text = place?.info
        if (place != null) {
            imageView.setImageResource(place.image)
        }

        return infoWindow
    }
}