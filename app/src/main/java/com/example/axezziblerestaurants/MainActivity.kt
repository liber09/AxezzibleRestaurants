package com.example.axezziblerestaurants

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

const val RESTAURANT_POSITION_KEY = "RESTAURANT_POSITION"
const val POSITION_NOT_SET = -1

class MainActivity : AppCompatActivity() {
    //initialize database
    lateinit var nameTextView: TextView
    lateinit var typeTextView: TextView
    lateinit var recyclerView: RecyclerView
    val db = Firebase.firestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInitialData() //Add the first restaurants that are included in app
        val restaurantPosition = intent.getIntExtra(RESTAURANT_POSITION_KEY, POSITION_NOT_SET)
        if (restaurantPosition != POSITION_NOT_SET) {
            displayRestaurant(restaurantPosition)
        }
        recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants)
        //Get the new restaurant button
        val newRestaurantButtonClick = findViewById<Button>(R.id.addNewResButton)
        //Set a clickListener
        newRestaurantButtonClick.setOnClickListener { val newRestaurantScreen = Intent(
                    this,
                AddNewRestaurant::class.java
            ) //Get a reference to the game activity screen
            startActivity(newRestaurantScreen) //Go to the new restaurant activity
        }
        val mapButtonCLick = findViewById<Button>(R.id.showMapButton)
        mapButtonCLick.setOnClickListener{
            val mapActivity = Intent(this,MapActivity::class.java) //Get a reference to the game activity screen
            startActivity(mapActivity) //Go to mainActivity
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    //Creates the first default restaurants included in app
    private fun addInitialData() {
        //Create new restaurant object
        val restaurantOne = Restaurant(
            "TiAmo",
            "Italian", true,
            true, "Strandvägen 4", 44431,
            "Stenungsund", "tiamo.png", 4.5, 0
        )
        val restaurantTwo = Restaurant(
            "McDonalds",
            "Hamburger", false,
            true, "Stenunge allé 1", 44430,
            "Stenungsund", "mcd.png", 3.0, 1
        )
        val restaurantThree = Restaurant(
            "Tonys",
            "Hamburger", true,
            true, "Sandbergs plats 1", 44430,
            "Stenungsund", "tonys.png", 5.0,2
        )
        val restaurantFour = Restaurant(
            "Karlbergs Krog",
            "HomeCooking", true,
            true, "Västra köpmansgatan 2", 44430,
            "Stenungsund", "karlbergs.png", 3.5,3
        )
        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("0").set(restaurantOne, SetOptions.merge())
        db.collection("restaurants").document("1").set(restaurantTwo, SetOptions.merge())
        db.collection("restaurants").document("2").set(restaurantThree, SetOptions.merge())
        db.collection("restaurants").document("3").set(restaurantFour, SetOptions.merge())
        }
    fun displayRestaurant(position : Int) {
        val restaurant = DataManager.restaurants[position]

        nameTextView.setText(restaurant.name)
        typeTextView.setText(restaurant.type)
    }
}

