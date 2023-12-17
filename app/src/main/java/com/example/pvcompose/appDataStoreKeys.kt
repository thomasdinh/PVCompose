package com.example.pvcompose

import androidx.datastore.preferences.core.booleanPreferencesKey



object appDataStoreKeys {
    val VIDEO_SWITCH = booleanPreferencesKey("videoSwitch")
    val SOUND_SWITCH = booleanPreferencesKey("soundSwitch")
    val BIOMETRIC_SWITCH = booleanPreferencesKey("biometricSwitch")
    val GPS_SWITCH = booleanPreferencesKey("gpsSwitch")
    val ONLINE_SWITCH = booleanPreferencesKey("onlineSwitch")
    val MOTION_SWITCH = booleanPreferencesKey("motionSwitch")
}