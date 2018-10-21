package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.app.Activity;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.hardware.Camera;
import android.os.Bundle;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.zhongzhoujianshe.ins.Home.HomeActivity;
import com.example.zhongzhoujianshe.ins.R;

public class PersonalPageActivity extends Activity {


    LinearLayout follow;

    //initialize
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);
        follow = (LinearLayout) findViewById(R.id.layout_follow);
        follow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
              //  Intent intent = new Intent(PersonalPageActivity.this, FollowActivity.class);
               // PersonalPageActivity.this.startActivity(intent);
            }
        });
    }
}
