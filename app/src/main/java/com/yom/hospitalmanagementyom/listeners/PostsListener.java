package com.yom.hospitalmanagementyom.listeners;

public interface PostsListener {
    void finishGetPosts();
    void finishGetDoctors();
    void onClickHospitalItem(int Position);
    void onClickLikePost(int Position, String postId);
    void onCancelLikePost(int Position, String postId);
    void onClickDisLikePost(int Position, String postId);
    void onCancelDisLikePost(int Position, String postId);
    void onClickStarPost(int Position, String postId);
    void onCancelStarPost(int Position, String postId);
}
