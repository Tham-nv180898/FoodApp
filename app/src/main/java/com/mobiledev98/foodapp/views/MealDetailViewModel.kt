package com.mobiledev98.foodapp.views

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mobiledev98.foodapp.model.FavoriteMeal
import com.mobiledev98.foodapp.model.MealInformation
import com.mobiledev98.foodapp.repository.FavoriteMealRepository
import com.mobiledev98.foodapp.repository.FoodRepository
import kotlinx.coroutines.*
import okhttp3.internal.checkOffsetAndCount

class MealDetailViewModel(application: Application) : AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val foodRepository = FoodRepository()
    private val favoriteMealRepository = FavoriteMealRepository(application)
    private val _mealInformation: MutableLiveData<MealInformation> = MutableLiveData()
    val mealInformation: LiveData<MealInformation> = _mealInformation
    private val _checkItemExist: MutableLiveData<Boolean> = MutableLiveData()
    val checkItemExist: LiveData<Boolean> = _checkItemExist

    fun getMealInformation(mealName: String) {
        coroutineScope.launch {
            foodRepository.getMealInformation(mealName)
            withContext(Dispatchers.Main) {
                if (foodRepository.mealInformation!!.isSuccess()) {
                    _mealInformation.value = foodRepository.mealInformation!!.toData()
                }
            }
        }
    }

    fun save(favoriteMeal: FavoriteMeal) {
        coroutineScope.launch {
            favoriteMealRepository.save(favoriteMeal)
        }
    }


    fun checkExist(nameMeal: String) {
        coroutineScope.launch {
            val result = favoriteMealRepository.exists(nameMeal)
            withContext(Dispatchers.Main){
                _checkItemExist.value = result
            }
        }
    }

    fun resetValue(){
        _checkItemExist.value = null
    }
}