package com.example.axezziblerestaurants

import android.content.Intent
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

const val RESTAURANT_POSITION_KEY = "RESTAURANT_POSITION"
const val POSITION_NOT_SET = -1
class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        //Get the return to main button
        val returnButton = findViewById<Button>(R.id.returnButton)
        val showOnMapButton = findViewById<Button>(R.id.showOnMapButton)
        val moreReviewsText = findViewById<TextView>(R.id.showMoreReviewsText)

        //Set clickListener
        returnButton.setOnClickListener{
            //Get the main activity we wanna go to
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            //Launch main activity
            startActivity(mainActivity) //Go to mainActivity
        }

        val intent = intent
        val restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION_KEY, POSITION_NOT_SET) //Get the restaurant position from calling activity

        //Get all the component views so we can add data to them
        val nameTextView = findViewById<TextView>(R.id.detailsNameTextView)
        val addressTextView = findViewById<TextView>(R.id.detailsAddressTextView)
        val postalAddressTextView = findViewById<TextView>(R.id.detailsPostalAddressTextView)
        val ratingView = findViewById<RatingBar>(R.id.detailsRatingBar)
        val typeTextView = findViewById<TextView>(R.id.detailsTypeTextView)
        val guideDogImageView = findViewById<ImageView>(R.id.detailsGuideDogImageView)
        val accessibleImageView = findViewById<ImageView>(R.id.detailsAccessibleImageView)
        val phoneNumberTextView = findViewById<TextView>(R.id.phoneNumberTextView)
        val emailTextView = findViewById<TextView>(R.id.eMailTextView)
        val webUrlTextView = findViewById<TextView>(R.id.webUrlTextView)
        val restaurantImageView = findViewById<ImageView>(R.id.detailsRestaurantImageView)
        val reviewTextView = findViewById<TextView>(R.id.reviewTextView)

        //Get the restaurant from database
        val restaurant = DataManager.restaurants[restaurantPosition]
        //Add data to the component views
        nameTextView.text = restaurant.name
        typeTextView.text = restaurant.type
        addressTextView.text = restaurant.address
        postalAddressTextView.text = restaurant.postalCode.toString().plus(" ".plus(restaurant.city))
        ratingView.rating = restaurant.rating.toFloat()
        phoneNumberTextView.text = restaurant.phoneNumber
        emailTextView.text = restaurant.mail
        webUrlTextView.text = restaurant.webUrl
        //If reviewtext is too long, shorten to 50 characters
        if (restaurant.review.length > 50){
            val text = restaurant.review.substring(0,50)
            reviewTextView.text = text
        }else{
            reviewTextView.text = restaurant.review
        }

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

        //Get the image from firebase
        if(restaurant.imageName.isNotEmpty()){
            val imageref = Firebase.storage.reference.child(restaurant.imageName)
            imageref.downloadUrl.addOnSuccessListener {Uri->
                val imageURL = Uri.toString() // get the URL for the image
                //Use third party product glide to load the image into the imageview
                Glide.with(this)
                    .load(imageURL)
                    .into(restaurantImageView)
            }

            showOnMapButton.setOnClickListener{
                //Get the main activity we wanna go to
                val mapActivity = Intent(this,MapActivity::class.java) //Get a reference to the game activity screen
                mapActivity.putExtra(RESTAURANT_POSITION_KEY,restaurantPosition)
                //Launch main activity
                startActivity(mapActivity) //Go to mainActivity
            }

            moreReviewsText.setOnClickListener{
                //Get the review activity we wanna go to
                val reviewActivity = Intent(this,reviewActivity::class.java) //Get a reference to the review activity screen
                reviewActivity.putExtra(RESTAURANT_POSITION_KEY,restaurantPosition)
                //Launch review activity
                startActivity(reviewActivity) //Go to reviewActivity
            }
        }




    }
}