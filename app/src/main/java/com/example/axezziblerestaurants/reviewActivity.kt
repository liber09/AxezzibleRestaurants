package com.example.axezziblerestaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RatingBar
import android.widget.TextView
import org.w3c.dom.Text

lateinit var restaurant: Restaurant

class reviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        val intent = intent
        val restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION_KEY, POSITION_NOT_SET) //Get the restaurant position from calling activity
        restaurant = DataManager.restaurants[restaurantPosition]
        val reviewTextView = findViewById<TextView>(R.id.reviewEditText)
        reviewTextView.text = restaurant.review
        val reviewBarView = findViewById<RatingBar>(R.id.reviewRatingBar)
        reviewBarView.rating = restaurant.rating.toFloat()
    }
}