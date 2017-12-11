package com.example.zhang.splash.adapter;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by YangXueYi
 * Time： 2017/12/11.
 */

public class GuideViewPagerAdapter extends PagerAdapter {

    private List<View> mList;
    public  GuideViewPagerAdapter(List<View> list){
        super();
        this.mList = list;
    }
    @Override
    public int getCount() {
        if (mList != null) {
            return mList.size();
        }
        return 0;
    }

    //删除条目
    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mList.get(position));
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    //实例化条目
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        container.addView(mList.get(position), 0);
        return mList.get(position);
    }
}
