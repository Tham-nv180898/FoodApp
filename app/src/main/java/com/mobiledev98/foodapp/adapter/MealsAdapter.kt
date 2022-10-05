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
import com.mobiledev98.foodapp.model.Meal

class MealsAdapter: RecyclerView.Adapter<MealsAdapter.MealsHolder>() {
    private var mMeals: List<Meal>? = null
    private var onItemMealClicked: OnItemMealClicked? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: List<Meal>?) {
        mMeals = data
        notifyDataSetChanged()
    }

    fun setOnItemMealClicked(onItemMealClicked: OnItemMealClicked) {
        this.onItemMealClicked = onItemMealClicked
    }

    inner class MealsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imgMeal: ImageView = itemView.findViewById(R.id.img_meal)
        private val tvMealName: TextView = itemView.findViewById(R.id.tv_meal_name)

        fun bindingWithDatta(meal: Meal) {
            Glide.with(itemView.context).load(meal.strMealThumb).into(imgMeal)
            tvMealName.text = meal.strMeal
        }

        fun setOnclickCategory(meal: Meal){
            itemView.setOnClickListener {
                onItemMealClicked?.onClickListener(meal)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MealsHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.meal_card, parent, false)
        return MealsHolder(view)
    }

    override fun onBindViewHolder(holder: MealsHolder, position: Int) {
        if (!mMeals.isNullOrEmpty()) {
            holder.bindingWithDatta(mMeals!![position])
            holder.setOnclickCategory(mMeals!![position])
        }
    }

    override fun getItemCount(): Int = if (mMeals.isNullOrEmpty()) {
        0
    } else {
        mMeals!!.size
    }
}

interface OnItemMealClicked{
    fun onClickListener(meal: Meal)
}