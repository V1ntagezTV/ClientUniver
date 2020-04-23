package com.example.testings.ui.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SettingsViewModel : ViewModel() {
    init {
        var backClickOpenedNavView: MutableLiveData<Boolean>
        var notification: MutableLiveData<Boolean>
        var economTraffic: MutableLiveData<Boolean>
        var imagesLoadedLowGraph: MutableLiveData<Boolean>
    }
}