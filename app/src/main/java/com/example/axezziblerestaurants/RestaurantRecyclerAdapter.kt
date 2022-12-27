package com.example.axezziblerestaurants

import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.RatingBar
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RestaurantRecyclerAdapter (
    val context: Context,
    val restaurants: List<Restaurant>):
    RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>(){
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false )
        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.nameTextView.text = restaurant.name
        holder.typeTextView.text = restaurant.type
        holder.ratingBar.rating = restaurant.rating.toFloat()
        holder.restaurantPosition = position
    }

    override fun getItemCount() = restaurants.size

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val typeTextView = itemView.findViewById<TextView>(R.id.typeTextView)
        val ratingBar = itemView.findViewById<RatingBar>(R.id.ratingBar)

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