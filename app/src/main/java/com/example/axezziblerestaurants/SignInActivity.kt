package com.example.axezziblerestaurants

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.widget.Button
import android.widget.EditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignInActivity : AppCompatActivity() {
    lateinit var auth: FirebaseAuth
    lateinit var passwordView: EditText
    lateinit var emailView: EditText
    override fun dispatchPopulateAccessibilityEvent(event: AccessibilityEvent?): Boolean {
        return super.dispatchPopulateAccessibilityEvent(event)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        auth = Firebase.auth
        emailView = findViewById<EditText>(R.id.editTextEmail)
        passwordView = findViewById<EditText>(R.id.editTextPassword)
        val signUpButton = findViewById<Button>(R.id.signUpButton)
        val signInButton = findViewById<Button>(R.id.signInButton)

        signUpButton.setOnClickListener{
            signUp()
        }
        signInButton.setOnClickListener{
            signIn()
        }
    }

    fun signUp(){
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            return
        }
        auth.createUserWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d("!!!", "User created")
                }else{
                    Log.d("!!!", "User not created ${task.exception}")
                }
            }

    }
    fun signIn(){
        val email = emailView.text.toString()
        val password = passwordView.text.toString()

        if(email.isEmpty() || password.isEmpty()){
            return
        }
        auth.signInWithEmailAndPassword(email,password)
            .addOnCompleteListener{ task ->
                if(task.isSuccessful){
                    Log.d("!!!", "User created")
                }else{
                    Log.d("!!!", "User not created ${task.exception}")
                }
            }
    }
}