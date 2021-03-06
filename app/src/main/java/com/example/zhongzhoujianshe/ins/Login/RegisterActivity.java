package com.example.zhongzhoujianshe.ins.Login;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.zhongzhoujianshe.ins.Helper.User;
import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.MyWidget.MyEditText;
import com.example.zhongzhoujianshe.ins.MyWidget.MyRoundCornerButton;
import com.example.zhongzhoujianshe.ins.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class RegisterActivity extends AppCompatActivity {
    private Context mContext;
    //UI Objects
    private MyEditText et_email;
    private MyEditText et_password;
    private MyEditText et_username;
    private MyRoundCornerButton btn_register;
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    //variables
    private String email;
    private String psw;
    private String usrname;
    private String append = "";  //to created unique username
    private String userID;

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
        setContentView(R.layout.activity_register);
        mContext = RegisterActivity.this;

        /* * * * * initialize view * * * * * */
        initialView();


        /* * * * * firebase * * * * * */

        //Get Firebase auth instance
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                mDb = FirebaseDatabase.getInstance();
                mRef = mDb.getReference();
                if (user != null) {// User is signed in
                    userID = user.getUid();
                    Log.e("TAG", "onAuthStateChanged:signed_in:" + userID);

                }else {// User is signed out
                    Log.e("TAG", "onAuthStateChanged:signed_out");
                }
            }

        };



        /* * * * * click * * * * * */
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = et_email.getEtText();
                usrname = et_username.getEtText();
                psw = et_password.getEtText();

                if(email.equals("") || usrname.equals("") || psw.equals("")){
                    Toast.makeText(mContext,
                            "Please fill out all the fields", Toast.LENGTH_SHORT).show();
                }else{
                    registerUsr(email, usrname, psw);
                }
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
        mContext = RegisterActivity.this;

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

    private void registerUsr(String email, final String usrname, String psw) {
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, psw)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.e("Create account", " success");
                            //FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            Toast.makeText(mContext, "Successfully register, login now ~",
                                    Toast.LENGTH_SHORT).show();

                            checkUsrnameExists(usrname);
                            Log.e("CheckExist", "end check");
                            /*
                            //ignore email verification
                            if(user != null){
                                user.sendEmailVerification()
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if(task.isSuccessful()){
                                                }else{
                                                    Toast.makeText(mContext,
                                                            "Send verification email fail.",
                                                            Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        });
                            }*/
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(mContext, "Create accound failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                        // ...
                    }
                });
    }

    private void checkUsrnameExists(final String username) {
        Log.e("CheckExist", "start check username exist?");
        mRef = FirebaseDatabase.getInstance().getReference();
        //search among the registered usernames
        Query query = mRef.child("users").orderByChild("username").equalTo(username);
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){
                    if (singleSnapshot.exists()){
                        append = "_" + mRef.push().getKey().substring(3,10);
                        Log.e("ExistUname", "add random string: " + append);
                    }
                }

                String newUsername = "";
                newUsername = username + append;


                //add new user to the database
                addNewUser(email, newUsername, "", "", "");

                Log.e("Add", "add end" );
                /*
                Toast.makeText(mContext, "Successful add information",
                        Toast.LENGTH_SHORT).show();
                        */
                //leave register activity, back to login page
                finish();
                mAuth.signOut();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    public void addNewUser(String email, String username, String description, String website, String profile_photo){
        Log.e("Add", "start add the user's information" );


        UserProfileModel profileModel = new UserProfileModel(description,
                0, 0, 0, profile_photo, username,
                website, userID, email
        );

        mRef.child("user_setting").child(userID).setValue(profileModel);

    }

}
