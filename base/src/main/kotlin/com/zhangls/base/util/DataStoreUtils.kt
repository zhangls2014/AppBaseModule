package com.zhangls.base.util

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import com.zhangls.base.util.DataStoreUtils.dataStore
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map


/**
 * DataStore 工具类
 *
 * 使用前需要通过 preferencesDataStore(name = "[Name]") 将 dataStore 实例传给 [dataStore]
 *
 * [DataStore] 的所有类型的数据 key 必须是唯一的
 *
 * @author zhangls
 */
object DataStoreUtils {
    // 需要应用通过 val Context.dataStore by preferencesDataStore(name = "[Name]") 方式创建一个 DataStore 实例，并赋值给本工具类
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

    suspend fun put(key: String, value: Set<String>) = dataStore.edit {
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

    suspend fun remove(key: String) {
        dataStore.edit {
            when {
                it.contains(intPreferencesKey(key)) -> it.remove(intPreferencesKey(key))
                it.contains(longPreferencesKey(key)) -> it.remove(longPreferencesKey(key))
                it.contains(floatPreferencesKey(key)) -> it.remove(floatPreferencesKey(key))
                it.contains(doublePreferencesKey(key)) -> it.remove(doublePreferencesKey(key))
                it.contains(booleanPreferencesKey(key)) -> it.remove(booleanPreferencesKey(key))
                it.contains(stringPreferencesKey(key)) -> it.remove(stringPreferencesKey(key))
                it.contains(stringSetPreferencesKey(key)) -> it.remove(stringSetPreferencesKey(key))
            }
        }
    }

    suspend fun clear() {
        dataStore.edit { it.clear() }
    }
}