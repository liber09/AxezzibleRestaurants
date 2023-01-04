package com.example.mapsintro

import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.example.axezziblerestaurants.PlaceInfo
import com.example.axezziblerestaurants.R
import com.example.axezziblerestaurants.Restaurant
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class PlacesInfoAdapter(val context : Context) : GoogleMap.InfoWindowAdapter {

    val layoutInflater = LayoutInflater.from(context)

    override fun getInfoContents(p0: Marker): View? {
        return null
    }

    //Get the extraInfo Window
    @RequiresApi(Build.VERSION_CODES.S)
    override fun getInfoWindow(marker : Marker): View? {
        val infoWindow = layoutInflater.inflate(R.layout.restaurant_map_info, null)
        val dogsAllowed = infoWindow.findViewById<ImageView>(R.id.mapInfoGuideDogsAllowedImageView)
        val accessibleRestaurant = infoWindow.findViewById<ImageView>(R.id.mapInfoAccessibleImageView)
        val restaurantImage = infoWindow.findViewById<ImageView>(R.id.mapInfoImage)
        val nameView = infoWindow.findViewById<TextView>(R.id.mapInfoName)
        val typeView = infoWindow.findViewById<TextView>(R.id.mapInfoType)
        val restaurant = marker.tag as? Restaurant

        //Set title and images on mapPopup
        nameView.text = restaurant?.name
        typeView.text = restaurant?.type
        if(restaurant?.imageName?.isNotEmpty() == true) {
            val imageref = Firebase.storage.reference.child(restaurant?.imageName)
            imageref.downloadUrl.addOnSuccessListener { Uri ->
                val imageURL = Uri.toString() // get the URL for the image
                //Use third party product glide to load the image into the imageview
                Glide.with(context)
                    .load(imageURL)
                    .into(restaurantImage)
            }

        }
        //Set the correct image on guideDogs and Accessibility images depending on database val
        val pack = this.javaClass.packageName
        if(restaurant?.guideDogsAllowed == true){
            val uriImage = "@drawable/".plus("guidedogs_allowed") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            dogsAllowed.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("dogs_not_allowed") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            dogsAllowed.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }
        if(restaurant?.accessible == true){
            val uriImage = "@drawable/".plus("accessible") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            accessibleRestaurant.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("not_accessible") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null,pack) //Get the actual image
            accessibleRestaurant.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }
        return infoWindow
    }
}