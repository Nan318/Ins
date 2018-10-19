package com.example.zhongzhoujianshe.ins.Discover;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhongzhoujianshe.ins.Helper.BottomNavigationViewHelper;
import com.example.zhongzhoujianshe.ins.Helper.User;

import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class DiscoverActivity extends AppCompatActivity {
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseDatabase mDb;
    private DatabaseReference mRef;
    //variable
    private String currentUserId;
    private static final int ACTIVITY_NUM = 1;
    private Context mContext;
    private List<User> userList;
    private UserListAdapter mAdapter;
    //UI objects
    private EditText et_search;
    private ListView listView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discover);

        mContext = DiscoverActivity.this;

        /* * * * * initialize view * * * * * */

        et_search = (EditText) findViewById(R.id.search);
        listView = (ListView) findViewById(R.id.listView);
        //Log.e("DISCOVER", "onCreate: started.");

        //hide SoftKeyboard
        if(getCurrentFocus() != null){
            InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        }

        //set up the bottom navigation view
        Log.e("SET", "bottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx = (BottomNavigationViewEx) findViewById(R.id.bottom_navigation_view);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, this,bottomNavigationViewEx);
        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);


        //initTextListener();

        Log.e("DISCOVER", "initTextListener: initializing");

        userList = new ArrayList<>();

        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {
                //String text = et_search.getText().toString().toLowerCase(Locale.getDefault());
                String text = et_search.getText().toString();
                searchForMatch(text);
            }
        });


    }

    private void searchForMatch(String keyword){
        Log.e("DISCOVER", "searchForMatch: searching for a match: " + keyword);
        userList.clear();
        //update the users list view
        if(keyword.length() != 0){
            DatabaseReference mRef = FirebaseDatabase.getInstance().getReference();
            Query query = mRef.child("users")
                    .orderByChild("username").equalTo(keyword);
            query.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                        Log.d("DISCOVER", "onDataChange: found user:" + singleSnapshot.getValue(User.class).toString());

                        userList.add(singleSnapshot.getValue(User.class));
                        //update the users list view
                        updateListView();
                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {
                }
            });
        }
    }

    //update the list view
    //i.e. show the user list
    private void updateListView(){
        Log.e("DISCOVER", "updateListView: updating users list");

        //mAdapter = new UserListAdapter(DiscoverActivity.this, R.layout.layout_user_listitem, userList);

        mAdapter = new UserListAdapter(mContext, userList);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DISCOVER", "onItemClick: selected user: " + userList.get(position).toString());

                //navigate to profile activity
                /*
                Intent intent =  new Intent(DiscoverActivity.this, ProfileActivity.class);
                intent.putExtra(getString(R.string.calling_activity), getString(R.string.search_activity));
                intent.putExtra(getString(R.string.intent_user), userList.get(position));
                startActivity(intent);*/
                Log.e("NAVIGATION", "go to Profile");
            }
        });
    }


}
