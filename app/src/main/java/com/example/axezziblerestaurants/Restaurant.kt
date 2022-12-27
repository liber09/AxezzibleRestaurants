package com.example.axezziblerestaurants

import java.io.Serializable

//Need to assign default values to all properties or fireStore get will crash
class Restaurant(
    val name: String = "",
    val type: String = "",
    val guideDogsAllowed: Boolean = false,
    val accessible: Boolean = false,
    val address: String = "",
    val postalCode: Int = 0,
    val city: String = "",
    val imageName: String = "",
    val rating: Double = 0.0,
    val id: Int = 0)