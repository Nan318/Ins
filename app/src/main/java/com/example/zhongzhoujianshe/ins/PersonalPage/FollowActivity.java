package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;

import java.util.ArrayList;

public class FollowActivity extends Activity {


    RecyclerView recyclerView;
    FollowListAdapter adapter;

    //initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //layout manage
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<UserProfileModel> followList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            UserProfileModel follow = new UserProfileModel();
            follow.setUsername("elias" + i);
            follow.setEmail("elias" + i + "@gmail.com");
            followList.add(follow);
        }

        adapter = new FollowListAdapter(this, followList);
        recyclerView.setAdapter(adapter);
    }

}
