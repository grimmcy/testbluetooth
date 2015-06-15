package com.wuhan.reservoir.datamanager;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuhan.reservoir.ReservoirApplication;
import com.wuhan.reservoir.asynctask.LoadJsonTask;
import com.wuhan.reservoir.bean.RequestResult;
import com.wuhan.reservoir.bean.ReservoirInfo;
import com.wuhan.reservoir.bean.ReservoirItem;

import android.os.Message;

public class DataManager {
    
    public interface OnEventListener{       
        public static final int EVENT_RESERVOIR_INFO = 1; 
        public static final int EVENT_RESERVOIR_DETAIL = 2;
        public void onEvent(Message msg);
    }
    
    private List<WeakReference<OnEventListener>> mEventListeners;
    
    private static DataManager mInstance;
    
    private ExecutorService mExecutor = Executors.newCachedThreadPool();
    
    public static DataManager getInstance(){
        if(mInstance == null){
            synchronized (DataManager.class) {
                if(mInstance == null){
                    mInstance = new DataManager();
                }
            }
        }
        
        return mInstance;
    }
    
    private DataManager(){
        mEventListeners = Collections.synchronizedList(new ArrayList<WeakReference<OnEventListener>>());
    }
    
    /***
     * 获取资讯接口
     * */
    public void requestReservoirInfo(){
        String url = DataUrl.ReserovirInfoUrl;
        int type = OnEventListener.EVENT_RESERVOIR_INFO;
        new LoadJsonTask().executeOnExecutor(mExecutor, url,type);
    }
    
    public void addEventListener(OnEventListener listener){
        for(WeakReference<OnEventListener> temp:mEventListeners){
            if(temp.get() == listener){
                return;
            }           
        }
        mEventListeners.add(new WeakReference<OnEventListener>(listener));
    };
    
    public void removeEventListener(OnEventListener listener){
        for(WeakReference<OnEventListener> temp:mEventListeners){
            if(temp.get() == listener){
                mEventListeners.remove(temp);
            }           
        }
    }
    
    public RequestResult parseReservoirInfo(String json){
        RequestResult result;
        try{
            JSONObject jsonObject = JSON.parseObject(json);
            result = JSON.toJavaObject(jsonObject, RequestResult.class);
        }catch(Exception e){
            e.printStackTrace();
            return null;
        }
        return result;
    }
    
    public ReservoirInfo parseReservoirInfoTest(String json){
        ReservoirInfo info;
        try{
            JSONObject object = JSON.parseObject(json);
            info = object.toJavaObject(object, ReservoirInfo.class);
        }catch(Exception e){
            return null;
        }
        return info;
    }
    
    public void onReservoirInfo(RequestResult result){
        notifyListener(OnEventListener.EVENT_RESERVOIR_INFO,result);
    }
    
    private void notifyListener(final int what,final Object object){

        ReservoirApplication.getInstance().runOnUiThread(new Runnable(){
            public void run() {
                
                Message msg = new Message();
                msg.what = what;
                msg.obj = object;
                
                List<WeakReference<OnEventListener>> invalidListeners = new ArrayList<WeakReference<OnEventListener>>();
                List<WeakReference<OnEventListener>> cloneListeners = new ArrayList<WeakReference<OnEventListener>>(mEventListeners);
                
                for(WeakReference<OnEventListener> listener : cloneListeners){
                    OnEventListener temp = listener.get();
                    if(temp != null){
                        temp.onEvent(msg);
                    }else{
                        invalidListeners.add(listener);
                    }
                }
                
                if(invalidListeners.size() > 0){
                    for(WeakReference<OnEventListener> listenerRef:invalidListeners){
                        mEventListeners.remove(listenerRef);
                    }
                }
                
            }
        });
    }
    
}
