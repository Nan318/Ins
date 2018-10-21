package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;

import com.example.zhongzhoujianshe.ins.R;

import java.util.ArrayList;

public class PostActivity extends Activity {


    RecyclerView recyclerView;
    PostListAdapter adapter;

    //initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_recycler);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);

        //create layout manage
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(gridLayoutManager);

        ArrayList<String> postUrlList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            postUrlList.add("iamge " + i);
        }

        adapter = new PostListAdapter(this, postUrlList);
        recyclerView.setAdapter(adapter);
    }

}
