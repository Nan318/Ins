package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.ViewHolder> {

    private ArrayList<UserProfileModel> follows = null;
    private Context context;

    public FollowListAdapter(Context context, ArrayList<UserProfileModel> follows) {
        this.context = context;
        this.follows = follows;

    }

    @Override
    public FollowListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_follow, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final FollowListAdapter.ViewHolder holder, final int position) {

        holder.textViewName.setText(getItem(position).getUsername());
        holder.textViewEmail.setText(getItem(position).getEmail());
        holder.buttonFollow.setBackgroundColor(context.getResources().getColor(R.color.blue));
//        String itemUserId = getItem(position).getUserId();
//
//        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
//        Query filter = mRoot.child("user_setting").orderByChild("userId").equalTo(itemUserId);
//        //display profile photo for user
//        filter.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                for (DataSnapshot singleSnapshot : dataSnapshot.getChildren()) {
//
//                    String profilePhoto = singleSnapshot
//                            .getValue(UserProfileModel.class).getProfilePhoto();
//
//                    if (!profilePhoto.equals("")) {
//                        ImageLoader imageLoader = ImageLoader.getInstance();
//                        imageLoader.displayImage(profilePhoto, holder.imageView);
//                    } else {
//                        Log.e("DISPLAY PHOTO", "no photo was added");
//                    }
//
//                }
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("item click:", "" + position);
            }
        });

    }

    @Override
    public int getItemCount() {
        if (follows != null)
            return follows.size();
        else return 0;
    }

    public UserProfileModel getItem(int position) {
        return follows.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;
        TextView textViewEmail;
        CircleImageView imageView;
        Button buttonFollow;

        public ViewHolder(View view) {
            super(view);
            textViewName = (TextView) view.findViewById(R.id.textView_name);
            textViewEmail = (TextView) view.findViewById(R.id.textView_email);
            imageView = (CircleImageView) view.findViewById(R.id.imageView);
            buttonFollow = (Button) view.findViewById(R.id.button_follow);
        }
    }
}
























