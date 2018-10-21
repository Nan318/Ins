package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.zhongzhoujianshe.ins.Helper.Post;
import com.example.zhongzhoujianshe.ins.R;

public class PersonalPageFragment extends Fragment {


    LinearLayout follow;
    LinearLayout post;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.activity_personal, container, false);
        follow = (LinearLayout) view.findViewById(R.id.layout_follow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalPageFragment.this.getActivity(), FollowActivity.class);
                PersonalPageFragment.this.getActivity().startActivity(intent);
            }
        });
        post = (LinearLayout) view.findViewById(R.id.layout_post);
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(PersonalPageFragment.this.getActivity(), PostActivity.class);
                PersonalPageFragment.this.getActivity().startActivity(intent);
            }
        });
        return view;
    }
}
