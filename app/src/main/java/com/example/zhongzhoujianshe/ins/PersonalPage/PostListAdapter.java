package com.example.zhongzhoujianshe.ins.PersonalPage;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class PostListAdapter extends RecyclerView.Adapter<PostListAdapter.ViewHolder> {

    private ArrayList<String> postUrls = null;
    private Context context;

    public PostListAdapter(Context context, ArrayList<String> postUrls) {
        this.context = context;
        this.postUrls = postUrls;

    }

    @Override
    public PostListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final PostListAdapter.ViewHolder holder, final int position) {

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
                Log.e("item click:", "" + getItem(position));
            }
        });

    }

    @Override
    public int getItemCount() {
        if (postUrls != null)
            return postUrls.size();
        else return 0;
    }

    public String getItem(int position) {
        return postUrls.get(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        public ViewHolder(View view) {
            super(view);
            imageView = (CircleImageView) view.findViewById(R.id.imageView);
        }
    }
}
























