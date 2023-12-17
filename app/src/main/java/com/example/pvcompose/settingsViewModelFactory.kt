package com.example.pvcompose

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SettingsViewModelFactory(
    private val settingsDataStore: DataStore<Preferences>
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(settingsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return settingsViewModel(settingsDataStore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}