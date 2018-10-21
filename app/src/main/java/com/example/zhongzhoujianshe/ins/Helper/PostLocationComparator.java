package com.example.zhongzhoujianshe.ins.Helper;

import java.util.Comparator;

public class PostLocationComparator implements Comparator<Post> {
    @Override
    public int compare(Post o1, Post o2) {
        // Order ascending.
        int ret = o1.getLocation().compareTo(o2.getLocation());

        // Order descending.
        //int ret = o2.getRegistDate().compareTo(o1.getRegistDate());

        return ret;
    }
}
