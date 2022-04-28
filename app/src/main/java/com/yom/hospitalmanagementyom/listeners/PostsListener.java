package com.yom.hospitalmanagementyom.listeners;

public interface PostsListener {
    void finishGetPosts();
    void onClickHospitalItem(int Position);
    void onClickLikePost(int Position);
    void onCancelLikePost(int Position);
    void onClickDisLikePost(int Position);
    void onCancelDisLikePost(int Position);
    void onClickStarPost(int Position);
    void onCancelStarPost(int Position);
}
