package com.cyn.composeproject.utils

import android.content.Context
import android.content.SharedPreferences
import com.cyn.composeproject.AppApplication

/***
 * 封装一个sp的工具类 （datastore）
 */
object SpUtils {

    private const val SP_FILE_NAME = "SP_FILE"

    private val prefs: SharedPreferences by lazy {
        AppApplication.getInstance().getSharedPreferences(SP_FILE_NAME, Context.MODE_PRIVATE)
    }

    fun getBoolean(name: String): Boolean = prefs.getBoolean(name, false)

    fun setBoolean(name: String, value: Boolean) {
        with(prefs.edit()) {
            putBoolean(name, value)
            apply()
        }
    }

    fun setString(name: String, value: String) {
        with(prefs.edit()) {
            putString(name, value)
            apply()
        }
    }

    fun getString(name: String): String? = prefs.getString(name,"")

}