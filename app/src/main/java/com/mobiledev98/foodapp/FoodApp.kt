package com.mobiledev98.foodapp

import android.app.Application
import com.mobiledev98.foodapp.helper.Settings
import com.mobiledev98.foodapp.helper.ThemeChanger

class FoodApp: Application() {
    lateinit var settings: Settings

    override fun onCreate() {
        super.onCreate()
        settings = Settings.getInstance(this)
        val theme = settings.appTheme
        ThemeChanger().invoke(theme)
    }
}