package com.wuhan.reservoir.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.wuhan.reservoir.R;
import com.wuhan.reservoir.ReservoirApplication;
import com.wuhan.reservoir.datamanager.DataUrl;

public class InfoListAdapter extends BaseAdapter{
    
    private Context mContext;
    private int mCount = 0;
    public InfoListAdapter(Context context){
        mContext = context;
    }
    
    public void setData(){
        
    }
    
    public void setCount(int count){
        mCount = count;
    }

    public int getCount() {
        // TODO Auto-generated method stub
        return mCount;
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if(convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.info_cell,parent,false);
            holder = new ViewHolder();
            holder.image = (ImageView)convertView.findViewById(R.id.info_cell_image);
            holder.title = (TextView)convertView.findViewById(R.id.info_cell_title);
            holder.content = (TextView)convertView.findViewById(R.id.info_cell_content);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText("测试"+position);
        
        //测试数据---
        String url = DataUrl.sTestImageUrl[position%DataUrl.sTestImageUrl.length];
        ReservoirApplication.getInstance().getImageLoader().displayImage(url,holder.image,mOption);
        return convertView;
    }
    
    public static class ViewHolder{
        ImageView image;
        TextView title;
        TextView content;
    }

    private DisplayImageOptions mOption = new DisplayImageOptions.Builder()
                                          .showStubImage(R.drawable.ic_launcher)
                                          .showImageForEmptyUri(R.drawable.ic_launcher)
                                          .cacheInMemory(true)
                                          .imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
                                          .bitmapConfig(Bitmap.Config.ARGB_8888)
                                          .build();
}
