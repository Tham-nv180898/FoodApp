package com.mobiledev98.foodapp.helper

import android.annotation.SuppressLint
import android.content.Context

class Settings(context: Context) {
    companion object{
        private var INSTANT: Settings? = null
        private const val SHARED_PREFERENCES_NAME = "SHARED_PREFERENCES_NAME"
        const val PREF_NAME_THEME_MODE = "pref_name_theme_mode"
        fun getInstance(context: Context): Settings{
            return INSTANT ?: synchronized(this){
                Settings(context.applicationContext).apply { INSTANT = this }
            }
        }
    }

    private val sharedPreferences =
        context.getSharedPreferences(SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE)

    val appTheme: Theme
        get() {
            val theme = sharedPreferences.getString(PREF_NAME_THEME_MODE, Theme.FOLLOW_SYSTEM.name)
            return Theme.valueOf(theme ?: Theme.FOLLOW_SYSTEM.name)
        }

    @SuppressLint("CommitPrefEdits")
    fun updateTheme(theme: Theme){
        sharedPreferences.edit()
            .putString(PREF_NAME_THEME_MODE, theme.name)
            .apply()
        ThemeChanger().invoke(theme)
    }


}