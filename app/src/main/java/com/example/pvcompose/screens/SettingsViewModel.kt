package com.example.pvcompose.screens

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.pvcompose.settings.appDataStoreKeys
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch


class settingsViewModel(
    private val settingsDataStore : DataStore<Preferences>
): ViewModel(
) {
     val videoSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
             preferences ->
         preferences[appDataStoreKeys.VIDEO_SWITCH]
     }
    val soundSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
            preferences ->
        preferences[appDataStoreKeys.SOUND_SWITCH]
    }
    val biometricSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
            preferences ->
        preferences[appDataStoreKeys.BIOMETRIC_SWITCH]
    }
    val gpsSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
            preferences ->
        preferences[appDataStoreKeys.GPS_SWITCH]
    }
    val onlineSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
            preferences ->
        preferences[appDataStoreKeys.ONLINE_SWITCH]
    }
    val motionSwitchFlow : Flow<Boolean?> =settingsDataStore.data.map {
            preferences ->
        preferences[appDataStoreKeys.MOTION_SWITCH]
    }

    fun saveVideoSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.VIDEO_SWITCH] = isChecked
            }
        }
    }
    fun saveSoundSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.SOUND_SWITCH] = isChecked
            }
        }
    }
    fun saveBiometricSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.BIOMETRIC_SWITCH] = isChecked
            }
        }
    }

    fun saveGpsSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.GPS_SWITCH] = isChecked
            }
        }
    }

    fun saveOnlineSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.ONLINE_SWITCH] = isChecked
            }
        }
    }

    fun saveMotionSwitch(isChecked : Boolean){
        viewModelScope.launch {
            settingsDataStore.edit { preferences ->
                preferences[appDataStoreKeys.MOTION_SWITCH] = isChecked
            }
        }
    }

}


