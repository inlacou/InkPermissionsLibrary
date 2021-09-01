package com.inlacou.inkpermissionslibraryproject

import android.Manifest
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.inlacou.inkpermissions.InkPermissionStatus
import com.inlacou.inkpermissions.InkPermissionUtils
import com.inlacou.inkpermissionslibraryproject.databinding.FragmentFirstBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class FirstFragment : Fragment() {
	
	private var binding: FragmentFirstBinding? = null
	private val permissions = listOf(
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.READ_EXTERNAL_STORAGE,
		Manifest.permission.CALL_PHONE,
		Manifest.permission.CAMERA,
	).toTypedArray()
	private val criticalPermissions = listOf(
		Manifest.permission.ACCESS_FINE_LOCATION,
		Manifest.permission.READ_EXTERNAL_STORAGE,
	).toTypedArray()
	
	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFirstBinding.inflate(inflater, container, false)
		return binding?.root
	}
	
	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		
		binding?.buttonFirst?.setOnClickListener {
			InkPermissionUtils.request(
				activity = requireActivity(),
				permissions = permissions,
				criticalPermissions = criticalPermissions,
				listener = { map ->
				binding?.textviewFirst?.let{ tv ->
					tv.text = ""
					val size = map.toList().map { it.first.length }.sortedByDescending { it }.first()
					map.toList().forEach { pair ->
						tv.text = tv.text.toString() + "\n" + pair.first + ":"
						val currentSize = pair.first.length
						repeat(size-currentSize) { tv.text = tv.text.toString() + " " }
						tv.text = tv.text.toString() + " " + when(pair.second) {
							InkPermissionStatus.BLOCKED -> "blocked"
							InkPermissionStatus.DENIED -> "denied"
							InkPermissionStatus.DENIED_BUT_ASKED -> "denied by default"
							InkPermissionStatus.GRANTED -> "granted"
							InkPermissionStatus.ERROR -> "error"
						}
					}
				}
			})
		}
	}
	
	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}