package com.example.zhongzhoujianshe.ins.Helper;

public class Like {
    private String postid;
    private String userId;  //who liked the image
    private String likeid;

    public Like() {
        // Default constructor required for calls to DataSnapshot.getValue(Like.class)
    }

    public Like(String postid, String userId) {
        this.postid = postid;
        this.userId = userId;
    }

    public void setUserId(String userId){
        this.userId = userId;
    }

    public String getUserId(){
        return userId;
    }

    public void setPostid(String postid){
        this.postid = postid;
    }

    public String getPostid(){
        return postid;
    }

}
