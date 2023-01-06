package com.example.mapsintro

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.axezziblerestaurants.R
import com.example.axezziblerestaurants.Restaurant
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PlacesInfoAdapter(val context : Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)

    @RequiresApi(Build.VERSION_CODES.S)
    override fun getInfoContents(p0:Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.restaurant_map_info, null)
        //val dogsAllowed = infoWindow.findViewById<ImageView>(R.id.mapInfoGuideDogsAllowedImageView)
        //val accessibleRestaurant = infoWindow.findViewById<ImageView>(R.id.mapInfoAccessibleImageView)
        //val restaurantImage = infoWindow.findViewById<ImageView>(R.id.mapInfoImage)
        val nameView = infoWindow.findViewById<TextView>(R.id.mapInfoName)
        val typeView = infoWindow.findViewById<TextView>(R.id.mapInfoType)
        val rating = infoWindow.findViewById<RatingBar>(R.id.infoRatingBar)
        val restaurant = p0.tag as? Restaurant

        //Set title and images on mapPopup
        nameView.text = restaurant?.name
        typeView.text = restaurant?.type
        rating.rating = restaurant?.rating?.toFloat() ?: 0.0.toFloat()
        return infoWindow
    }

    //Get the extraInfo Window
    @RequiresApi(Build.VERSION_CODES.S)
    override fun getInfoWindow(marker : Marker): View? {
        return null
    }
}

