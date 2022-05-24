package com.yom.hospitalmanagementyom.listeners;

import com.yom.hospitalmanagementyom.model.Post;

public interface PostsListener {
    void finishGetPosts();
    void finishGetDoctors();
    void onClickHospitalItem(int Position);
    void onClickLikePost(Post post, int Position);
    void onCancelLikePost(Post post, int Position);
    void onClickDisLikePost(Post post, int Position);
    void onCancelDisLikePost(Post post, int Position);
    void onClickStarPost(Post post, int Position);
    void onCancelStarPost(Post post, int Position);
}
