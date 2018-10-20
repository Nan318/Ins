package com.example.zhongzhoujianshe.ins.Discover;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.Helper.User;
import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by User on 9/17/2017.
 */

public class UserListAdapter extends BaseAdapter {

    private ArrayList<User> users = null;
    private Context mContext;

    public UserListAdapter(@NonNull Context context, @NonNull ArrayList<User> users) {
        this.mContext = context;
        this.users = users;
    }

    private static class ViewHolder{
        TextView txt_username;
        TextView txt_email;
        CircleImageView profilePhoto;
    }


    @Override
    public int getCount() {
        return users.size();
    }

    @Override
    public User getItem(int position) {
        return users.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup viewGroup) {


        final ViewHolder holder;

        if(view == null){
            holder = new ViewHolder();
            //view = mInflater.inflate(layoutResource, viewGroup, false);
            view = LayoutInflater.from(mContext).inflate(R.layout.layout_user_listitem,viewGroup,false);

            holder.txt_username = (TextView) view.findViewById(R.id.username);
            holder.txt_email = (TextView) view.findViewById(R.id.email);
            holder.profilePhoto = (CircleImageView) view.findViewById(R.id.profile_image);

            view.setTag(holder);
        }else{
            holder = (ViewHolder) view.getTag();
        }

        holder.txt_username.setText(getItem(position).getUsername());
        holder.txt_email.setText(getItem(position).getEmail());
        String itemUserId = getItem(position).getUserId();

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
        Query filter = mRoot.child("user_setting").orderByChild("userId").equalTo(itemUserId);
        //display profile photo for user
        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    String profilePhoto = singleSnapshot
                            .getValue(UserProfileModel.class).getProfilePhoto();

                    if (!profilePhoto.equals("")){
                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage(profilePhoto, holder.profilePhoto);
                    }else {
                        Log.e("DISPLAY PHOTO", "no photo was added");
                    }


                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        return view;
    }
}

























