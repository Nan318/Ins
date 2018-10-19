package com.example.zhongzhoujianshe.ins.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.example.zhongzhoujianshe.ins.Discover.DiscoverActivity;
import com.example.zhongzhoujianshe.ins.HomeActivity;
import com.example.zhongzhoujianshe.ins.R;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


/**
 * Created by User on 5/28/2017.
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";

    public static void setupBottomNavigationView(BottomNavigationViewEx bnve){
        Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView");

        bnve.enableAnimation(false);
        bnve.enableShiftingMode(0, false);
        bnve.setTextVisibility(false);


    }

    public static void enableNavigation(final Context context, final Activity callingActivity, BottomNavigationViewEx view){
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){

                    case R.id.ic_home:
                        Intent intent1 = new Intent(context, HomeActivity.class);//ACTIVITY_NUM = 0
                        context.startActivity(intent1);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_search:
                        Intent intent2  = new Intent(context, DiscoverActivity.class);//ACTIVITY_NUM = 1
                        context.startActivity(intent2);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
                        break;

                    case R.id.ic_add:
                        /*
                        Intent intent3 = new Intent(context, ShareActivity.class);//ACTIVITY_NUM = 2
                        context.startActivity(intent3);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                        Log.e("NAVIGATION", "go to Upload Photo");
                        break;

                    case R.id.ic_follow:
                        /*
                        Intent intent4 = new Intent(context, LikesActivity.class);//ACTIVITY_NUM = 3
                        context.startActivity(intent4);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                        Log.e("NAVIGATION", "go to Activity Feed");
                        break;

                    case R.id.ic_profile:
                        /*
                        Intent intent5 = new Intent(context, ProfileActivity.class);//ACTIVITY_NUM = 4
                        context.startActivity(intent5);
                        callingActivity.overridePendingTransition(R.anim.fade_in, R.anim.fade_out);*/
                        Log.e("NAVIGATION", "go to Profile");
                        break;
                }


                return false;
            }
        });
    }
}

