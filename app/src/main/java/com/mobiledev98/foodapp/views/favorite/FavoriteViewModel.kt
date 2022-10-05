package com.mobiledev98.foodapp.views.favorite

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mobiledev98.foodapp.model.FavoriteMeal
import com.mobiledev98.foodapp.repository.FavoriteMealRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FavoriteViewModel(application: Application): AndroidViewModel(application) {
    private val coroutineScope = CoroutineScope(Dispatchers.IO)
    private val favoriteMealRepository = FavoriteMealRepository(application)
    private val _favoriteMeal: MutableLiveData<List<FavoriteMeal>> = MutableLiveData()
    val favoriteMeal: LiveData<List<FavoriteMeal>> = _favoriteMeal

    fun getAllSavedMeals(){
        coroutineScope.launch {
            val result = favoriteMealRepository.getAllSavedMeals()
            withContext(Dispatchers.Main){
                _favoriteMeal.value = result
            }
        }
    }
}