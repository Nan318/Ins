package com.example.zhongzhoujianshe.ins.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;

import com.example.zhongzhoujianshe.ins.Discover.UserListAdapter;
import com.example.zhongzhoujianshe.ins.Helper.User;
import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;


public class DiscoveryFragment extends Fragment {
    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRoot;
    //variable
    private String currentUserId;
    private static final int ACTIVITY_NUM = 1;
    private Context mContext;
    private ArrayList<UserProfileModel> userList;
    private UserListAdapter mAdapter;
    //UI objects
    private EditText et_search;
    private ListView listView;


    public DiscoveryFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_discovery, container, false);

        mContext = getActivity();
        mRoot = FirebaseDatabase.getInstance().getReference();

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));

        /* * * * * initialize view * * * * * */
        et_search = (EditText) view.findViewById(R.id.et_search);
        listView = (ListView) view.findViewById(R.id.listview);

        userList = new ArrayList<>();

        Log.e("DISCOVER", "initTextListener: initializing");

        //default: display recommended users
        recommendUser();


        //when input, start to search users and display them
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
                String input = et_search.getText().toString();
                if(input.equals("")){
                    recommendUser();
                }else {
                    searchUser(input);
                }

            }
        });

        return view;
    }


    private void recommendUser(){
        Log.e("DISCOVER", "display recommended users");
        userList.clear();

        //update the users list view
        //start at the input
        Query filter = mRoot.child("user_setting")
                .orderByChild("followers");
        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.d("DISCOVER", "onDataChange: found user:"
                            + singleSnapshot.getValue(UserProfileModel.class).toString());

                    userList.add(singleSnapshot.getValue(UserProfileModel.class));
                    //update the users list view
                    updateListView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }

    private void searchUser(String inputUsername){
        Log.e("DISCOVER", "start searching for user: " + inputUsername);
        userList.clear();
        //update the users list view
        if(inputUsername.length() != 0){

            //search
            //exact match
            //Query filter = mRoot.child("users").orderByChild("username").equalTo(inputUsername);

            //start at the input
            Query filter = mRoot.child("user_setting")
                    .orderByChild("username").startAt(inputUsername).endAt(inputUsername + "\uf8ff");
            filter.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                        Log.d("DISCOVER", "onDataChange: found user:" + singleSnapshot.getValue(User.class).toString());

                        userList.add(singleSnapshot.getValue(UserProfileModel.class));
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
        Log.e("DISCOVER", "start updating users list");

        //mAdapter = new UserListAdapter(DiscoverActivity.this, R.layout.layout_user_listitem, userList);

        mAdapter = new UserListAdapter(mContext, userList);
        listView.setAdapter(mAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.e("DISCOVER", "onItemClick: selected user: " + userList.get(position).toString());

                //navigate to profile activity
                /*
                //go to another activity
                Intent intent = new Intent(getActivity(),OtherActivity.class);
                startActivity(intent);*/
                /*
                //go to another fragment which belongs to the same activity
                * getActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, new YourFragment(), null)
                        .addToBackStack(null)
                        .commit();
                        */
                Log.e("NAVIGATION", "go to Profile");
            }
        });
    }



}
