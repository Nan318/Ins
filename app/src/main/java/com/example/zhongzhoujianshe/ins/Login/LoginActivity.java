package com.example.zhongzhoujianshe.ins.Login;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.MyWidget.MyEditText;
import com.example.zhongzhoujianshe.ins.MyWidget.MyRoundCornerButton;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }
    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        /* * * * * initialize view * * * * * */
        initialView();


    }
    public void initialView(){
        //input part
        MyEditText et_email = (MyEditText) findViewById(R.id.et_email);
        MyEditText et_password = (MyEditText) findViewById(R.id.et_password);

        //button login
        MyRoundCornerButton btn_login = (MyRoundCornerButton) findViewById(R.id.btn_login);
        btn_login.setFillet(true);
        btn_login.setRadius(15);  //shape: round rectangle
        btn_login.setBackColor(getResources().getColor(R.color.darkerBlue));
        btn_login.setBackColorSelected(getResources().getColor(R.color.orange));
        btn_login.setTextColori(getResources().getColor(R.color.orange));
        btn_login.setTextColorSelected(getResources().getColor(R.color.darkerBlue));
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        //sign up?
        TextView register = (TextView) findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "go to register activity");
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });


    }
}
