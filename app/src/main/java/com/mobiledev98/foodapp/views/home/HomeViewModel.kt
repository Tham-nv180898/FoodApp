package com.mobiledev98.foodapp.views.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledev98.foodapp.model.Categories
import com.mobiledev98.foodapp.model.Meal
import com.mobiledev98.foodapp.model.Meals
import com.mobiledev98.foodapp.repository.FoodRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeViewModel:ViewModel() {
    private val foodRepository = FoodRepository()
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val _categoriesData:MutableLiveData<Categories>  = MutableLiveData()
    val categoriesData: LiveData<Categories> = _categoriesData

    private val _mealsData:MutableLiveData<Meals>  = MutableLiveData()
    val mealsData: LiveData<Meals> = _mealsData

    private val _suggestData:MutableLiveData<List<Meal>>  = MutableLiveData()
    val suggestData: LiveData<List<Meal>> = _suggestData


    fun getDataToShow(){
        coroutineScope.launch {
            foodRepository.getData("Beef", "Chicken")
            withContext(Dispatchers.Main){
                if(foodRepository.categoriesData!!.isSuccess()){
                    _categoriesData.value = foodRepository.categoriesData!!.toData()
                }
                if (foodRepository.popularMeal!!.isSuccess()){
                    _mealsData.value = foodRepository.popularMeal!!.toData()
                }

                if (foodRepository.suggestMeals!!.isSuccess()){
                    _suggestData.value = foodRepository.suggestMeals!!.toData()
                }
            }
        }
    }

}