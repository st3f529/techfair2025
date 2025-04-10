package com.simple.imagegenerator.core

import android.content.Context
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore

object PreferenceStore {

    val Context.dataStore by preferencesDataStore(name = "defaultSettings")

    val SELECTED_IMAGE_RATIO = stringPreferencesKey("selected_image_ratio")

}