package com.mobiledev98.foodapp.database.remotedatabase

import androidx.lifecycle.LiveData
import com.mobiledev98.foodapp.model.Categories
import com.mobiledev98.foodapp.model.MealInformation
import com.mobiledev98.foodapp.model.Meals
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://www.themealdb.com/api/json/v1/1/"

interface ApiServices {
    @GET("categories.php")
    suspend fun getCategories(): Response<Categories>
    @GET("filter.php?")
    suspend fun getMeals(@Query("c") category: String): Response<Meals>
    @GET("search.php?")
    suspend fun getMealInformation(@Query("s") mealName: String): Response<MealInformation>
}

object RestClient {
    private var INSTANT: ApiServices? = null
    fun getInstant(): ApiServices {
        return INSTANT ?: run {
            Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
                .create(ApiServices::class.java).apply { INSTANT = this }
        }
    }
}