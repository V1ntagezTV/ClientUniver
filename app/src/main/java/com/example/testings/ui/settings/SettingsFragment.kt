package com.example.testings.ui.settings

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.fragment.app.Fragment
import com.example.testings.R
import com.google.android.material.switchmaterial.SwitchMaterial

class SettingsFragment : Fragment()  {

    lateinit var viewModel: SettingsViewModel;
    lateinit var preferences: SharedPreferences
    lateinit var editor: SharedPreferences.Editor

    lateinit var backSwitch: SwitchMaterial
    lateinit var notificationSwitch: SwitchMaterial
    lateinit var economyTrafficSwitch: SwitchMaterial
    lateinit var imageQualitySwitch: SwitchMaterial

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val root = inflater.inflate(R.layout.fragment_settings, container, false)
        preferences = root.context.getSharedPreferences("Settings", MODE_PRIVATE)
        editor = preferences.edit()
        initSwitches(root)
        initListeners()
        viewModel = getSettingViewModel()
        setPreferences()
        return root
    }


    private fun initSwitches(view: View){
        backSwitch = view.findViewById(R.id.settings_backButton)
        notificationSwitch = view.findViewById(R.id.settings_notifications)
        economyTrafficSwitch = view.findViewById(R.id.settings_economyTraffic)
        imageQualitySwitch = view.findViewById(R.id.settings_imagesQuality)
    }

    private fun getSettingViewModel(): SettingsViewModel {
        val backButton = preferences.getBoolean("backButton", false)
        val notification = preferences.getBoolean("notification", false)
        val economyTraffic = preferences.getBoolean("economyTraffic", false)
        val imageQuality = preferences.getBoolean("imageQuality", false)
        return SettingsViewModel(backButton, notification, economyTraffic, imageQuality)
    }

    private fun initListeners() {
        backSwitch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            editor.putBoolean("backButton", b).apply()
        }
        notificationSwitch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            editor.putBoolean("notification", b).apply()
        }
        economyTrafficSwitch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            editor.putBoolean("economyTraffic", b).apply()
        }
        imageQualitySwitch.setOnCheckedChangeListener { compoundButton: CompoundButton, b: Boolean ->
            editor.putBoolean("imageQuality", b).apply()
        }
    }

    private fun setPreferences() {
        backSwitch.isChecked = viewModel.backButton
        notificationSwitch.isChecked = viewModel.notification
        economyTrafficSwitch.isChecked = viewModel.economyTrafic
        imageQualitySwitch.isChecked = viewModel.imagesQuality
    }
}