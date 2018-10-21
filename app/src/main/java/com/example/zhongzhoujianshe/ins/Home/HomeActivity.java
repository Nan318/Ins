package com.example.zhongzhoujianshe.ins.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.Helper.HomePagerAdapter;
import com.example.zhongzhoujianshe.ins.Helper.NoScrollViewPager;
import com.example.zhongzhoujianshe.ins.R;

public class HomeActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private NoScrollViewPager viewPager;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            switch (item.getItemId()) {
                case R.id.ic_home:
                   // mTextMessage.setText(R.string.title_home);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.ic_search:
                   // mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.ic_add:
                  //  mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(0);
                    return true;
                case R.id.ic_follow:
                   // mTextMessage.setText(R.string.title_dashboard);
                    viewPager.setCurrentItem(1);
                    return true;
                case R.id.ic_profile:
                   // mTextMessage.setText(R.string.title_notifications);
                    viewPager.setCurrentItem(2);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //mTextMessage = (TextView) findViewById(R.id.message);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);


        /* * * * * body * * * * */

        HomePagerAdapter homeAdapter = new HomePagerAdapter(getSupportFragmentManager(),this);
        viewPager = (NoScrollViewPager) findViewById(R.id.viewpager);
        viewPager.setNoScroll(true);
        viewPager.setAdapter(homeAdapter);
        viewPager.setCurrentItem(0);
        //viewPager.addOnPageChangeListener(this);

    }

}


