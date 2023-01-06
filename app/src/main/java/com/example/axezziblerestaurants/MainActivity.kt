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
    var userSignedin = false
    val db = Firebase.firestore  //initialize database

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        auth = Firebase.auth
        if(auth.currentUser != null){
            userSignedin = true;
            changeLoginButtonToLogOutButton()
        }
        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this) //Set the layout manager
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants) //Attach data to the recyclerview
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
        Thread.sleep(1_000) //Sleep while waiting for database response
        recyclerView.adapter?.notifyDataSetChanged()
    }

    //Checks if anyone is signed in
    private fun isUserSignedIn() {
        //Check if user is logged in
        if(auth.currentUser != null) {
            userSignedin = true
            changeLoginButtonToLogOutButton()
        }
    }

    //Change the signinbutton to signout button
    fun changeLoginButtonToLogOutButton() {
        val button = findViewById<Button>(R.id.startSignInButton)
        button.text = getString(R.string.signOut) //Set new text on button
        //Add clicklistener
        button.setOnClickListener{
            auth.signOut() //Sign out
            userSignedin = false //Set user signout property
            changeLogoutButtonToLoginButton() //Call to change button to signin since no user is currently signed in.
    }
}
    //Change the LogoutButton to SignInButton
    fun changeLogoutButtonToLoginButton() {
        val button = findViewById<Button>(R.id.startSignInButton)
        button.text = getString(R.string.signIn)
        button.setOnClickListener{
            //Get the sign in activity
            val signInActivity = Intent(this,SignInActivity::class.java) //Get a reference to the game activity screen
            //Launch the sign in activity
            startActivity(signInActivity) //Go to signIn
            isUserSignedIn() //Check if user is signed in or not
        }
    }

    //When we return update restaurant list
    override fun onResume() {
        super.onResume()
        recyclerView.adapter = RestaurantRecyclerAdapter(this, DataManager.restaurants) //Attach data to the recyclerview when returning to main activity
    }
}