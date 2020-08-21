package com.thxx.ydzrzy.listener;

import androidx.annotation.NonNull;

public interface PermissionListener {

    /**
     * 通过授权
     *
     * @param permissions 权限集
     */
    void permissionGranted(@NonNull String[] permissions);

    /**
     * 拒绝授权
     *
     * @param permissions 权限集
     */
    void permissionDenied(@NonNull String[] permissions);
}
