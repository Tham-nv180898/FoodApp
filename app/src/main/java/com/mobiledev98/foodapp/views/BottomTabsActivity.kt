package com.mobiledev98.foodapp.views

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.fragment.app.Fragment
import com.google.android.material.navigation.NavigationBarView
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.databinding.ActivityBottomTabsBinding
import com.mobiledev98.foodapp.views.favorite.FavoriteFragment
import com.mobiledev98.foodapp.views.home.HomeFragment
import com.mobiledev98.foodapp.views.settings.SettingsFragment

class BottomTabsActivity : AppCompatActivity(), NavigationBarView.OnItemSelectedListener {
    private lateinit var binding: ActivityBottomTabsBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_tabs)
        binding = ActivityBottomTabsBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        initBottomNavigationView()

        if (savedInstanceState == null) {
            showFragment(R.id.item_home)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == binding.bottomNavigationView.selectedItemId) {
            return false
        }
        showFragment(item.itemId)
        return true
    }

    private fun initBottomNavigationView() {
        binding.bottomNavigationView.apply {
            setOnItemSelectedListener(this@BottomTabsActivity)
        }
    }

    private fun showFragment(bottomItemId: Int) {
        val fragment = when (bottomItemId) {
            R.id.item_home -> HomeFragment()
            R.id.item_favorite -> FavoriteFragment()
            R.id.item_settings -> SettingsFragment()
            else -> null
        }
        fragment?.apply(::replaceFragment)
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(R.id.layout_fragment_container, fragment)
            .setReorderingAllowed(true)
            .commit()
    }
}