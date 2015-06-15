package com.wuhan.reservoir;

import com.nostra13.universalimageloader.cache.memory.impl.LruMemoryCache;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import android.app.Application;
import android.os.Handler;
import android.util.LruCache;

public class ReservoirApplication extends Application{
    
    private static ReservoirApplication sInstance;
    ImageLoader sImageLoader = ImageLoader.getInstance();
    private Handler mHandler;
    Thread mMainThread;
    
    @Override
    public void onCreate() {
        // TODO Auto-generated method stub
        super.onCreate();
        sInstance = this;
        mHandler = new Handler();
        mMainThread = Thread.currentThread();
        initImageLoader();
    }
    
    public static ReservoirApplication getInstance(){
        return sInstance;
    }
    
    public void runOnUiThread(Runnable runnable){
        if(Thread.currentThread() != mMainThread){
            mHandler.post(runnable);
        }else{
            runnable.run();
        }
    }
    
    public ImageLoader getImageLoader(){
        return sImageLoader;
    }
    
    private void initImageLoader(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this)
        .threadPoolSize(3)
        .memoryCache(new LruMemoryCache(2*1021*1024))
        .memoryCacheSizePercentage(13)
        .diskCacheSize(50*1024*1024)
        .build();
               
        sImageLoader.init(config);
    }
}
