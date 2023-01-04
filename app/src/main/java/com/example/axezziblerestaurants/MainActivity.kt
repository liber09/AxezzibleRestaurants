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
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var auth: FirebaseAuth //authentication
    val db = Firebase.firestore  //initialize database
    var userSignedin = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addInitialData() //Add the first restaurants that are included in app
        auth = Firebase.auth
        if(auth.currentUser != null){
            userSignedin = true;
            changeLoginButtonToLogOutButton()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager
        //Get the new restaurant button
        val newRestaurantButtonClick = findViewById<Button>(R.id.addNewResButton)
        //Set a clickListener
        newRestaurantButtonClick.setOnClickListener {
            //Check if user is logged in
            if(auth.currentUser == null){
                userSignedin = false
                changeLogoutButtonToLoginButton()
                Toast.makeText(this@MainActivity,"You have to be signed in to add new restaurants!", Toast.LENGTH_SHORT).show() //Show warning message that user has to be signed in
            }else {
                userSignedin = true
                changeLoginButtonToLogOutButton()
                val newRestaurantScreen = Intent(
                    this, AddNewRestaurant::class.java
                ) //Get a reference to the game activity screen
                startActivity(newRestaurantScreen) //Go to the new restaurant activity
            }
        }
        //Get the sign in button
        val signInButton = findViewById<Button>(R.id.startSignInButton)
        //Set a clickListener on the button
        signInButton.setOnClickListener{
            if(!userSignedin){
                //Get the sign in activity
                val signInActivity = Intent(this,SignInActivity::class.java) //Get a reference to the game activity screen
                //Launch the sign in activity
                startActivity(signInActivity) //Go to signIn
            }else{
                changeLoginButtonToLogOutButton()
            }
        }
        isUserSignedIn()

            //Get a reference to the collection
            val docRef = db.collection("restaurants")

            //Get snapshots if any
            docRef.addSnapshotListener { snapshot, e ->
                //if there are snapshots
                if (snapshot != null) {

                    //Empty the restaurant list
                    DataManager.restaurants.clear()

                    //for each document (restaurant). Convert to restaurant object and add to restaurant list
                    for (document in snapshot.documents) {
                        val item = document.toObject<Restaurant>()
                        if (item != null) {
                            DataManager.restaurants.add(item)

                        }
                    }
                }
            }


        Thread.sleep(1_000) //Sleep while waiting for database response
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants) //Attach data to the recyclerview
    }

    private fun isUserSignedIn() {
        //Check if user is logged in
        if(auth.currentUser != null) {
            userSignedin = true
            changeLoginButtonToLogOutButton()
        }
    }

    private fun changeLoginButtonToLogOutButton() {
        val button = findViewById<Button>(R.id.startSignInButton)
        button.text = getString(R.string.signOut)
        button.setOnClickListener{
            auth.signOut()
            userSignedin = false
            changeLogoutButtonToLoginButton()
    }
}
    private fun changeLogoutButtonToLoginButton() {
        val button = findViewById<Button>(R.id.startSignInButton)
        button.text = getString(R.string.signIn)
        button.setOnClickListener{
            //Get the sign in activity
            val signInActivity = Intent(this,SignInActivity::class.java) //Get a reference to the game activity screen
            //Launch the sign in activity
            startActivity(signInActivity) //Go to signIn
            isUserSignedIn()
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants) //Attach data to the recyclerview when returning to main activity
    }

    //Creates the first default restaurants included in app
    private fun addInitialData() {
        //Create the first restaurant object
        val restaurantOne = Restaurant(
            "TiAmo",
            "Italian",
            true,
            false,
            "Strandvägen 4",
            44431,
            "Stenungsund",
            "/restaurants/tiamo.jpg",
            4.5,
            "0303-88859",
            webUrl="https://www.tiamostenungsund.se/",
            description ="Nice italian restaurant with a good mix of foods.", review = "Nice italian food. You always feel welcome. Nice place, will come back",
            reviewerName = "Ludwig B",mail="info@tiamostenungsund.se"
        )
        //Create the second restaurant object
        val restaurantTwo = Restaurant(
            "McDonalds",
            "Hamburger", false,
            true, "Stenunge allé 1", 44430,
            "Stenungsund", "/restaurants/mcd_stenungsund.jpeg", 3.0, "0303-65480",
            webUrl="https://www.mcdonalds.com/se/sv-se/location/160.html", mail ="info@mcdonaldsstenungsund.se",
            description ="Fastfood, nothing else to say.", review = "Good for beeing a fast food restaurant. Clean and fresh",
            reviewerName = "Camilla A."
        )
        //Create the third restaurant object
        val restaurantThree = Restaurant(
            "Tonys",
            "Hamburger", true,
            true, "Sandbergs plats 1", 44430,
            "Stenungsund", "/restaurants/tonys.jpeg", 5.0,"072-5113397",
            webUrl="https://stenungsund.tonysrestaurang.se/", mail ="tony.holm@tonysrestaurang.se",
            description = "Probably the best burger in town!", review = "Best burgers i have ever eaten. Recommend!!",
            reviewerName = "Erik"
        )
        //Create the fourth restaurant object
        val restaurantFour = Restaurant(
            "Karlbergs Krog",
            "HomeCooking", true,
            false, "Västra köpmansgatan 2", 44430,
            "Stenungsund", "/restaurants/karlbergs.jpeg", 3.5,"0303-80300",
            webUrl="https://stenungsund.tonysrestaurang.se/", mail = "info@karlbergskrog.se",
            description = "Nice fashionable restaurant with excellent view of the harbor and sea.", review = "A little expensive but i kinda like it. Food is good, view is excellent",
            reviewerName = "Sven Svensson"
        )
        //Add it to collection restaurants, SetOptions.merge() = do not overwrite if exists
        db.collection("restaurants").document("0").set(restaurantOne, SetOptions.merge())
        db.collection("restaurants").document("1").set(restaurantTwo, SetOptions.merge())
        db.collection("restaurants").document("2").set(restaurantThree, SetOptions.merge())
        db.collection("restaurants").document("3").set(restaurantFour, SetOptions.merge())
        }
}