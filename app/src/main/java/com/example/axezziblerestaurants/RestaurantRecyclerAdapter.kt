package com.example.axezziblerestaurants

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RestaurantRecyclerAdapter (
    val context: Context,
    val restaurants: List<Restaurant>
): RecyclerView.Adapter<RestaurantRecyclerAdapter.ViewHolder>(){
    val layoutInflater = LayoutInflater.from(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.list_item, parent, false )

        return ViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val restaurant = restaurants[position]

        holder.nameTextView.text = restaurant.name
        holder.typeTextView.text = restaurant.type
        holder.restaurantPosition = position

    }

    override fun getItemCount() = restaurants.size

    fun removeStudent(position: Int) {
        DataManager.restaurants.removeAt(position)
        notifyDataSetChanged()
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nameTextView = itemView.findViewById<TextView>(R.id.nameTextView)
        val typeTextView = itemView.findViewById<TextView>(R.id.typeTextView)
        var restaurantPosition = 0


        init {
            itemView.setOnClickListener {
                val intent = Intent(context, MainActivity::class.java)
                intent.putExtra(RESTAURANT_POSITION_KEY, restaurantPosition)
                context.startActivity(intent)
            }
        }



    }
}