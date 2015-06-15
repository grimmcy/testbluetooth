package com.wuhan.reservoir;

import java.util.ArrayList;
import java.util.List;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.wuhan.reservoir.asynctask.TestThread;
import com.wuhan.reservoir.ui.AboutFragment;
import com.wuhan.reservoir.ui.BaseActivity;
import com.wuhan.reservoir.ui.ConditionFragment;
import com.wuhan.reservoir.ui.DetailFragment;
import com.wuhan.reservoir.ui.InfoFragment;
import com.wuhan.reservoir.ui.SearchFragment;

public class MainActivity extends BaseActivity {

    private ViewPager mViewPager;
    private List<Fragment> mFragmentList;
    private Button mBtnInfo;
    private Button mBtnDetail;
    private Button mBtnSearch;
    private Button mBtnAbout;
    private Button mBtnCondition;
    private Button[] mBtns;
    private int mPreIndex = 0;
    
    //viewpager 索引
    private interface INDEX{
        int INFO = 0;
        int DETAIL = 1;
        int CONDITION = 2;
        int SEARCH = 3;
        int MY = 4;
    }
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initWidget();
		new TestThread().start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
	
	private void initWidget(){
	    mViewPager = (ViewPager)findViewById(R.id.activity_main_pager);
	    
	    mBtnInfo = (Button)findViewById(R.id.activity_main_btn_info);
	    mBtnDetail = (Button)findViewById(R.id.activity_main_btn_detail);
	    mBtnSearch = (Button)findViewById(R.id.activity_main_btn_search);
	    mBtnAbout = (Button)findViewById(R.id.activity_main_btn_my);
	    mBtnCondition = (Button)findViewById(R.id.activity_main_btn_condition);
	    mBtnInfo.setOnClickListener(mOnClickListener);
	    mBtnDetail.setOnClickListener(mOnClickListener);
	    mBtnSearch.setOnClickListener(mOnClickListener);
	    mBtnAbout.setOnClickListener(mOnClickListener);
	    mBtnCondition.setOnClickListener(mOnClickListener);
	    
	    mBtns = new Button[]{mBtnInfo,mBtnDetail,mBtnCondition,mBtnSearch,mBtnAbout};
	    
	    mFragmentList = new ArrayList<Fragment>();
	    mFragmentList.add(new InfoFragment());
	    mFragmentList.add(new DetailFragment());
	    mFragmentList.add(new ConditionFragment());
	    mFragmentList.add(new SearchFragment());
	    mFragmentList.add(new AboutFragment());
	    
	    FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager(), mFragmentList);
	    mViewPager.setAdapter(adapter);	    
	    mViewPager.setCurrentItem(0);
	    
	    //设置初始的按钮状态
	    mBtns[0].setSelected(true);
	    mViewPager.setOnPageChangeListener(new OnPageChangeListener() {
            
            public void onPageSelected(int position) {
                // TODO Auto-generated method stub
                mBtns[position].setSelected(true);
                mBtns[mPreIndex].setSelected(false);
                mPreIndex = position;
                Log.d("xxx","position is : "+position);
            }
            
            public void onPageScrolled(int arg0, float arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            public void onPageScrollStateChanged(int arg0) {
                // TODO Auto-generated method stub
                
            }
        });
	    
	    
	    
	}
	
	
	private class FragmentAdapter extends FragmentPagerAdapter{
	    
        private final List<Fragment> mFragments;
	    public FragmentAdapter(FragmentManager fm,List<Fragment> list) {
            super(fm);
            mFragments = list;
            // TODO Auto-generated constructor stub
        }

        @Override
        public Fragment getItem(int position) {
            // TODO Auto-generated method stub
            return mFragments.get(position);
        }

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return mFragments != null?mFragments.size():0;
        }
    
	}
	
	private OnClickListener mOnClickListener = new OnClickListener() {
        
        public void onClick(View v) {
            // TODO Auto-generated method stub
            switch(v.getId()){
            case R.id.activity_main_btn_info:
                mViewPager.setCurrentItem(INDEX.INFO);
                break;
            case R.id.activity_main_btn_detail:   
                mViewPager.setCurrentItem(INDEX.DETAIL);
                break;
            case R.id.activity_main_btn_search:
                mViewPager.setCurrentItem(INDEX.SEARCH);
                break;
            case R.id.activity_main_btn_my:
                mViewPager.setCurrentItem(INDEX.MY);
                break;    
            case R.id.activity_main_btn_condition:
                mViewPager.setCurrentItem(INDEX.CONDITION);
                break;
            }
        }
    };

}
