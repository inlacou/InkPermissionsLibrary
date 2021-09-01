package com.inlacou.inkpermissions

import android.content.Context

object InkPermissionConfig {
	var saver: ((context: Context, id: String, value: Boolean) -> Unit)? = null
	var loader: ((context: Context, id: String, defaultValue: Boolean) -> Boolean)? = null
}