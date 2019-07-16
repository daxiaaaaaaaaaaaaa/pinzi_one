package com.jilian.pinzi.service;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.jilian.pinzi.Constant;
import com.jilian.pinzi.R;
import com.jilian.pinzi.callback.DownloadCallBack;
import com.jilian.pinzi.http.DownLoadRetrofit;
import com.jilian.pinzi.utils.InstallApkUtils;
import com.jilian.pinzi.utils.SPDownloadUtil;

import java.io.File;


/**
 * 创建时间：2018/3/7
 * 编写人：weishixiong
 * 功能描述 ：
 */

public class DownloadIntentService extends IntentService {
    private static final String TAG = "DownloadIntentService";
    private NotificationManager mNotifyManager;
    private String mDownloadFileName;

    /**
     * 刷新UI接口回调
     */
    public interface UpdateUi {
        void update(int progress);

    }

    public DownloadIntentService() {
        super("InitializeService");
    }

    private static UpdateUi mUpdateUi;

    public static void updateUi(UpdateUi updateUi) {
        mUpdateUi = updateUi;
    }

    String channelId = "my_channel_01";
    String channelName = "完美外访";

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        String downloadUrl = intent.getExtras().getString("download_url");
        final int downloadId = intent.getExtras().getInt("download_id");
        mDownloadFileName = intent.getExtras().getString("download_file");
        Log.d(TAG, "download_url --" + downloadUrl);
        Log.d(TAG, "download_file --" + mDownloadFileName);
        final File file = new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), mDownloadFileName);
        long range = 0;
        int progress = 0;
        if (file.exists()) {
            range = SPDownloadUtil.getInstance().get(downloadUrl, 0);
            progress = (int) (range * 100 / file.length());
            if (range == file.length()) {
                installApp(file);
                return;
            }
        }
        new DownLoadRetrofit(Constant.Server.DOWN_URL).downloadFile(range, "donghui_oa" + downloadUrl.split("donghui_oa")[1], mDownloadFileName, file, new DownloadCallBack() {
            @Override
            public void onProgress(int progress) {
                notificationBuilder.setProgress(100, progress++, false);
                Notification notification = notificationBuilder.build();
                mNotifyManager.notify(notificationID, notification);
                mUpdateUi.update(progress);
            }

            @Override
            public void onCompleted() {
                Log.d(TAG, "下载完成");
                mNotifyManager.cancel(downloadId);
                installApp(file);
            }

            @Override
            public void onError(String msg) {
                mNotifyManager.cancel(downloadId);
                Log.d(TAG, "下载发生错误--" + msg);
            }
        });
    }

    private NotificationCompat.Builder notificationBuilder;
    final Integer notificationID = 100;

    @Override
    public int onStartCommand(@Nullable Intent intent, int flags, int startId) {
        mNotifyManager = (NotificationManager)
                getSystemService(Context.NOTIFICATION_SERVICE);
        /**
         * 8.0以上需要增加channel
         */
        if (Build.VERSION.SDK_INT >= 26) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_LOW);
            channel.enableLights(false);
            channel.enableVibration(false);
            //channel.importance = NotificationManager.IMPORTANCE_LOW //设置为low, 通知栏不会有声音
            mNotifyManager.createNotificationChannel(channel);
        }
        //Set notification information:
        notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), channelId);
        notificationBuilder.setOngoing(true)
                .setSmallIcon(R.drawable.image_logo)
                .setContentTitle("779百香街")
                .setContentText("正在下载...")
                .setProgress(100, 0, false);
        //Send the notification:
        final Notification notification = notificationBuilder.build();
        startForeground(notificationID, notification);
        return super.onStartCommand(intent, flags, startId);
    }

    /**
     * 安装apk
     *
     * @param file
     */
    private void installApp(File file) {
        new InstallApkUtils().installApk(this, file);
    }
}
