package com.ElOuedUniv.maktaba.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

@Singleton
class DataStoreManager @Inject constructor(
    @ApplicationContext private val context: Context
) {

    private val ONBOARDING_KEY = booleanPreferencesKey("onboarding_done")

    val isOnboardingDone: Flow<Boolean> =
        context.dataStore.data.map { preferences ->
            preferences[ONBOARDING_KEY] ?: false
        }

    suspend fun setOnboardingDone() {
        context.dataStore.edit { preferences ->
            preferences[ONBOARDING_KEY] = true
        }
    }
}