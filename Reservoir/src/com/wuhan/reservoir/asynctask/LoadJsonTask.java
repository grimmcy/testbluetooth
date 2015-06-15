package com.wuhan.reservoir.asynctask;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import android.os.AsyncTask;
import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONException;
import com.alibaba.fastjson.JSONObject;
import com.wuhan.reservoir.bean.RequestResult;
import com.wuhan.reservoir.datamanager.DataManager;
import com.wuhan.reservoir.datamanager.DataManager.OnEventListener;
import com.wuhan.reservoir.util.HttpUtil;

public class LoadJsonTask extends AsyncTask<Object, Object, Boolean> {

    private final int TRY_TIMES = 10;// 重试次数
    private final int TIME_OUT = 500;// 超时时间

    @Override
    protected Boolean doInBackground(Object... params) {
        String url = (String) params[0];
        int type = (Integer) params[1];
        String json = null;
        json = getDataByUrl(url,"");
        Log.d("xxx","json is : "+json);
        boolean success = (!TextUtils.isEmpty(json));
        switch (type) {
        case OnEventListener.EVENT_RESERVOIR_INFO:
            if(success){
                RequestResult result = DataManager.getInstance().parseReservoirInfo(json);
                DataManager.getInstance().onReservoirInfo(result);
            }else{
                //如果失败的话，返回的是一个空的javabean
                DataManager.getInstance().onReservoirInfo(new RequestResult());
            }
            break;
        }
        return null;
    }

    private String getDataByUrl(String url,String param) {
        String result = null;
        for (int i = 0; i < TRY_TIMES; i++) {
            try {
                result = HttpUtil.requestJson(url,param,TIME_OUT);
                if (!TextUtils.isEmpty(result)) {
                    JSONObject object = JSON.parseObject(result);
                    if (object.getInteger("retCode") == 200) {
                        return result;
                    }
                    break;
                }
            } catch (ClientProtocolException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                result = "";
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
                result = "";
            } catch(JSONException e){
                Log.d("xxx","JSONException is : "+e.toString());
                result = "";
            }
        }

        return result;
    }
}
