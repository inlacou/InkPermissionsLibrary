package com.inlacou.inkpermissionslibraryproject

import android.app.Application
import android.content.Context
import com.inlacou.inkpermissions.InkPermissionConfig

class AppCtrl: Application() {
	
	override fun onCreate() {
		super.onCreate()
		InkPermissionConfig.loader = { context: Context, name: String, defaultValue: Boolean ->
			GenericSharedPrefMngr.getBooleanValue(context, name, defaultValue)
		}
		InkPermissionConfig.saver = { context: Context, name: String, value: Boolean ->
			GenericSharedPrefMngr.setBooleanValue(context, name, value)
		}
	}
	
}