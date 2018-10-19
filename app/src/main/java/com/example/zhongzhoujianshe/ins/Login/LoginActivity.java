package com.example.zhongzhoujianshe.ins.Login;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhongzhoujianshe.ins.Discover.DiscoverActivity;
import com.example.zhongzhoujianshe.ins.MyWidget.MyEditText;
import com.example.zhongzhoujianshe.ins.MyWidget.MyRoundCornerButton;
import com.example.zhongzhoujianshe.ins.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private Context mContext;
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    //UI
    private MyEditText et_email;
    private MyEditText et_password;
    private ProgressBar mProgressBar;
    private TextView mPleaseWait;
    private MyRoundCornerButton btn_login;

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

        mContext = LoginActivity.this;

        /* * * * * initialize view * * * * * */

        initialView();

        /* * * * * firebase * * * * * */

        //Get Firebase auth instance
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {// User is signed in
                    Log.e("TAG", "onAuthStateChanged:signed_in:" + user.getUid());

                    Intent intent = new Intent(LoginActivity.this, DiscoverActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    finish();

                }else {// User is signed out
                    Log.e("TAG", "onAuthStateChanged:signed_out");
                }
            }

        };

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginWithPassword();
            }
        });


    }
    public void initialView(){
        //hide progress bar and msg
        mProgressBar = (ProgressBar) findViewById(R.id.progress_bar);
        mPleaseWait = (TextView) findViewById(R.id.wait);
        mPleaseWait.setVisibility(View.GONE);
        mProgressBar.setVisibility(View.GONE);

        //input part
        et_email = (MyEditText) findViewById(R.id.et_email);
        et_password = (MyEditText) findViewById(R.id.et_password);

        //button login
        btn_login = (MyRoundCornerButton) findViewById(R.id.btn_login);
        btn_login.setFillet(true);
        btn_login.setRadius(15);  //shape: round rectangle
        btn_login.setBackColor(getResources().getColor(R.color.darkerBlue));
        btn_login.setBackColorSelected(getResources().getColor(R.color.orange));
        btn_login.setTextColori(getResources().getColor(R.color.orange));
        btn_login.setTextColorSelected(getResources().getColor(R.color.darkerBlue));


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

    private void loginWithPassword() {
        String email = et_email.getEtText();
        String password = et_password.getEtText();

        if(email.equals("") || password.equals("")){
            Toast.makeText(mContext, "Please fill out all the fields", Toast.LENGTH_SHORT).show();
            return;
        }
        mPleaseWait.setVisibility(View.VISIBLE);
        mProgressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                FirebaseUser user = mAuth.getCurrentUser();

                if (!task.isSuccessful()) { //fail to login
                    mProgressBar.setVisibility(View.GONE);
                    mPleaseWait.setVisibility(View.GONE);
                    Toast.makeText(LoginActivity.this, "Authentication failed.",
                            Toast.LENGTH_SHORT).show();
                }else {
                    try{
                        if(user.isEmailVerified()){
                            Log.e("LOGIN", "email is verified.");
                            Intent intent = new Intent(LoginActivity.this, DiscoverActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(mContext, "Please verify your email", Toast.LENGTH_SHORT).show();
                            mProgressBar.setVisibility(View.GONE);
                            mPleaseWait.setVisibility(View.GONE);
                            mAuth.signOut();
                        }

                    }catch (NullPointerException e){
                        Log.e("LOGIN", "onComplete: NullPointerException: " + e.getMessage() );
                    }

                }
            }
        });

    }

}
