# InkPermissionsLibrary
Simple callback style permission requesting

Import with `implementation 'com.github.inlacou:InkPermissionsLibrary:'` [![](https://jitpack.io/v/inlacou/InkPermissionsLibrary.svg)](https://jitpack.io/#inlacou/InkPermissionsLibrary)


## Configuration

Add activity to your manifest:

```Xml
<application ...>
    ...
    <activity
        android:name="com.inlacou.inkpermissions.InkRequestPermissionActivity"
        android:theme="@style/Theme.InkPermissionsLibraryProject.NoActionBar"/>
    ...
</application>
```
            
Add loader and saver to InkPermissionConfig:

```Kt
InkPermissionConfig.saver = { context: Context, name: String, value: Boolean ->
  //In this example, we save to sharedprefs
  GenericSharedPrefMngr.setBooleanValue(context, name, value)
}
InkPermissionConfig.loader = { context: Context, name: String, defaultValue: Boolean ->
  //In this example, we load from sharedprefs
  GenericSharedPrefMngr.getBooleanValue(context, name, defaultValue)
}
```

## Usage

Define permissions on Manifest:

```Xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.ACCESS_FINE_LOCATION" />
<uses-permission android:name="android.permission.ACCESS_COARSE_LOCATION" />
<uses-permission android:name="android.permission.CALL_PHONE" />
<uses-permission android:name="android.permission.CAMERA" />
```

Request permissions:

```Kt
InkPermissionUtils.request(
  activity = requireActivity(),
  permissions = listOf(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.READ_EXTERNAL_STORAGE,
      Manifest.permission.CALL_PHONE,
      Manifest.permission.CAMERA,
    ).toTypedArray(),
  criticalPermissions = listOf(
      Manifest.permission.ACCESS_FINE_LOCATION,
      Manifest.permission.READ_EXTERNAL_STORAGE,
    ).toTypedArray(),
  listener = { map ->
  binding?.textviewFirst?.let{ tv ->
    map.toList().forEach { pair: Pair<String, InkPermissionStatus> ->
      //pair.first is the permission, for example android.permission.ACCESS_FINE_LOCATION
      //pair.second has the permission status, which varies between BLOCKED, DENIED, DENIED_BUT_ASKED, GRANTED, and ERROR
    }
  }
})
```

Status types:

* GRANTED: permission granted
* DENIED: permission denied (default)
* DENIED_BUT_ASKED: permission denied (asked for, but refused by user)
* BLOCKED: permission denied (asked for, but refused by user and checked the checkbox to prevent future requests)
* ERROR: should not happen

Critical permissions will be asked for even when BLOCKED. If asking for a BLOCKED permission, the request will be different and it will take the user to the app setting screen on Android settings.

If permissions are already granted, no request will be shown and the callback will return all permissions with GRANTED status.

If you need to manually check for permissions:

```Kt
val status: InkPermissionStatus = InkPermissionUtils.getPermissionStatus(requireActivity(), Manifest.permission.Camera)
```




