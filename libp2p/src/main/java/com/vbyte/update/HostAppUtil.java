package com.vbyte.update;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.PackageManager.NameNotFoundException;
import android.util.Log;

public class HostAppUtil {
	private static final String TAG = "HostAppUtil";
	
	private static final String Preference = "P2PMODULE";
	private static final String Version = "HOSTVERSION";
	public static boolean isRecored(Context context) {
		try {
			SharedPreferences sp = context.getSharedPreferences(Preference,
					context.MODE_PRIVATE);
			String version = context.getPackageManager().getPackageInfo(
					context.getPackageName(), 0).versionName;
			
			// 第一次检测更新
			Editor editor = sp.edit();
			if (sp == null || !sp.contains(Version)) {
				Log.e(TAG, "never update before, and app's version is " + version);
				editor.putString(Version,version);
				editor.commit();
				return false;
			} else if (sp.getString(Version, "").equals(version)) {
				Log.e(TAG, "host version is recored");
				return true;
			} else {
				// 宿主app版本变更
				Log.e("MSG", "host app updated before recored");
				editor.putString(Version,version);
				editor.commit();
				return true;
			}
		} catch (NameNotFoundException e) {
			e.printStackTrace();
			Log.e("MSG", "never update before");
			return true;
		}
	}
}
