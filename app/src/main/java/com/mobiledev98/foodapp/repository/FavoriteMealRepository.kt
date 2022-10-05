package com.mobiledev98.foodapp.repository

import android.content.Context
import androidx.lifecycle.LiveData
import com.mobiledev98.foodapp.database.localdatabase.FavoriteMealsDatabase
import com.mobiledev98.foodapp.model.FavoriteMeal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FavoriteMealRepository(context: Context) {
    private val favoriteMealsDatabase = FavoriteMealsDatabase.getInstance(context)


    suspend fun save(favoriteMeal: FavoriteMeal): Long {
        var id: Long = 0
        withContext(Dispatchers.IO) {
            id = favoriteMealsDatabase.save(favoriteMeal)
        }
        return id
    }

    suspend fun exists(nameMeal: String): Boolean {
        return favoriteMealsDatabase.exists(nameMeal)
    }

    suspend fun getAllSavedMeals(): List<FavoriteMeal> {
        return favoriteMealsDatabase.getAllSavedMeals()
    }
}