package com.inlacou.inkpermissions

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.core.app.ActivityCompat
import java.lang.Exception
import java.util.*

/**
 * Created by https://gist.github.com/kyze8439690
 * Edited by Inlacou on 07/11/2018
 */
class InkRequestPermissionActivity : Activity() {

	private var mPermissions: List<String> = listOf()
	private var mCriticalPermissions: List<String> = listOf()
	private var mGrantedPermissions: MutableList<String> = mutableListOf()

	private var forceBlockeds = true

	companion object {
		private const val REQUEST_GRANT_PERMISSION = 236
		private const val REQUEST_RETRY_BLOCKED = 237

		private var sListener: ((Map<String, InkPermissionStatus>) -> Unit)? = null

		internal fun launch(activity: Activity, permissions: Array<String>, criticalPermissions: Array<String>, listener: (Map<String, InkPermissionStatus>) -> Unit) {
			val intent = Intent(activity, InkRequestPermissionActivity::class.java)
			intent.putExtra("permissions", permissions)
			intent.putExtra("permissions_critical", criticalPermissions)
			activity.startActivity(intent)
			activity.overridePendingTransition(0, 0)
			sListener = listener
		}
	}

	public override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)

		//Get data from intent
		mPermissions = intent.getStringArrayExtra("permissions")?.toList() ?: listOf()
		mCriticalPermissions = intent.getStringArrayExtra("permissions_critical")?.toList() ?: listOf()

		work()
	}

	private fun work() {
		//filter granted permission
		val permissionsNotGranted = ArrayList<String>()
		mGrantedPermissions = ArrayList()
		mPermissions.forEach {
			if (ActivityCompat.checkSelfPermission(this, it) == PackageManager.PERMISSION_DENIED) {
				permissionsNotGranted.add(it)
			} else {
				mGrantedPermissions.add(it)
			}
		}

		if (permissionsNotGranted.isEmpty()) {
			returnAllGranted()
			return
		}

		permissionsNotGranted.forEach { InkPermissionConfig.saver?.invoke(this, "${it}_asked", true) ?: throw Exception("No saver function provided to InkPermissionConfig") }
		ActivityCompat.requestPermissions(this,
				permissionsNotGranted.toTypedArray(),
				REQUEST_GRANT_PERMISSION
		)
	}

	override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
	                                        grantResults: IntArray) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults)
		if (requestCode != REQUEST_GRANT_PERMISSION) return
		val result = HashMap<String, InkPermissionStatus>()
		mPermissions.forEach { permission ->
			var done = false
			for (i in permissions.indices) {
				if (permissions[i] == permission) {
					result[permission] = when(grantResults[i]){
						PackageManager.PERMISSION_GRANTED -> {
							InkPermissionStatus.GRANTED
						}
						PackageManager.PERMISSION_DENIED -> {
							getPermissionBlockedType(permission)
						}
						else -> {
							InkPermissionStatus.ERROR
						}
					}
					done = true
					break
				}
			}
			if (!done) {
				if (mGrantedPermissions.contains(permission)) {
					result[permission] = InkPermissionStatus.GRANTED
				} else {
					result[permission] = getPermissionBlockedType(permission)
				}
			}
		}

		end(result)
	}

	private fun getPermissionBlockedType(permission: String): InkPermissionStatus {
		return if (!ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
			if(InkPermissionConfig.loader?.invoke(this, "${permission}_asked", false) ?: throw Exception("No loader function provided to InkPermissionConfig")) InkPermissionStatus.BLOCKED
			else InkPermissionStatus.DENIED
			
		} else InkPermissionStatus.DENIED
	}

	private fun returnAllGranted() {
		val result = HashMap<String, InkPermissionStatus>()
		for (permission in mPermissions) {
			result[permission] = InkPermissionStatus.GRANTED
		}
		end(result)
	}

	private fun end(result: HashMap<String, InkPermissionStatus>) {
		var finished = true
		val criticalNotAcceptedPermissions = mutableListOf<String>()
		if(forceBlockeds) {
			result.keys.forEach {
				if (mCriticalPermissions.contains(it) && result[it] == InkPermissionStatus.BLOCKED) {
					finished = false
					criticalNotAcceptedPermissions.add(it)
				}
			}
		}
		if(finished) {
			realEnd(result)
		}else{
			var title = ""
			var content = ""
			when {
				criticalNotAcceptedPermissions.size==1 -> {
					title = getString(R.string.Forced_permissions_title_singular)
					content = getString(R.string.Forced_permissions_content_pre_singular)
					mCriticalPermissions.forEach { content += " $it " }
					content += getString(R.string.Forced_permissions_content_post_singular)
				}
				criticalNotAcceptedPermissions.size==2 -> {
					title = getString(R.string.Forced_permissions_title_plural)
					content = getString(R.string.Forced_permissions_content_pre_plural)
					mCriticalPermissions.take(1).forEach { content += " $it," }
					mCriticalPermissions.takeLast(1).forEach { content += " $it" }
					content += getString(R.string.Forced_permissions_content_post_plural)
				}
				criticalNotAcceptedPermissions.size>2 -> {
					title = getString(R.string.Forced_permissions_title_plural)
					content = getString(R.string.Forced_permissions_content_pre_plural)
					mCriticalPermissions.take(criticalNotAcceptedPermissions.size-2).forEach { content += " $it," }
					mCriticalPermissions.takeLast(1).forEach { content += " $it" }
					content += getString(R.string.Forced_permissions_content_post_plural)
				}
			}

			AlertDialog.Builder(this)
					.setTitle(title)
					.setMessage(content)
					.setPositiveButton(R.string.Forced_permissions_positive_button) { dialogInterface: DialogInterface, i: Int ->
						InkPermissionUtils.openAppSettings(this, REQUEST_GRANT_PERMISSION)
					}
					.setNegativeButton(R.string.Forced_permissions_negative_button) { dialogInterface: DialogInterface, i: Int ->
						realEnd(result)
					}
					.show()
		}
	}

	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		forceBlockeds = false
		work()
	}

	private fun realEnd(result: HashMap<String, InkPermissionStatus>){
		sListener?.invoke(result)
		sListener = null
		finish()
		overridePendingTransition(0, 0)
	}
}