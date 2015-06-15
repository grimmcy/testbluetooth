package com.wuhan.reservoir.asynctask;

import java.util.ArrayList;
import java.util.List;

import android.util.Log;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.wuhan.reservoir.bean.ReservoirInfo;

public class TestThread extends Thread{
    
    private List<JSONObject> mItemList;
    @Override
    public void run() {
        mItemList = new ArrayList<JSONObject>();
        // TODO Auto-generated method stub
        for(int i = 0;i < 10;i++){
            JSONObject item = initItem(i);
            mItemList.add(item);
        }
        JSONObject object = new JSONObject();
        object.put("PKGTYPE", 11);
        object.put("GROUPCOUNT",2);
        object.put("ACCOUNT",Integer.toString(1321));
        object.put("DATA", mItemList);
        
        String json = object.toJSONString();
        Log.d("xxxx","json is : "+json);
                
        ReservoirInfo info = JSON.parseObject(json, ReservoirInfo.class);
        Log.d("xxxx","account is : "+info.getAccount());
        Log.d("xxxx","pkgtype is : "+info.getPKGTYPE());
        Log.d("xxxx","groupcount is : "+info.getGrounpcount());
        Log.d("xxxx","data is : "+info.getData());
        
        for(int i= 0;i < info.getData().size();i++){
            Log.d("xxxx","stnm is : "+info.getData().get(i).getSTNM());
            Log.d("xxxx","url is : "+info.getData().get(i).getPICURL());
        }

        
    }
    
    
    /**
     * 测试用创建一个资讯数据用于测试
     * @param i
     * {"STCD":"","RZ":"","STNM":"","OWPTN":"","DYP":"","TM":"","AMFLAG":"","PICURL":""},
     * @return
     */
    private JSONObject initItem(int i){
        JSONObject object = new JSONObject();
        object.put("STCD",Integer.toString(i));
        object.put("RZ", i);
        object.put("STNM", "reservoir"+i);
        object.put("OWPTN",i);
        object.put("DYP",i);
        object.put("TM",i);
        object.put("AMFLAG",0);
        object.put("PICURL","www.hao123.com/"+i);
        return object;
    }
}
