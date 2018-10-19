package com.example.zhongzhoujianshe.ins.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.MyWidget.MyEditText;
import com.example.zhongzhoujianshe.ins.MyWidget.MyRoundCornerButton;
import com.example.zhongzhoujianshe.ins.R;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    //UI Objects
    private MyEditText et_email;
    private MyEditText et_password;
    private MyEditText et_username;
    private MyRoundCornerButton btn_register;
    private TextView loading_txt;
    private Button btnRegister;
    private ProgressBar mProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;

        /* * * * * initialize view * * * * * */
        initialView();

        /* * * * * firebase * * * * * */


        /* * * * * click * * * * * */
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    private void initialView() {
        Log.e("TAG", "initWidgets: Initializing Widgets.");
        /* * * * * toolbar * * * * * */

        //used for setting icon-font
        Typeface font = Typeface.createFromAsset(getAssets(), "iconfont.ttf");
        Toolbar toolbar = (Toolbar) findViewById(R.id.register_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //set icon-font
        TextView txt_menu_back = (TextView) toolbar.findViewById(R.id.toolbar_back);
        txt_menu_back.setTypeface(font);
        txt_menu_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RegisterActivity.this.finish();
            }
        });

        /* * * * * body * * * * * */


        et_email = (MyEditText) findViewById(R.id.et_email);
        et_password = (MyEditText) findViewById(R.id.et_password);
        et_username = (MyEditText) findViewById(R.id.et_username);
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        loading_txt = (TextView) findViewById(R.id.loading);
        mContext = RegisterActivity.this;

        mProgressBar.setVisibility(View.GONE);
        loading_txt.setVisibility(View.GONE);

        //button login
        btn_register = (MyRoundCornerButton) findViewById(R.id.btn_register);
        btn_register.setFillet(true);
        btn_register.setRadius(15);  //shape: round rectangle
        btn_register.setBackColor(getResources().getColor(R.color.darkerBlue));
        btn_register.setBackColorSelected(getResources().getColor(R.color.orange));
        btn_register.setTextColori(getResources().getColor(R.color.orange));
        btn_register.setTextColorSelected(getResources().getColor(R.color.darkerBlue));
        btn_register.setText(getResources().getString(R.string.register));



    }
}
