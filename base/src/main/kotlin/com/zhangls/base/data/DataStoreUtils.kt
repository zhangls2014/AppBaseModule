package com.zhangls.base.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


/**
 * @author zhangls
 */
object DataStoreUtils {
    lateinit var dataStore: DataStore<Preferences>


    suspend fun put(key: String, value: Int) = dataStore.edit {
        it[intPreferencesKey(key)] = value
    }

    suspend fun put(key: String, value: Long) = dataStore.edit {
        it[longPreferencesKey(key)] = value
    }

    suspend fun put(key: String, value: String) = dataStore.edit {
        it[stringPreferencesKey(key)] = value
    }

    suspend fun putStringSetData(key: String, value: Set<String>) = dataStore.edit {
        it[stringSetPreferencesKey(key)] = value
    }

    suspend fun put(key: String, value: Boolean) = dataStore.edit {
        it[booleanPreferencesKey(key)] = value
    }

    suspend fun put(key: String, value: Float) = dataStore.edit {
        it[floatPreferencesKey(key)] = value
    }

    suspend fun put(key: String, value: Double) = dataStore.edit {
        it[doublePreferencesKey(key)] = value
    }

    suspend fun getInt(key: String, default: Int = 0): Int {
        return dataStore.data.map { it[intPreferencesKey(key)] ?: default }.first()
    }

    suspend fun getLong(key: String, default: Long = 0): Long {
        return dataStore.data.map { it[longPreferencesKey(key)] ?: default }.first()
    }

    suspend fun getString(key: String, default: String): String {
        return dataStore.data.map { it[stringPreferencesKey(key)] ?: default }.first()
    }

    suspend fun getStringSet(key: String, default: Set<String> = emptySet()): Set<String> {
        return dataStore.data.map { it[stringSetPreferencesKey(key)] ?: default }.first()
    }

    suspend fun getBoolean(key: String, default: Boolean = false): Boolean {
        return dataStore.data.map { it[booleanPreferencesKey(key)] ?: default }.first()
    }

    private suspend fun getFloat(key: String, default: Float = 0F): Float {
        return dataStore.data.map { it[floatPreferencesKey(key)] ?: default }.first()
    }

    suspend fun getDouble(key: String, default: Double = 0.0): Double {
        return dataStore.data.map { it[doublePreferencesKey(key)] ?: default }.first()
    }
}