package com.example.axezziblerestaurants

import com.google.android.gms.maps.model.LatLng

data class PlaceInfo(
    val name: String = "",
    val type: String = "",
    val imageURL: String = "",
    val position: LatLng = LatLng(0.0,0.0),
    val accessible: Boolean = false,
    val guideDogsAllowed: Boolean = false
)

