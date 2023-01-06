package com.example.axezziblerestaurants

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.RatingBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ReviewRecycleAdapter (
    val context: Context,
    val reviews: List<Review>):
    RecyclerView.Adapter<ReviewRecycleAdapter.ViewHolder>(){ val layoutInflater = LayoutInflater.from(context)
    override fun onBindViewHolder(holder: ReviewRecycleAdapter.ViewHolder, position: Int) {
        val review = reviews[position]

        holder.nameTextView.text = review.name
        holder.reviewEditText.setText(review.reviewText)
        holder.ratingBar.rating = review.rating.toFloat()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewRecycleAdapter.ViewHolder {
        val itemView = layoutInflater.inflate(R.layout.review_view, parent, false ) //The itemView that holds each restaurants layout inj the recyclerview
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return reviews.size
    }

    inner class ViewHolder(ratingView: View) : RecyclerView.ViewHolder(ratingView) {
        val nameTextView = ratingView.findViewById<TextView>(R.id.reviewersName)
        val reviewEditText = ratingView.findViewById<EditText>(R.id.reviewPageEditText)
        val ratingBar = ratingView.findViewById<RatingBar>(R.id.reviewPageRatingBar)
    }
}