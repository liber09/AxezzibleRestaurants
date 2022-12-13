package com.example.axezziblerestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class AddNewRestaurant : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_new_restaurant)

        //Get the return to list button
        val returnToListButtonClick = findViewById<Button>(R.id.returnToListButton)
        //Set a clickListener
        returnToListButtonClick.setOnClickListener{
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            startActivity(mainActivity) //Go to mainActivity
        }
    }
}