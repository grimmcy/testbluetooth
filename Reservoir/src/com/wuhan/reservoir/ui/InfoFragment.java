package com.wuhan.reservoir.ui;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.wuhan.reservoir.R;
import com.wuhan.reservoir.bean.RequestResult;
import com.wuhan.reservoir.datamanager.DataManager;
import com.wuhan.reservoir.datamanager.DataManager.OnEventListener;
import com.wuhan.reservoir.widget.InfoHorizontalListAdapter;
import com.wuhan.reservoir.widget.InfoListAdapter;

public class InfoFragment extends Fragment{

    private PullToRefreshListView mListView;
    private RecyclerView mRecyclerView;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
    }
    
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        View view = inflater.inflate(R.layout.info_fragment, container,false);
        mListView = (PullToRefreshListView) view.findViewById(R.id.info_fragment_listview);
        final InfoListAdapter adapter = new InfoListAdapter(getActivity().getApplicationContext());
        adapter.setCount(20);
        mListView.getRefreshableView().setAdapter(adapter);
        
        mRecyclerView = (RecyclerView)view.findViewById(R.id.info_fragment_horizontal);
        
        LinearLayoutManager manager = new LinearLayoutManager(getActivity());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mRecyclerView.setLayoutManager(manager);
        
        List<String> data = new ArrayList<String>();
        for(int i = 0;i < 10;i++){
            data.add("测试数据"+i);
        }
        
        InfoHorizontalListAdapter infoAdapter = new InfoHorizontalListAdapter();
        infoAdapter.setData(data);
        
        mRecyclerView.setAdapter(infoAdapter);
        
        return view;
    }
    
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onActivityCreated(savedInstanceState);
        DataManager.getInstance().addEventListener(mListener);
        DataManager.getInstance().requestReservoirInfo();
    }
    
    @Override
    public void onDestroy() {
        // TODO Auto-generated method stub
        DataManager.getInstance().removeEventListener(mListener);
        super.onDestroy();
    }
    
    private OnEventListener mListener = new OnEventListener() {
        
        public void onEvent(Message msg) {
            // TODO Auto-generated method stub
            RequestResult result = (RequestResult) msg.obj;
            if(result == null || result.getRetCode() != 200){
                Toast.makeText(getActivity(), "数据获取失败", Toast.LENGTH_SHORT).show();
            }
        }
    };

}
