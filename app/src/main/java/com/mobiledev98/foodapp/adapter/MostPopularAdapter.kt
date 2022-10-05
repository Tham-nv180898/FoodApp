package com.mobiledev98.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.model.Meal
import com.mobiledev98.foodapp.model.Meals

class MostPopularAdapter : RecyclerView.Adapter<MostPopularAdapter.MostPopularHolder>() {
    private var mMeals: Meals? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: Meals?) {
        mMeals = data
        notifyDataSetChanged()
    }

    class MostPopularHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgPopularMeal: ImageView = itemView.findViewById(R.id.img_popular_meal)

        fun bindingWithDatta(meal: Meal) {
            Glide.with(itemView.context).load(meal.strMealThumb).into(imgPopularMeal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MostPopularHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.most_popular_card, parent, false)
        return MostPopularHolder(view)
    }

    override fun onBindViewHolder(holder: MostPopularHolder, position: Int) {
        if (!mMeals?.meals.isNullOrEmpty()) {
            holder.bindingWithDatta(mMeals!!.meals[position])
        }
    }

    override fun getItemCount(): Int = if (mMeals?.meals.isNullOrEmpty()) {
        0
    } else {
        mMeals!!.meals.size
    }
}