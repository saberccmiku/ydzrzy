package com.thxx.ydzrzy.activity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.NonNull;

import com.thxx.ydzrzy.R;
import com.thxx.ydzrzy.base.RxBaseActivity;
import com.thxx.ydzrzy.listener.PermissionListener;
import com.thxx.ydzrzy.utils.PermissionsUtil;

public class SplashActivity extends RxBaseActivity {
    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViews(Bundle savedInstanceState) {
        checkPermission();
    }

    @Override
    public void initToolBar() {

    }

    private void checkPermission() {
        //检查权限
        String[] permissions = {
                Manifest.permission.ACCESS_NETWORK_STATE,
                Manifest.permission.ACCESS_WIFI_STATE,
                Manifest.permission.INTERNET,
                Manifest.permission.CALL_PHONE,
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.WRITE_EXTERNAL_STORAGE,
                Manifest.permission.ACCESS_FINE_LOCATION
        };
        PermissionsUtil.requestPermission(this, new PermissionListener() {
            @Override
            public void permissionGranted(@NonNull String[] permissions) {
                new Handler().postDelayed(() -> {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                    SplashActivity.this.finish();
                }, 2000);
            }

            @Override
            public void permissionDenied(@NonNull String[] permissions) {
                System.exit(0);
            }
        }, permissions);
    }
}
