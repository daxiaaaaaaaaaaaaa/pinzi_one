package com.jilian.pinzi.http;

import android.os.Environment;
import android.util.Log;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.jilian.pinzi.PinziApplication;
import com.jilian.pinzi.callback.DownloadCallBack;
import com.jilian.pinzi.interceptor.AddCookiesInterceptor;
import com.jilian.pinzi.interceptor.ReceivedCookiesInterceptor;
import com.jilian.pinzi.utils.SPDownloadUtil;

import java.io.File;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DownLoadRetrofit {
    private static final int DEFAULT_TIMEOUT = 10;
    private static final String TAG = "RetrofitClient";

    private ApiService apiService;

    private OkHttpClient okHttpClient;

    private  String mBaseUrl;

    private DownLoadRetrofit sIsntance;

    public DownLoadRetrofit(String mBaseUrl) {
        this.mBaseUrl = mBaseUrl;
        getInstance();
    }


    private void getInstance() {
        okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .addInterceptor(new AddCookiesInterceptor()) //这部分
                .addInterceptor(new ReceivedCookiesInterceptor()) //这部分
                .build();
        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(mBaseUrl)
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    /**
     *
     * @param range
     * @param url
     * @param fileName
     * @param file
     * @param downloadCallback
     */
    public void downloadFile(final long range, final String url, final String fileName, File file,final DownloadCallBack downloadCallback) {
        //断点续传时请求的总长度
        String totalLength = "-";
        if (file.exists()) {
            totalLength += file.length();
        }
        apiService.executeDownload("bytes=" + Long.toString(range) + totalLength, url)
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                    }
                    @Override
                    public void onNext(ResponseBody responseBody) {
                        RandomAccessFile randomAccessFile = null;
                        InputStream inputStream = null;
                        long total = range;
                        long responseLength = 0;
                        try {
                            byte[] buf = new byte[2048];
                            int len = 0;
                            responseLength = responseBody.contentLength();
                            inputStream = responseBody.byteStream();
                            //文件
                            File file = new File(PinziApplication.getInstance().getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS), fileName);
//                            //文件夹
                            randomAccessFile = new RandomAccessFile(file, "rwd");
                            if (range == 0) {
                                randomAccessFile.setLength(responseLength);
                            }
                            randomAccessFile.seek(range);
                            int progress = 0;
                            int lastProgress = 0;
                          while ((len = inputStream.read(buf)) != -1) {
                                randomAccessFile.write(buf, 0, len);
                                total += len;
                                lastProgress = progress;
                                progress = (int) (total * 100 / randomAccessFile.length());
                                if (progress > 0 && progress != lastProgress) {
                                    downloadCallback.onProgress(progress);
                                }
                            }
                            downloadCallback.onCompleted();
                        } catch (Exception e) {
                            Log.d(TAG, e.getMessage());
                            downloadCallback.onError(e.getMessage());
                            e.printStackTrace();
                        } finally {
                            try {
                                SPDownloadUtil.getInstance().save(url, total);
                                if (randomAccessFile != null) {
                                    randomAccessFile.close();
                                }

                                if (inputStream != null) {
                                    inputStream.close();
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        downloadCallback.onError(e.toString());
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
