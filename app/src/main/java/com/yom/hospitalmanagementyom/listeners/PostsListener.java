package com.yom.hospitalmanagementyom.listeners;

public interface PostsListener {
    void finishGetPosts();
    void onClickHospitalItem(int Position);
    void onClickLikePost(int Position);
}
