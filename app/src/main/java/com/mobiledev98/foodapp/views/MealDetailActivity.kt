package com.mobiledev98.foodapp.views

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.databinding.ActivityMealDetailesBinding
import com.mobiledev98.foodapp.helper.Constant
import com.mobiledev98.foodapp.model.FavoriteMeal


class MealDetailActivity : AppCompatActivity() {
    private lateinit var mealDetailViewModel: MealDetailViewModel
    private lateinit var binding: ActivityMealDetailesBinding
    private var mealStr = ""
    private var mealThumb = ""
    private var isFavorite = false

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMealDetailesBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mealDetailViewModel = ViewModelProvider(this)[MealDetailViewModel::class.java]
        getMealInfoFromIntent()
        checkTobeFavorite(mealStr)
        showDetailMeal()
        setUpViewWithMealInformation()
        registerObserver()

        binding.btnSave.setOnClickListener {
            if(!isFavorite) {
                mealDetailViewModel.save(FavoriteMeal(nameMeal = mealStr, src = mealThumb))
                Toast.makeText(this@MealDetailActivity, "Saved...", Toast.LENGTH_SHORT).show()
                setColor()
                isFavorite = true
            }else{
                Toast.makeText(this@MealDetailActivity, "This meal is saved!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    private fun setColor(){
        binding.btnSave.setImageResource(R.drawable._favorite)
    }

    private fun getMealInfoFromIntent() {
        val tempIntent = intent
        this.mealStr = tempIntent.getStringExtra(Constant.MEAL_NAME.name) ?: ""
        this.mealThumb = tempIntent.getStringExtra(Constant.MEAL_THUMB.name) ?: ""
    }

    private fun setUpViewWithMealInformation() {
        binding.apply {
            collapsingToolbar.title = mealStr
            Glide.with(applicationContext)
                .load(mealThumb)
                .into(imgMealDetail)
        }
    }

    private fun showDetailMeal() {
        mealDetailViewModel.getMealInformation(mealStr)
    }

    private fun checkTobeFavorite(nameMeal: String){
        mealDetailViewModel.checkExist(nameMeal)
    }

    @SuppressLint("SetTextI18n")
    private fun registerObserver(){
        mealDetailViewModel.mealInformation.observe(this) {
            if (it.meals.isNotEmpty()) {
                binding.apply {
                    tvInstructions.text = "- Instructions : "
                    tvContent.text = it.meals[0].strInstructions
                    tvAreaInfo.visibility = View.VISIBLE
                    tvCategoryInfo.visibility = View.VISIBLE
                    tvAreaInfo.text = tvAreaInfo.text.toString() + it.meals[0].strArea
                    tvCategoryInfo.text = tvCategoryInfo.text.toString() + it.meals[0].strCategory
                }
            }
        }

        mealDetailViewModel.checkItemExist.observe(this) {
            when (it) {
                true -> {
                    binding.btnSave.setImageResource(R.drawable._favorite)
                    isFavorite = true
                }
                false -> {
                    binding.btnSave.setImageResource(R.drawable.no_favorite)
                    isFavorite =  false
                }
                else -> {

                }
            }
//            mealDetailViewModel.resetValue()
        }
    }
}