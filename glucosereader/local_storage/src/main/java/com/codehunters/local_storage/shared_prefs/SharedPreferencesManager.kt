package com.codehunters.local_storage.shared_prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.codehunters.local_storage.interfaces.ISharedPreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES = "user_preferences"
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES)

class SharedPreferencesManager constructor(
    private val context: Context
) : ISharedPreferencesManager {

    private val dataStore = context.dataStore

    companion object {
        private val NOTIFICATION_PREFS = intPreferencesKey("notification_prefs")
    }

    override val isNotificationFlow: Flow<Boolean>
        get() = dataStore.getValueAsFlow(NOTIFICATION_PREFS, 0).map { it != 0 }

    override val notificationFlow: Flow<Int>
        get() = dataStore.getValueAsFlow(NOTIFICATION_PREFS, 0)

    override suspend fun setNotificationInterval(value: Int) {
        dataStore.setValue(NOTIFICATION_PREFS, value)
    }
}