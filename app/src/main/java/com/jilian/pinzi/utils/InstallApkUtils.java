package com.jilian.pinzi.utils;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v4.content.FileProvider;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.ui.AndroidOPermissionActivity;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.io.File;

public class InstallApkUtils {
    private CaptureStrategy captureStrategy = new CaptureStrategy(true, Constant.FINALVALUE.FILE_PROVIDER);
    private Uri uri;
    /**
     * @param context
     */
    public void installApk(Context context, File apkFile) {
        boolean haveInstallPermission;
        // 兼容Android 8.0
        if (Build.VERSION.SDK_INT >= 26) {
            //先获取是否有安装未知来源应用的权限
            haveInstallPermission = context.getPackageManager().canRequestPackageInstalls();
            if (!haveInstallPermission) {//没有权限
                // 弹窗，并去设置页面授权
                final AndroidOInstallPermissionListener listener = new AndroidOInstallPermissionListener() {
                    @Override
                    public void permissionSuccess() {
                        installApk(context, apkFile);
                    }

                    @Override
                    public void permissionFail() {
                        ToastUitl.showImageToastFail("授权失败，无法安装应用");
                    }
                };

                AndroidOPermissionActivity.sListener = listener;
                Intent intent = new Intent(context, AndroidOPermissionActivity.class);
                context.startActivity(intent);
            } else {
                //授权之后 直接去安装
                toInstallApk(context, apkFile);
            }
        } else {
            //低于  8 .0直接去安装
            toInstallApk(context, apkFile);
        }
    }

    /**
     * 去安裝apk
     *
     * @param context
     * @param apkFile
     */
    private void toInstallApk(Context context, File apkFile) {
        Intent intentInstall = new Intent();
        intentInstall.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intentInstall.setAction(Intent.ACTION_VIEW);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
            uri = Uri.fromFile(apkFile);
        } else { // Android 7.0 以上
            uri = FileProvider.getUriForFile(context, captureStrategy.authority, apkFile);
            intentInstall.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION | Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        }
        // 安装应用
        intentInstall.setDataAndType(uri, "application/vnd.android.package-archive");
        context.startActivity(intentInstall);
    }

    public interface AndroidOInstallPermissionListener {
        void permissionSuccess();
        void permissionFail();
    }
}
