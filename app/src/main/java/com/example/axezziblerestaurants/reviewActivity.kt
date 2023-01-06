package com.example.axezziblerestaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.w3c.dom.Text

lateinit var restaurant: Restaurant
lateinit var reviewRecyclerView: RecyclerView

class reviewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_review)
        //Get data from the other activity
        val intent = intent
        val restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION_KEY, POSITION_NOT_SET) //Get the restaurant position from calling activity
        restaurant = DataManager.restaurants[restaurantPosition] //The restaurant passed from the other activity
        val reviewList = ArrayList<Review>()
        val review = Review(restaurant.reviewerName, restaurant.review, restaurant.rating)
        reviewList.add(review)
        val reviewHeader = findViewById<TextView>(R.id.reviewHeader)
        reviewHeader.text = reviewHeader.text.toString().plus(" ".plus(restaurant.name))
        reviewRecyclerView = findViewById(R.id.reviewRecycleView)
        reviewRecyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager
        reviewRecyclerView.adapter = ReviewRecycleAdapter(this,reviewList) //Attach data to the recyclerview
        val returnButton = findViewById<Button>(R.id.goBackButton)  //get return button
        returnButton.setOnClickListener{
            finish() //Return to other activity
        }
    }
}
