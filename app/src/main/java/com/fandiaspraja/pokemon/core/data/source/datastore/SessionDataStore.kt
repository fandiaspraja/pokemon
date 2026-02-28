package com.fandiaspraja.pokemon.core.data.source.local.datastore

import android.content.Context
import androidx.datastore.preferences.core.*
import com.fandiaspraja.pokemon.core.data.source.datastore.dataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionDataStore(private val context: Context) {

    private val dataStore = context.dataStore

    companion object {
        val USER_ID = intPreferencesKey("user_id")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_EMAIL = stringPreferencesKey("user_email")
        val IS_LOGIN = booleanPreferencesKey("is_login")
    }

    // SAVE SESSION
    suspend fun saveSession(id: Int?, name: String?, email: String?) {
        dataStore.edit { pref ->
            pref[USER_ID] = id as Int
            pref[USER_NAME] = name as String
            pref[USER_EMAIL] = email as String
            pref[IS_LOGIN] = true
        }
    }

    // CLEAR SESSION
    suspend fun clearSession() {
        dataStore.edit { pref ->
            pref.clear()
        }
    }

    // GET SESSION DATA
    val userId: Flow<Int?> = dataStore.data.map { it[USER_ID] }
    val userName: Flow<String?> = dataStore.data.map { it[USER_NAME] }
    val userEmail: Flow<String?> = dataStore.data.map { it[USER_EMAIL] }
    val isLogin: Flow<Boolean> = dataStore.data.map { it[IS_LOGIN] ?: false }
}