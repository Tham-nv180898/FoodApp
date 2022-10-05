package com.mobiledev98.foodapp.views.settings

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mobiledev98.foodapp.R
import com.mobiledev98.foodapp.databinding.FragmentHomeBinding
import com.mobiledev98.foodapp.databinding.FragmentSettingsBinding
import com.mobiledev98.foodapp.helper.Settings
import com.mobiledev98.foodapp.helper.Theme

class SettingsFragment : Fragment() {
    private lateinit var binding: FragmentSettingsBinding
    private lateinit var settings: Settings
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        settings = Settings.getInstance(requireActivity().applicationContext)
        binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSwitchButton()
    }

    private fun initSwitchButton() {
        binding.dartMode.isChecked = settings.appTheme == Theme.DARK_MODE
        binding.dartMode.setOnCheckedChangeListener { _, _ ->
            Log.d("tham", "run at here : ${binding.dartMode.isActivated}")
            if (binding.dartMode.isChecked){
                settings.updateTheme(Theme.DARK_MODE)
            }else{
                settings.updateTheme(Theme.LIGHT_MODE)
            }
        }
    }

}