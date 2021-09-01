package com.inlacou.inkpermissions

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.Settings
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.lang.Exception

object InkPermissionUtils {

	val allPermissions = listOf(
			Manifest.permission.ACCEPT_HANDOVER,
			Manifest.permission.ACCESS_BACKGROUND_LOCATION,
			Manifest.permission.ACCESS_CHECKIN_PROPERTIES,
			Manifest.permission.ACCESS_COARSE_LOCATION,
			Manifest.permission.ACCESS_FINE_LOCATION,
			Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
			Manifest.permission.ACCESS_MEDIA_LOCATION,
			Manifest.permission.ACCESS_NETWORK_STATE,
			Manifest.permission.ACCESS_NOTIFICATION_POLICY,
			Manifest.permission.ACCESS_WIFI_STATE,
			Manifest.permission.ACCOUNT_MANAGER,
			Manifest.permission.ACTIVITY_RECOGNITION,
			Manifest.permission.ADD_VOICEMAIL,
			Manifest.permission.ANSWER_PHONE_CALLS,
			Manifest.permission.BATTERY_STATS,
			Manifest.permission.BIND_ACCESSIBILITY_SERVICE,
			Manifest.permission.BIND_APPWIDGET,
			Manifest.permission.BIND_AUTOFILL_SERVICE,
			Manifest.permission.BIND_CALL_REDIRECTION_SERVICE,
			Manifest.permission.BIND_CARRIER_MESSAGING_CLIENT_SERVICE,
			Manifest.permission.BIND_CARRIER_MESSAGING_SERVICE,
			Manifest.permission.BIND_CARRIER_SERVICES,
			Manifest.permission.BIND_CHOOSER_TARGET_SERVICE,
			Manifest.permission.BIND_CONDITION_PROVIDER_SERVICE,
			Manifest.permission.BIND_CONTROLS,
			Manifest.permission.BIND_DEVICE_ADMIN,
			Manifest.permission.BIND_DREAM_SERVICE,
			Manifest.permission.BIND_INCALL_SERVICE,
			Manifest.permission.BIND_INPUT_METHOD,
			Manifest.permission.BIND_MIDI_DEVICE_SERVICE,
			Manifest.permission.BIND_NFC_SERVICE,
			Manifest.permission.BIND_NOTIFICATION_LISTENER_SERVICE,
			Manifest.permission.BIND_PRINT_SERVICE,
			Manifest.permission.BIND_QUICK_ACCESS_WALLET_SERVICE,
			Manifest.permission.BIND_QUICK_SETTINGS_TILE,
			Manifest.permission.BIND_REMOTEVIEWS,
			Manifest.permission.BIND_SCREENING_SERVICE,
			Manifest.permission.BIND_TELECOM_CONNECTION_SERVICE,
			Manifest.permission.BIND_TEXT_SERVICE,
			Manifest.permission.BIND_TV_INPUT,
			Manifest.permission.BIND_VISUAL_VOICEMAIL_SERVICE,
			Manifest.permission.BIND_VOICE_INTERACTION,
			Manifest.permission.BIND_VPN_SERVICE,
			Manifest.permission.BIND_VR_LISTENER_SERVICE,
			Manifest.permission.BIND_WALLPAPER,
			Manifest.permission.BLUETOOTH,
			Manifest.permission.BLUETOOTH_ADMIN,
			Manifest.permission.BLUETOOTH_PRIVILEGED,
			Manifest.permission.BODY_SENSORS,
			Manifest.permission.BROADCAST_PACKAGE_REMOVED,
			Manifest.permission.BROADCAST_SMS,
			Manifest.permission.BROADCAST_STICKY,
			Manifest.permission.BROADCAST_WAP_PUSH,
			Manifest.permission.CALL_COMPANION_APP,
			Manifest.permission.CALL_PHONE,
			Manifest.permission.CALL_PRIVILEGED,
			Manifest.permission.CAMERA,
			Manifest.permission.CAPTURE_AUDIO_OUTPUT,
			Manifest.permission.CHANGE_COMPONENT_ENABLED_STATE,
			Manifest.permission.CHANGE_CONFIGURATION,
			Manifest.permission.CHANGE_NETWORK_STATE,
			Manifest.permission.CHANGE_WIFI_MULTICAST_STATE,
			Manifest.permission.CHANGE_WIFI_STATE,
			Manifest.permission.CLEAR_APP_CACHE,
			Manifest.permission.CONTROL_LOCATION_UPDATES,
			Manifest.permission.DELETE_CACHE_FILES,
			Manifest.permission.DELETE_PACKAGES,
			Manifest.permission.DIAGNOSTIC,
			Manifest.permission.DISABLE_KEYGUARD,
			Manifest.permission.DUMP,
			Manifest.permission.EXPAND_STATUS_BAR,
			Manifest.permission.FACTORY_TEST,
			Manifest.permission.FOREGROUND_SERVICE,
			Manifest.permission.GET_ACCOUNTS,
			Manifest.permission.GET_ACCOUNTS_PRIVILEGED,
			Manifest.permission.GET_PACKAGE_SIZE,
			Manifest.permission.GET_TASKS,
			Manifest.permission.GLOBAL_SEARCH,
			Manifest.permission.INSTALL_LOCATION_PROVIDER,
			Manifest.permission.INSTALL_PACKAGES,
			Manifest.permission.INSTALL_SHORTCUT,
			Manifest.permission.INSTANT_APP_FOREGROUND_SERVICE,
			Manifest.permission.INTERACT_ACROSS_PROFILES,
			Manifest.permission.INTERNET,
			Manifest.permission.KILL_BACKGROUND_PROCESSES,
			Manifest.permission.LOADER_USAGE_STATS,
			Manifest.permission.LOCATION_HARDWARE,
			Manifest.permission.MANAGE_DOCUMENTS,
			Manifest.permission.MANAGE_EXTERNAL_STORAGE,
			Manifest.permission.MANAGE_OWN_CALLS,
			Manifest.permission.MASTER_CLEAR,
			Manifest.permission.MEDIA_CONTENT_CONTROL,
			Manifest.permission.MODIFY_AUDIO_SETTINGS,
			Manifest.permission.MODIFY_PHONE_STATE,
			Manifest.permission.MOUNT_FORMAT_FILESYSTEMS,
			Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
			Manifest.permission.NFC,
			Manifest.permission.NFC_PREFERRED_PAYMENT_INFO,
			Manifest.permission.NFC_TRANSACTION_EVENT,
			Manifest.permission.PACKAGE_USAGE_STATS,
			Manifest.permission.PERSISTENT_ACTIVITY,
			Manifest.permission.PROCESS_OUTGOING_CALLS,
			Manifest.permission.QUERY_ALL_PACKAGES,
			Manifest.permission.READ_CALENDAR,
			Manifest.permission.READ_CALL_LOG,
			Manifest.permission.READ_CONTACTS,
			Manifest.permission.READ_EXTERNAL_STORAGE,
			Manifest.permission.READ_INPUT_STATE,
			Manifest.permission.READ_LOGS,
			Manifest.permission.READ_PHONE_NUMBERS,
			Manifest.permission.READ_PHONE_STATE,
			Manifest.permission.READ_PRECISE_PHONE_STATE,
			Manifest.permission.READ_SMS,
			Manifest.permission.READ_SYNC_SETTINGS,
			Manifest.permission.READ_SYNC_STATS,
			Manifest.permission.READ_VOICEMAIL,
			Manifest.permission.REBOOT,
			Manifest.permission.RECEIVE_BOOT_COMPLETED,
			Manifest.permission.RECEIVE_MMS,
			Manifest.permission.RECEIVE_SMS,
			Manifest.permission.RECEIVE_WAP_PUSH,
			Manifest.permission.RECORD_AUDIO,
			Manifest.permission.REORDER_TASKS,
			Manifest.permission.REQUEST_COMPANION_RUN_IN_BACKGROUND,
			Manifest.permission.REQUEST_COMPANION_USE_DATA_IN_BACKGROUND,
			Manifest.permission.REQUEST_DELETE_PACKAGES,
			Manifest.permission.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS,
			Manifest.permission.REQUEST_INSTALL_PACKAGES,
			Manifest.permission.REQUEST_PASSWORD_COMPLEXITY,
			Manifest.permission.RESTART_PACKAGES,
			Manifest.permission.SEND_RESPOND_VIA_MESSAGE,
			Manifest.permission.SEND_SMS,
			Manifest.permission.SET_ALARM,
			Manifest.permission.SET_ALWAYS_FINISH,
			Manifest.permission.SET_ANIMATION_SCALE,
			Manifest.permission.SET_DEBUG_APP,
			Manifest.permission.SET_PREFERRED_APPLICATIONS,
			Manifest.permission.SET_PROCESS_LIMIT,
			Manifest.permission.SET_TIME,
			Manifest.permission.SET_TIME_ZONE,
			Manifest.permission.SET_WALLPAPER,
			Manifest.permission.SET_WALLPAPER_HINTS,
			Manifest.permission.SIGNAL_PERSISTENT_PROCESSES,
			Manifest.permission.SMS_FINANCIAL_TRANSACTIONS,
			Manifest.permission.START_VIEW_PERMISSION_USAGE,
			Manifest.permission.STATUS_BAR,
			Manifest.permission.SYSTEM_ALERT_WINDOW,
			Manifest.permission.TRANSMIT_IR,
			Manifest.permission.UNINSTALL_SHORTCUT,
			Manifest.permission.UPDATE_DEVICE_STATS,
			Manifest.permission.USE_BIOMETRIC,
			Manifest.permission.USE_FINGERPRINT,
			Manifest.permission.USE_FULL_SCREEN_INTENT,
			Manifest.permission.USE_SIP,
			Manifest.permission.VIBRATE,
			Manifest.permission.WAKE_LOCK,
			Manifest.permission.WRITE_APN_SETTINGS,
			Manifest.permission.WRITE_CALENDAR,
			Manifest.permission.WRITE_CALL_LOG,
			Manifest.permission.WRITE_CONTACTS,
			Manifest.permission.WRITE_EXTERNAL_STORAGE,
			Manifest.permission.WRITE_GSERVICES,
			Manifest.permission.WRITE_SECURE_SETTINGS,
			Manifest.permission.WRITE_SETTINGS,
			Manifest.permission.WRITE_SYNC_SETTINGS,
			Manifest.permission.WRITE_VOICEMAIL,
			"android.car.permission.CAR_VENDOR_EXTENSION",
	).toTypedArray()

	fun request(activity: Activity, permission: String, critical: Boolean = true, listener: ((Map<String, InkPermissionStatus>) -> Unit)? = null){
		request(activity, arrayOf(permission), if(critical) arrayOf(permission) else arrayOf(), listener)
	}

	fun request(activity: Activity, permissions: Array<String>, criticalPermissions: Array<String> = permissions, listener: ((Map<String, InkPermissionStatus>) -> Unit)? = null){
		if(permissions.filter { getPermissionStatus(activity, it) != InkPermissionStatus.GRANTED }.isEmpty()){
			//If there are no non-granted permissions
			val map = mutableMapOf<String, InkPermissionStatus>()
			permissions.forEach { map.put(it, InkPermissionStatus.GRANTED) }
			listener?.invoke(map)
		}else {
			InkRequestPermissionActivity.launch(activity, permissions, criticalPermissions) {
				listener?.invoke(it)
			}
		}
	}

	fun getPermissionStatus(activity: Activity, permission: String): InkPermissionStatus {
		val asked = InkPermissionConfig.loader?.invoke(activity, "${permission}_asked", false) ?: throw Exception("No loader function provided to InkPermissionConfig")
		return if (ContextCompat.checkSelfPermission(activity, permission)!=PackageManager.PERMISSION_GRANTED) {
			if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
				if(asked){
					InkPermissionStatus.BLOCKED
				}else{
					InkPermissionStatus.DENIED
				}
			} else if(asked){
				InkPermissionStatus.DENIED_BUT_ASKED
			}else{
				InkPermissionStatus.DENIED
			}
		} else InkPermissionStatus.GRANTED
	}

	fun openAppSettings(activity: Activity, requestCode: Int){
		val intent = Intent()
		intent.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
		intent.addCategory(Intent.CATEGORY_DEFAULT)
		val uri = Uri.fromParts("package", activity.packageName, null)
		intent.addFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
		intent.addFlags(Intent.FLAG_ACTIVITY_EXCLUDE_FROM_RECENTS)
		intent.data = uri
		activity.startActivityForResult(intent, requestCode)
	}
}