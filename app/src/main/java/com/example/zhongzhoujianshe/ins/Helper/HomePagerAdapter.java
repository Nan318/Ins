package com.example.zhongzhoujianshe.ins.Helper;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.ViewGroup;

import com.example.zhongzhoujianshe.ins.Home.DiscoveryFragment;
import com.example.zhongzhoujianshe.ins.Home.HomeFragment;

public class HomePagerAdapter extends FragmentPagerAdapter {
    private Context mContext;
    private final int PAGER_COUNT = 2;

    public HomePagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        this.mContext = context;
    }

    @Override
    public int getCount() {
        return PAGER_COUNT;
    }

    @Override
    public Object instantiateItem(ViewGroup vg, int position) {
        return super.instantiateItem(vg, position);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        System.out.println("position Destory" + position);
        super.destroyItem(container, position, object);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: // Fragment # 0 - This will show the first page
                return new HomeFragment();
            case 1: // Fragment # 1 - This will show the second page
                return new DiscoveryFragment();

            default:
                return null;
        }

    }


}
