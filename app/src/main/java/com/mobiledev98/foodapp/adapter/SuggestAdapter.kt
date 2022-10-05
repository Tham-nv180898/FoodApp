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

class SuggestAdapter : RecyclerView.Adapter<SuggestAdapter.SuggestHolder>() {

    private var mMeals: List<Meal>? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Meal>?) {
        mMeals = data
        notifyDataSetChanged()
    }

    class SuggestHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgSuggestMeal: ImageView = itemView.findViewById(R.id.suggest_img)

        fun bindingWithDatta(meal: Meal) {
            Glide.with(itemView.context).load(meal.strMealThumb).into(imgSuggestMeal)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SuggestHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.suggest_card, parent, false)
        return SuggestHolder(view)
    }

    override fun onBindViewHolder(holder: SuggestHolder, position: Int) {
        if (!mMeals.isNullOrEmpty()) {
            holder.bindingWithDatta(mMeals!![position])
        }
    }

    override fun getItemCount(): Int = if (mMeals.isNullOrEmpty()) {
        0
    } else {
        mMeals!!.size
    }
}