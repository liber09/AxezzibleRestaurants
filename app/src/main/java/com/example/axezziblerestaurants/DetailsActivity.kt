package com.example.axezziblerestaurants

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.google.type.PostalAddress

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener{
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            startActivity(mainActivity) //Go to mainActivity
        }
        val intent = intent
        val restaurant = intent.getSerializableExtra("restaurant") as Restaurant
        val nameTextView = findViewById<TextView>(R.id.detailsNameTextView)
        val addressTextView = findViewById<TextView>(R.id.detailsAddressTextView)
        val postalAddressTextView = findViewById<TextView>(R.id.detailsPostalAddressTextView)
        val ratingView = findViewById<RatingBar>(R.id.detailsRatingBar)
        val typeTextView = findViewById<TextView>(R.id.detailsTypeTextView)
        val guideDogImageView = findViewById<ImageView>(R.id.detailsGuideDogImageView)
        val accessibleImageView = findViewById<ImageView>(R.id.detailsAccessibleImageView)
        nameTextView.text = restaurant.name
        typeTextView.text = restaurant.type
        addressTextView.text = restaurant.address
        postalAddressTextView.text = restaurant.postalCode.toString().plus(" ".plus(restaurant.city))
        ratingView.rating = restaurant.rating.toFloat()
        if(restaurant.guideDogsAllowed){
            val uriImage = "@drawable/".plus("guidedogs_allowed") //Get filePath
            val imageResource = resources.getIdentifier(uriImage, null, packageName) //Get the actual image
            guideDogImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("dogs_not_allowed") //Get filePath
            val imageResource = resources.getIdentifier(uriImage, null, packageName) //Get the actual image
            guideDogImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource)) //Show the image on screen
        }
        if(restaurant.accessible){
            val uriImage = "@drawable/".plus("accessible") //Get filePath
            val imageResource = resources.getIdentifier(uriImage, null, packageName) //Get the actual image
            accessibleImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("not_accessible") //Get filePath
            val imageResource = resources.getIdentifier(uriImage, null, packageName) //Get the actual image
            accessibleImageView.setImageBitmap(BitmapFactory.decodeResource(getResources(), imageResource)) //Show the image on screen
        }
    }
}