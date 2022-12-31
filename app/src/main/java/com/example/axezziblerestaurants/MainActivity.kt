package com.example.axezziblerestaurants

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var auth: FirebaseAuth //authentication
    val db = Firebase.firestore  //initialize database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInitialData() //Add the first restaurants that are included in app
        auth = Firebase.auth
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants) //Attach data to the recyclerview
        //Get the new restaurant button
        val newRestaurantButtonClick = findViewById<Button>(R.id.addNewResButton)
        //Set a clickListener
        newRestaurantButtonClick.setOnClickListener {
            val user = FirebaseAuth.getInstance().currentUser  //Get the user currently logged in
            //Check if user is logged in
            if(user == null){
                Toast.makeText(this@MainActivity,"You have to be signed in to add new restaurants!", Toast.LENGTH_SHORT).show() //Show warning message that user has to be signed in
            }else {
                val newRestaurantScreen = Intent(
                    this, AddNewRestaurant::class.java
                ) //Get a reference to the game activity screen
                startActivity(newRestaurantScreen) //Go to the new restaurant activity
            }
        }
        //Get the map button
        val mapButtonCLick = findViewById<Button>(R.id.showMapButton)
        //Add clickListener to map button
        mapButtonCLick.setOnClickListener{
            //Get the map activity
            val mapActivity = Intent(this,MapActivity::class.java) //Get a reference to the game activity screen
            //Launch map activity
            startActivity(mapActivity) //Go to mainActivity
        }
        //Get the sign in button
        val signInButton = findViewById<Button>(R.id.startSignInButton)
        //Set a clickListener on the button
        signInButton.setOnClickListener{
            //Get the sign in activity
            val signInActivity = Intent(this,SignInActivity::class.java) //Get a reference to the game activity screen
            //Launch the sign in activity
            startActivity(signInActivity) //Go to signIn
        }
        recyclerView.adapter?.notifyDataSetChanged()
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter?.notifyDataSetChanged()
    }

    //Creates the first default restaurants included in app
    private fun addInitialData() {
        //Create the first restaurant object
        val restaurantOne = Restaurant(
            "TiAmo",
            "Italian",
            true,
            true,
            "Strandvägen 4",
            44431,
            "Stenungsund",
            "tiamo.png",
            4.5,
            "0303-88859",
            eMail="info@tiamostenungsund.se",
            webUrl="https://www.tiamostenungsund.se/",
        )
        //Create the second restaurant object
        val restaurantTwo = Restaurant(
            "McDonalds",
            "Hamburger", false,
            true, "Stenunge allé 1", 44430,
            "Stenungsund", "mcd.png", 3.0, "0303-65480",
            webUrl="https://www.mcdonalds.com/se/sv-se/location/160.html", eMail = "info@mcdonaldsstenungsund.se"
        )
        //Create the third restaurant object
        val restaurantThree = Restaurant(
            "Tonys",
            "Hamburger", true,
            true, "Sandbergs plats 1", 44430,
            "Stenungsund", "tonys.png", 5.0,"072-5113397",
            webUrl="https://stenungsund.tonysrestaurang.se/", eMail = "tony.holm@tonysrestaurang.se"
        )
        //Create the fourth restaurant object
        val restaurantFour = Restaurant(
            "Karlbergs Krog",
            "HomeCooking", true,
            false, "Västra köpmansgatan 2", 44430,
            "Stenungsund", "karlbergs.png", 3.5,"0303-80300",
            webUrl="https://stenungsund.tonysrestaurang.se/", eMail = "info@karlbergskrog.se"
        )
        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("0").set(restaurantOne, SetOptions.merge())
        db.collection("restaurants").document("1").set(restaurantTwo, SetOptions.merge())
        db.collection("restaurants").document("2").set(restaurantThree, SetOptions.merge())
        db.collection("restaurants").document("3").set(restaurantFour, SetOptions.merge())
        }
}