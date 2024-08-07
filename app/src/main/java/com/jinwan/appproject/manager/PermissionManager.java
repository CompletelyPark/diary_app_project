package com.jinwan.appproject.manager;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.widget.Toast;
import androidx.core.content.ContextCompat;

public class PermissionManager {
    public static final int WRITE_SETTINGS_PERMISSION_REQUEST_CODE = 0x1000;

    public static boolean checkWriteSettingsPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            return Settings.System.canWrite(activity);
        } else {
            return ContextCompat.checkSelfPermission(activity, android.Manifest.permission.WRITE_SETTINGS) == PackageManager.PERMISSION_GRANTED;
        }
    }

    public static void requestWriteSettingsPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            Intent intent = new Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS, Uri.parse("package:" + activity.getPackageName()));
            activity.startActivityForResult(intent, WRITE_SETTINGS_PERMISSION_REQUEST_CODE);
        } else {
            Toast.makeText(activity, "시스템 설정 변경 권한이 필요합니다.", Toast.LENGTH_SHORT).show();
        }
    }
}
