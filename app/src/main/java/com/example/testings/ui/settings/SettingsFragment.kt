package com.example.testings.ui.settings

import android.content.SharedPreferences
import android.os.Bundle
import android.preference.Preference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.testings.R
import java.util.prefs.Preferences

class SettingsFragment : Fragment()  {

    lateinit var ViewModel: SettingsViewModel;

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        ViewModel = SettingsViewModel()
        val root = inflater.inflate(R.layout.fragment_settings, container, false)


        return root
    }

    private fun loadPreferences() {

    }

    private fun savePreferences() {

    }
}