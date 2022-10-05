package com.mobiledev98.foodapp.views

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledev98.foodapp.model.Meal
import com.mobiledev98.foodapp.model.Meals
import com.mobiledev98.foodapp.repository.FoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MealViewModel: ViewModel() {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val foodRepository = FoodRepository()
    private val _mealData: MutableLiveData<Meals> = MutableLiveData()
    val mealData: LiveData<Meals> = _mealData

    fun getMealsData(category: String){
        coroutineScope.launch {
            foodRepository.getMealsData(category)
            withContext(Dispatchers.Main){
                if(foodRepository.meals!!.isSuccess()){
                    _mealData.value = foodRepository.meals!!.toData()
                }
            }
        }
    }
}