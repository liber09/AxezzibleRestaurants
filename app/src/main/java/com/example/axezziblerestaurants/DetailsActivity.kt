package com.example.axezziblerestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
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
        nameTextView.text = restaurant.name
        addressTextView.text = restaurant.address
        postalAddressTextView.text = restaurant.postalCode.toString().plus(" ".plus(restaurant.city))
    }
}