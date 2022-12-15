package com.example.axezziblerestaurants

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class DetailsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)
        val returnButton = findViewById<Button>(R.id.returnButton)
        returnButton.setOnClickListener{
            val mainActivity = Intent(this,MainActivity::class.java) //Get a reference to the game activity screen
            startActivity(mainActivity) //Go to mainActivity
        }
    }
}