package com.example.zhongzhoujianshe.ins.Helper;

import android.support.annotation.NonNull;

public class Post{
    private String userId;
    private String url;
    private String timeStamp;
    private String location;
    private String key;
    private int like;

    public Post() {
    }

    public Post(String userId, String url, String timeStamp, String location, String key){
        this.userId = userId;
        this.url = url;
        this.timeStamp = timeStamp;
        this.location = location;
        this.key = key;
    }
    public void setUserId(String userId){
        this.userId = userId;
    }
    public String getUserId(){
        return userId;
    }
    public void setKey(String key){
        this.key = key;
    }
    public String getKey(){
        return key;
    }
    public void setUrl(String url){
        this.url = url;
    }
    public String getUrl(){
        return url;
    }
    public void setTimeStamp(String timeStamp){
        this.timeStamp = timeStamp;
    }
    public String getTimeStamp(){
        return timeStamp;
    }

    public String getLocation() {
         return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getLike() {
        return like;
    }

    public void addLike() {
        like = like + 1;
    }
    public void reduceLike(){
        like = like - 1;
    }

}
