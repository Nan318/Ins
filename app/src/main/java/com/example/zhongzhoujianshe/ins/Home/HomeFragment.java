package com.example.zhongzhoujianshe.ins.Home;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.zhongzhoujianshe.ins.Helper.Post;
import com.example.zhongzhoujianshe.ins.Helper.PostLocationComparator;
import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

import java.util.ArrayList;
import java.util.Collections;


public class HomeFragment extends Fragment {

    // Firebase instance variables
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    private DatabaseReference mRoot;
    //variable
    private String currentUserId;
    private static final int ACTIVITY_NUM = 1;
    private Context mContext;
    private ArrayList<String> followedUserIdList = new ArrayList<String>();
    private ArrayList<Post> postArrayList;
    private HomeListAdapter mAdapter;
    private int i;
    //UI objects
    private Button byTime;
    private Button byLoca;
    private ListView listView;

    public HomeFragment() {
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
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        byTime = view.findViewById(R.id.sortbytime);
        byLoca = view.findViewById(R.id.sortbylocation);
        listView = (ListView) view.findViewById(R.id.listview);


        mContext = getActivity();
        mRoot = FirebaseDatabase.getInstance().getReference();

        FirebaseUser user_db = FirebaseAuth.getInstance().getCurrentUser();
        if (user_db != null) {
            currentUserId = user_db.getUid();

        }

        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));

        postArrayList = new ArrayList<Post>();

        //first, find users who are supposed to be displayed
       // findUserToDisplay();

        //second, query their posts
        getPosts();
        updateListView();
        //sort
        //default by time
        //sortByTime();
        //updateListView();

        byTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               // findUserToDisplay();
               // getPosts();
                sortByTime();
                updateListView();
            }
        });
        byLoca.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //   findUserToDisplay();
                //getPosts();
                sortByLocation();
                updateListView();
            }
        });


        return view;
    }

    private void getPosts() {
        Log.e("HOME", "get posts");
        //have followed someone
        postArrayList.clear();
        Query filter = mRoot.child("post").orderByChild("timeStamp");

        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    Log.e("HOME", "onDataChange: found post:"
                            + singleSnapshot.getValue(Post.class).toString());

                    postArrayList.add(singleSnapshot.getValue(Post.class));
                    //update the users list view

                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("HOME", "get post fail");
            }
        });
       // updateListView();


    }

    private void sortByLocation() {
        if(postArrayList.size() >1){
            Log.e("HOME", "sort by location");
            Collections.sort(postArrayList, new PostLocationComparator());

        }

    }
    private void sortByTime() {


        /*

        if(postArrayList.size() != 0){
            Log.e("HOME", "sort by time");
            Collections.sort(postArrayList, new PostTimeComparator());
        }
        */

    }

    public void findUserToDisplay() {

        //followedUserIdList = new ArrayList<>();
        //followedUserIdList.clear();
        Log.e("HOME", "find who have the current user followed");


        //update the users list view  user_setting
        //start at the input
        //Query filter = mRoot.child("follow").orderByChild("following").equalTo(currentUserId);
        Query filter = mRoot.child("user_setting");

        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){
                    String key = singleSnapshot.getKey(); // this key is the eid of the existing data
                    followedUserIdList.add(key);
                    //update the users list view
                    //updateListView();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        });
    }
    private void updateListView(){

        Log.e("HOME", "start updating users list");

        Log.e("HOME", "the lenght of list: " + postArrayList.size());
        //mAdapter = new UserListAdapter(DiscoverActivity.this, R.layout.layout_user_listitem, userList);

        if(postArrayList.size() == 0){
            Log.e("HOME", "listview: no item to be updated");

        }else{
            mAdapter = new HomeListAdapter(getActivity(), postArrayList, currentUserId );
            listView.setAdapter(mAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("HOME", "onItemClick: selected user: "
                            + postArrayList.get(position).toString());

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
}
