package com.mobiledev98.foodapp.views

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.mobiledev98.foodapp.adapter.MealsAdapter
import com.mobiledev98.foodapp.adapter.OnItemMealClicked
import com.mobiledev98.foodapp.databinding.ActivityMealBinding
import com.mobiledev98.foodapp.helper.Constant
import com.mobiledev98.foodapp.model.Meal

class MealActivity : AppCompatActivity() {
    private lateinit var mealViewModel: MealViewModel
    private lateinit var binding: ActivityMealBinding
    private lateinit var mealsAdapter: MealsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val categoryName = getCategory()
        mealViewModel = ViewModelProvider(this)[MealViewModel::class.java]
        prepareRecyclerView()
        showData(categoryName)
    }

    private fun prepareRecyclerView() {
        mealsAdapter = MealsAdapter()
        binding.mealRecyclerview.apply {
            adapter = mealsAdapter
            layoutManager = GridLayoutManager(context, 2, GridLayoutManager.VERTICAL, false)
        }
    }

    private fun showData(category: String){
        binding.tvCategoryCount.text = category
        mealViewModel.getMealsData(category)
        mealViewModel.mealData.observe(this){
            mealsAdapter.setData(it.meals)
            mealsAdapter.setOnItemMealClicked(object : OnItemMealClicked {
                override fun onClickListener(meal: Meal) {
                    val intent = Intent(this@MealActivity, MealDetailActivity::class.java)
                    intent.putExtra(Constant.MEAL_NAME.name, meal.strMeal)
                    intent.putExtra(Constant.MEAL_THUMB.name, meal.strMealThumb)
                    startActivity(intent)
                }
            })
        }
    }

    private fun getCategory(): String {
        return intent.getStringExtra(Constant.CATEGORY_NAME.name)!!
    }
}
