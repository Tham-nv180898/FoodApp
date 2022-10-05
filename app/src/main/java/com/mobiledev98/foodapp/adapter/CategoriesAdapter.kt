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
import com.mobiledev98.foodapp.model.Categories
import com.mobiledev98.foodapp.model.Category

class CategoriesAdapter: RecyclerView.Adapter<CategoriesAdapter.CategoriesHolder>() {
    private var mCategories: Categories? = null
    private var onItemCategoryClicked: OnItemCategoryClicked? = null

    @SuppressLint("NotifyDataSetChanged")
    fun setData(data: Categories?){
        mCategories = data
        notifyDataSetChanged()
    }

    fun setOnclickCategory(onItemCategoryClicked: OnItemCategoryClicked?){
        this.onItemCategoryClicked = onItemCategoryClicked
    }

    inner class CategoriesHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        private val imgCategory: ImageView = itemView.findViewById(R.id.img_category)
        private val tvCategoryName: TextView = itemView.findViewById(R.id.tvCategoryName)

        fun bindingWithDatta(category: Category){
            Glide.with(itemView.context).load(category.strCategoryThumb).into(imgCategory)
            tvCategoryName.text = category.strCategory
        }

        fun setOnclickCategory(position: Int){
            itemView.setOnClickListener {
                onItemCategoryClicked?.onClickListener(mCategories!!.categories[position])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoriesHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.category_card, parent, false)
        return CategoriesHolder(view)
    }

    override fun onBindViewHolder(holder: CategoriesHolder, position: Int) {
        if (!mCategories?.categories.isNullOrEmpty()){
            holder.bindingWithDatta(mCategories!!.categories[position])
            holder.setOnclickCategory(position)
        }
    }

    override fun getItemCount(): Int = if (mCategories?.categories.isNullOrEmpty()) {
        0
    } else {
        mCategories!!.categories.size
    }
}

interface OnItemCategoryClicked{
    fun onClickListener(category:Category)
}