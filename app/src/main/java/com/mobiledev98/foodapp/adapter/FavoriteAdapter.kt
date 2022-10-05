package com.mobiledev98.foodapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.model.FavoriteMeal
import com.mobiledev98.foodapp.model.Meal


class FavoriteAdapter: RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    private var favoriteMeals: List<FavoriteMeal>? = null
    private var onItemFavoriteMealClicked: OnItemFavoriteMealClicked? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<FavoriteMeal>?) {
        favoriteMeals = data
        notifyDataSetChanged()
    }

    fun setOnItemFavoriteMealClicked(onItemFavoriteMealClicked: OnItemFavoriteMealClicked) {
        this.onItemFavoriteMealClicked = onItemFavoriteMealClicked
    }

    inner class FavoriteHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgFavMeal: ImageView = itemView.findViewById(R.id.img_fav_meal)
        private val tvMealName: TextView = itemView.findViewById(R.id.tv_fav_meal_name)

        fun bindingWithDatta(favoriteMeal: FavoriteMeal) {
            Glide.with(itemView.context).load(favoriteMeal.src).into(imgFavMeal)
            tvMealName.text = favoriteMeal.nameMeal
        }

        fun setOnclickCategory(favoriteMeal: FavoriteMeal){
            itemView.setOnClickListener {
                onItemFavoriteMealClicked?.onClickListener(favoriteMeal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.fav_meal_card, parent, false)
        return FavoriteHolder(view)
    }

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        if (!favoriteMeals.isNullOrEmpty()) {
            holder.bindingWithDatta(favoriteMeals!![position])
            holder.setOnclickCategory(favoriteMeals!![position])
        }
    }

    override fun getItemCount(): Int = if (favoriteMeals.isNullOrEmpty()) {
        0
    } else {
        favoriteMeals!!.size
    }
}

interface OnItemFavoriteMealClicked{
    fun onClickListener(favoriteMeal: FavoriteMeal)
}