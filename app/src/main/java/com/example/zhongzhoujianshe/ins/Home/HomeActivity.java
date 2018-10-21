package com.example.zhongzhoujianshe.ins.Home;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.Helper.HomePagerAdapter;
import com.example.zhongzhoujianshe.ins.Helper.NoScrollViewPager;
import com.example.zhongzhoujianshe.ins.Login.LoginActivity;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HomeActivity extends AppCompatActivity {

    //private TextView mTextMessage;
    private NoScrollViewPager viewPager;
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

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
                    viewPager.setCurrentItem(0);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);


        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {// User is signed in
                    Log.e("onAuthStateChanged", ":signed_in:" + user.getUid());



                }else {// User is signed out
                    Log.e("onAuthStateChanged", ":signed_out");
                    Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();
                }
            }

        };

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


