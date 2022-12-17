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
        //Get the return to main button
        val returnButton = findViewById<Button>(R.id.returnButton)
        //Set clickListener
        returnButton.setOnClickListener{
            //Get the main activity we wanna go to
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            //Launch main activity
            startActivity(mainActivity) //Go to mainActivity
        }
        val intent = intent
        val restaurant = intent.getSerializableExtra("restaurant") as Restaurant //Get the restaurant that was passed in from other activity
        //Get all the component views so we can add data to them
        val nameTextView = findViewById<TextView>(R.id.detailsNameTextView)
        val addressTextView = findViewById<TextView>(R.id.detailsAddressTextView)
        val postalAddressTextView = findViewById<TextView>(R.id.detailsPostalAddressTextView)
        val ratingView = findViewById<RatingBar>(R.id.detailsRatingBar)
        val typeTextView = findViewById<TextView>(R.id.detailsTypeTextView)
        val guideDogImageView = findViewById<ImageView>(R.id.detailsGuideDogImageView)
        val accessibleImageView = findViewById<ImageView>(R.id.detailsAccessibleImageView)
        //Add data to the component views
        nameTextView.text = restaurant.name
        typeTextView.text = restaurant.type
        addressTextView.text = restaurant.address
        postalAddressTextView.text = restaurant.postalCode.toString().plus(" ".plus(restaurant.city))
        ratingView.rating = restaurant.rating.toFloat()
        //Set the correct image on guideDogs and Accessibility images depending on database val
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