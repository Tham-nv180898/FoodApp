package com.mobiledev98.foodapp.repository

import com.mobiledev98.foodapp.database.remotedatabase.RestClient
import com.mobiledev98.foodapp.model.Categories
import com.mobiledev98.foodapp.model.Meal
import com.mobiledev98.foodapp.model.MealInformation
import com.mobiledev98.foodapp.model.Meals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import com.mobiledev98.foodapp.types.Result

class FoodRepository {
    var categoriesData: Result<Categories>? = null
    var popularMeal: Result<Meals>? = null
    var suggestMeals: Result<List<Meal>>? = null
    var mealInformation: Result<MealInformation>? = null
    var meals: Result<Meals>? = null

    private val client = RestClient.getInstant()

    suspend fun getData(popularMealsCategory: String, suggestMealsCategory: String) {
        try {
            val categoryResponse = client.getCategories()
            val popularMealsResponse = client.getMeals(popularMealsCategory)
            val suggestMealsResponse = client.getMeals(suggestMealsCategory)
            withContext(Dispatchers.Main) {
                categoriesData =
                    if (categoryResponse.isSuccessful && categoryResponse.body() != null) {
                        Result.Success(categoryResponse.body()!!)
                    } else {
                        Result.Error()
                    }
                popularMeal =
                    if (popularMealsResponse.isSuccessful && popularMealsResponse.body() != null) {
                        Result.Success(popularMealsResponse.body()!!)
                    } else {
                        Result.Error()
                    }
                suggestMeals =
                    if (suggestMealsResponse.isSuccessful && suggestMealsResponse.body() != null) {
                        val returnData = if (suggestMealsResponse.body()!!.meals.size <= 5) {
                            suggestMealsResponse.body()!!.meals
                        } else {
                            suggestMealsResponse.body()!!.meals.subList(0, 5)
                        }
                        Result.Success(returnData)
                    } else {
                        Result.Error()
                    }
            }
        } catch (networkError: IOException) {
            withContext(Dispatchers.Main) {
                categoriesData = Result.Error()
                popularMeal = Result.Error()
                suggestMeals = Result.Error()
            }
        }
    }

    suspend fun getMealsData(category: String){
        try {
            val mealsResponse = client.getMeals(category)
            withContext(Dispatchers.Main) {
                meals = if (mealsResponse.isSuccessful && mealsResponse.body() != null) {
                    Result.Success(mealsResponse.body()!!)
                } else {
                    Result.Error()
                }
            }
        } catch (networkError: IOException) {
            withContext(Dispatchers.Main) {
                meals = Result.Error()
            }
        }
    }

    suspend fun getMealInformation(mealName: String) {
        try {
            val mealInformationResponse = client.getMealInformation(mealName)
            withContext(Dispatchers.Main) {
                mealInformation = if (mealInformationResponse.isSuccessful && mealInformationResponse.body() != null) {
                    Result.Success(mealInformationResponse.body()!!)
                } else {
                    Result.Error()
                }
            }
        } catch (networkError: IOException) {
            withContext(Dispatchers.Main) {
                mealInformation = Result.Error()
            }
        }
    }

}