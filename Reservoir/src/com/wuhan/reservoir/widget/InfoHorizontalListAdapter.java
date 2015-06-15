package com.wuhan.reservoir.widget;

import java.util.List;

import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.wuhan.reservoir.R;
import com.wuhan.reservoir.ReservoirApplication;
import com.wuhan.reservoir.datamanager.DataUrl;

public class InfoHorizontalListAdapter extends RecyclerView.Adapter<InfoHorizontalListViewHolder> {

    private List<String> mData;
    private LayoutInflater mInflater = LayoutInflater.from(ReservoirApplication.getInstance());

    public void setData(List<String> data) {
        mData = data;
    }

    @Override
    public int getItemCount() {
        // TODO Auto-generated method stub
        return mData.size();
    }

    @Override
    public void onBindViewHolder(InfoHorizontalListViewHolder viewholder, int position) {
        // TODO Auto-generated method stub
        viewholder.title.setText(mData.get(position));
        
        //test url 这是测试的网址
        String url = DataUrl.sTestImageUrl[position%DataUrl.sTestImageUrl.length];
        ReservoirApplication.getInstance().getImageLoader().displayImage(url, viewholder.image,mOption);
    }

    @Override
    public InfoHorizontalListViewHolder onCreateViewHolder(ViewGroup parent, int position) {
        // TODO Auto-generated method stub
        View view = mInflater.inflate(R.layout.info_cell_h, parent, false);
        InfoHorizontalListViewHolder viewholder = new InfoHorizontalListViewHolder(view);
        viewholder.title = (TextView) view.findViewById(R.id.info_cell_h_title);
        viewholder.image = (ImageView) view.findViewById(R.id.info_cell_h_image);
        return viewholder;
    }

    private DisplayImageOptions mOption = new DisplayImageOptions.Builder()
            .showStubImage(R.drawable.ic_launcher).showImageForEmptyUri(R.drawable.ic_launcher)
            .cacheInMemory(true).imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
            .bitmapConfig(Bitmap.Config.ARGB_8888).build();

}
