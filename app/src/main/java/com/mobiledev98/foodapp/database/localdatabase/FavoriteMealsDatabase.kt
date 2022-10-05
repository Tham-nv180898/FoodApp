package com.mobiledev98.foodapp.database.localdatabase

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.mobiledev98.foodapp.model.FavoriteMeal

@Database(entities = [FavoriteMeal::class], version = 2)
abstract class FavoriteMealsDatabaseFactory : RoomDatabase() {
    abstract fun getFavoriteMealsDatabase(): FavoriteMealsDatabase
}

@Dao
interface FavoriteMealsDatabase {

    companion object {
        private var INSTANCE: FavoriteMealsDatabase? = null
        fun getInstance(context: Context): FavoriteMealsDatabase {
            return INSTANCE ?: synchronized(this) {
                Room
                    .databaseBuilder(context.applicationContext,
                        FavoriteMealsDatabaseFactory::class.java,
                        "db")
                    .addMigrations(object : Migration(1, 2) {
                        override fun migrate(database: SupportSQLiteDatabase) {
                            database.execSQL("ALTER TABLE codes ADD COLUMN name TEXT")
                        }
                    })
                    .build()
                    .getFavoriteMealsDatabase().apply {
                        INSTANCE = this
                    }
            }
        }
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun save(favoriteMeal: FavoriteMeal): Long

    @Query("SELECT * FROM favorite_meal")
    fun getAllSavedMeals(): List<FavoriteMeal>

    @Query("SELECT EXISTS (SELECT 1 FROM favorite_meal WHERE nameMeal = :nameMeals)")
    fun exists(nameMeals: String): Boolean
}