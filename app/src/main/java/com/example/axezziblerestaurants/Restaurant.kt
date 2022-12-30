package com.example.axezziblerestaurants

import java.io.Serializable

//Need to assign default values to all properties or fireStore get will crash
class Restaurant(
    val name: String,
    val type: String,
    val guideDogsAllowed: Boolean,
    val accessible: Boolean,
    val address: String,
    val postalCode: Int,
    val city: String,
    val imageName: String,
    val rating: Double,
    val phoneNumber: String,
    val eMail: String,
    val webUrl: String)