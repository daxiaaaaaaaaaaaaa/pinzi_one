package com.jilian.pinzi.utils;

import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.media.MediaMetadataRetriever;

import java.util.HashMap;

public class BitmapUtils {
    /**
     * 压缩
     * @param bitmap
     * @return
     */
    public static Bitmap getScanBitmap(Bitmap bitmap){
        if(bitmap==null){
            return null;
        }
        //压缩
        Matrix matrix = new Matrix();
        matrix.setScale(0.4f, 0.4f);
        return  Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(),bitmap.getHeight(), matrix, true);
    }

    /**
     * 获取视频封面
     * @param videoUrl
     * @return
     */
    public static  Bitmap getNetVideoBitmap(String videoUrl) {
        Bitmap bitmap = null;

        MediaMetadataRetriever retriever = new MediaMetadataRetriever();
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, new HashMap());
            //获得第一帧图片
            bitmap = retriever.getFrameAtTime();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            retriever.release();
        }

        return bitmap;
    }
}
