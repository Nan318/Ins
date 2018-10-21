package com.example.zhongzhoujianshe.ins.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.zhongzhoujianshe.ins.Helper.GlideApp;
import com.example.zhongzhoujianshe.ins.Helper.Like;
import com.example.zhongzhoujianshe.ins.Helper.Post;
import com.example.zhongzhoujianshe.ins.Helper.UserProfileModel;
import com.example.zhongzhoujianshe.ins.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.nostra13.universalimageloader.core.ImageLoader;
import de.hdodenhof.circleimageview.CircleImageView;

import java.util.ArrayList;
import java.util.UUID;

public class HomeListAdapter extends BaseAdapter {


   // private ArrayList<UserProfileModel> users;
    private ArrayList<Post> posts;
    private Context mContext;
    private String postUserId;
    private boolean like = false;
    String postPhoto = "";
    //private Post currentPost;

    //firebase
    private String currentUserId;

    public HomeListAdapter(@NonNull Context context, @NonNull
            ArrayList<Post> posts, String currentUserId) {
        this.mContext = context;
        this.posts = posts;
        this.currentUserId = currentUserId;
    }

    private static class ViewHolder{
        CircleImageView imageView_profile;
        ImageView imageView_post;
        TextView txt_username;
        TextView txt_location;
        TextView ic_like;
        TextView ic_comment;
        TextView txt_likenum;
        TextView txt_time;
        //TextView txt_likeappend;
    }


    @Override
    public int getCount() {
        return posts.size();
    }

    @Override
    public Post getItem(int position) {
        return posts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View view, @NonNull ViewGroup viewGroup) {

        Log.e("HomeListAdapter", "list size: " + getCount());

        Log.e("HomeListAdapter", "start create view");

        final HomeListAdapter.ViewHolder holder;

        if(view == null){
            holder = new HomeListAdapter.ViewHolder();
            //view = mInflater.inflate(layoutResource, viewGroup, false);
            view = LayoutInflater.from(mContext).inflate(R.layout.home_fragment_item,viewGroup,
                    false);

            holder.txt_username = (TextView) view.findViewById(R.id.username);
            holder.ic_comment = (TextView) view.findViewById(R.id.comment);
            holder.ic_like = (TextView) view.findViewById(R.id.like);
            holder.txt_likenum = (TextView) view.findViewById(R.id.liked_num);
            holder.txt_location = (TextView) view.findViewById(R.id.location);
            holder.imageView_profile = (CircleImageView) view.findViewById(R.id.img_pro);
            holder.imageView_post = (ImageView) view.findViewById(R.id.img_post);
            holder.txt_time = (TextView) view.findViewById(R.id.txt_time);

            view.setTag(holder);
        }else{
            holder = (HomeListAdapter.ViewHolder) view.getTag();
        }

       // currentPost = getItem(position);


        Log.e("HomeListAdapter", "getItemId" + getItemId(position));

        postUserId = getItem(position).getUserId();
        Log.e("HomeListAdapter", "getItem" + getItem(position));


        Log.e("HomeListAdapter", "get postUserId" + postUserId);
        holder.txt_likenum.setText(String.valueOf(getItem(position).getLike()) + "liked");
        holder.txt_location.setText("location: " + getItem(position).getLocation());

        holder.txt_time.setText("time: " + getItem(position).getTimeStamp());

        //display posted image
        postPhoto = getItem(position).getUrl();
        Log.e("HomeListAdapter", "get postPhoto url" + postPhoto);

        if (postPhoto != null){
            Log.e("DISPLAY Post PHOTO", "show photos");

            FirebaseStorage storage = FirebaseStorage.getInstance();

            // Creating a reference to the link
            StorageReference httpsReference = storage.getReferenceFromUrl(postPhoto);

            Glide.with(mContext /* context */)
                    .load(httpsReference)
                    .into(holder.imageView_post);

        }else {
            Log.e("DISPLAY Post PHOTO", "no photo was added");
        }

        DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();

        Log.e("HomeListAdapter", "search for users");

        Query filter = mRoot.child("user_setting").orderByChild("postUserId").equalTo(postUserId);
        //display profile photo for user
        filter.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot singleSnapshot: dataSnapshot.getChildren()){

                    UserProfileModel user = singleSnapshot
                            .getValue(UserProfileModel.class);

                    String usrname = user.getUsername();
                    holder.txt_username.setText(usrname);

                    String profilePhoto = user.getProfilePhoto();

                    if (!profilePhoto.equals("")){

                        ImageLoader imageLoader = ImageLoader.getInstance();
                        imageLoader.displayImage(profilePhoto, holder.imageView_profile);
                    }else {
                        Log.e("DISPLAY profile PHOTO", "no photo was added");
                    }

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        holder.ic_comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CLICK", "go to Comment");




            }
        });

        //like click


        holder.ic_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("CLICK", "like");
                if (like){ //if already liked , cancel like
                    final DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
                    final String postId = getItem(position).getKey();

                    //update like num in post
                    Query queryPost = mRoot.child("post").orderByChild("key").equalTo(postId);
                    queryPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                               //Post cPost = dataSnapshot.getValue(Post.class);
                                getItem(position).reduceLike();
                                int likenum = getItem(position).getLike();
                                mRoot.child("post").child(postId).child("like")
                                        .setValue(likenum);
                                Log.e("CLICK Like", "post like -1 ");
                                holder.txt_likenum.setText(String.valueOf(likenum) + "liked");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //delete like object
                    Query queryLikep = mRoot.child("like").orderByChild("postid").equalTo(postId);
                    queryLikep.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                for(DataSnapshot singleSnapshot :  dataSnapshot.getChildren()){

                                    Like like = singleSnapshot.getValue(Like.class);


                                    if (like.getUserId().equals(currentUserId)){
                                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren()
                                                .iterator().next();
                                        String key = nodeDataSnapshot.getKey();
                                        mRoot.child("like").child(key).removeValue();
                                        Log.e("CLICK Like", "delete like");
                                        break;

                                    }


                                }

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    like = false;



                }else { //if haven't liked, like it

                    final DatabaseReference mRoot = FirebaseDatabase.getInstance().getReference();
                    final String postId = getItem(position).getKey();

                    //update like num in post
                    Query queryPost = mRoot.child("post").orderByChild("key").equalTo(postId);
                    //display profile photo for user
                    queryPost.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                //Post cPost = dataSnapshot.getValue(Post.class);
                                getItem(position).addLike();
                                mRoot.child("post").child(postId).setValue(getItem(position));
                                Log.e("CLICK Like", "post like +1 ");
                                holder.txt_likenum.setText(String.valueOf(getItem(position).getLike()) + "liked");
                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });

                    //add like object
                    mRoot.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()){
                                Log.e("CLICK Like", "add like");
                                Like like = new Like(postId, currentUserId);
                                mRoot.child("like").push().setValue(like);

                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {
                        }
                    });

                    like = true;


                }
            }
        });


        return view;
    }
}
