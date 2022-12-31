package com.example.axezziblerestaurants

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage

class RestaurantRecyclerAdapter (
    val context: Context,
    val restaurants: List<Restaurant>):
    RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>(){
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false )
        return ViewHolder(itemView)
    }

    @RequiresApi(Build.VERSION_CODES.S)
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.nameTextView.text = restaurant.name
        holder.typeTextView.text = restaurant.type
        holder.ratingBar.rating = restaurant.rating.toFloat()
        holder.restaurantPosition = position
        holder.descriptionView.text = restaurant.description
        holder.cityTextView.text = restaurant.city
        //Set the correct image on guideDogs and Accessibility images depending on database val
        val pack = this.javaClass.packageName
        if(restaurant.guideDogsAllowed){
            val uriImage = "@drawable/".plus("guidedogs_allowed") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            holder.guideDogImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("dogs_not_allowed") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            holder.guideDogImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }
        if(restaurant.accessible){
            val uriImage = "@drawable/".plus("accessible") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null, pack) //Get the actual image
            holder.accessibleImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }else{
            val uriImage = "@drawable/".plus("not_accessible") //Get filePath
            val imageResource = context.resources.getIdentifier(uriImage, null,pack) //Get the actual image
            holder.accessibleImage.setImageBitmap(BitmapFactory.decodeResource(context.resources, imageResource)) //Show the image on screen
        }
        if(restaurant.imageName.isNotEmpty()){
            val imageref = Firebase.storage.reference.child(restaurant.imageName)
            imageref.downloadUrl.addOnSuccessListener {Uri->

                val imageURL = Uri.toString()

                Glide.with(context)
                    .load(imageURL)
                    .into(holder.restaurantImage)

            }
        }

    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val typeTextView = itemView.findViewById<TextView>(R.id.typeTextView)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)
        val restaurantImage = itemView.findViewById<ImageView>(R.id.itemRestaurantImage)
        val guideDogImage = itemView.findViewById<ImageView>(R.id.itemGuideDogImage)
        val accessibleImage = itemView.findViewById<ImageView>(R.id.itemAccessibleImage)
        val descriptionView = itemView.findViewById<TextView>(R.id.ItemDescriptionTextView)
        val cityTextView = itemView.findViewById<TextView>(R.id.itemCityTextView)
        var restaurantPosition = 0
        init {
            itemView.setOnClickListener {
                val intent = Intent(context, DetailsActivity::class.java)
                intent.putExtra(RESTAURANT_POSITION_KEY, restaurantPosition)
                context.startActivity(intent)
            }
        }
    }
}