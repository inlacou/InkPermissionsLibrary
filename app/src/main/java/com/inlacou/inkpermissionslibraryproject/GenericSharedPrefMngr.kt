package com.inlacou.inkpermissionslibraryproject

import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

/**
 * Created by inlacou on 18/08/17.
 */
@SuppressLint("ApplySharedPref")
object GenericSharedPrefMngr {
	
	fun eraseAll(context: Context, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		sharedPreferences.edit().clear().commit()
	}
	
	fun erase(context: Context, key: String, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		sharedPreferences.edit().remove(key).commit()
	}
	
	fun getBooleanValue(context: Context, key: String, default: Boolean = false, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)): Boolean {
		return sharedPreferences.getBoolean(key, default)
	}
	
	fun setBooleanValue(context: Context, key: String, value: Boolean, sharedPreferences: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)) {
		val editor = sharedPreferences.edit()
		editor.putBoolean(key, value)
		editor.commit()
	}
	
}