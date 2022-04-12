package com.yom.hospitalmanagementyom.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity(tableName = "post_table")
public class Post {

  @PrimaryKey
  @NonNull
  private String id;
  private String Profile,Name,Time,Post,Image,Video;
  private long Like,DisLike,Star;

  public Post() {
    id = "0";
  }

  @NonNull
  public String getId() {
    return id;
  }

  public void setId(@NonNull String id) {
    this.id = id;
  }

  public String getProfile() {
    return Profile;
  }

  public void setProfile(String profile) {
    Profile = profile;
  }

  public String getName() {
    return Name;
  }

  public void setName(String name) {
    Name = name;
  }

  public String getTime() {
    return Time;
  }

  public void setTime(String time) {
    Time = time;
  }

  public String getPost() {
    return Post;
  }

  public void setPost(String post) {
    Post = post;
  }

  public String getImage() {
    return Image;
  }

  public void setImage(String image) {
    Image = image;
  }

  public String getVideo() {
    return Video;
  }

  public void setVideo(String video) {
    Video = video;
  }

  public long getLike() {
    return Like;
  }

  public void setLike(long like) {
    Like = like;
  }

  public long getDisLike() {
    return DisLike;
  }

  public void setDisLike(long disLike) {
    DisLike = disLike;
  }

  public long getStar() {
    return Star;
  }

  public void setStar(long star) {
    Star = star;
  }
}
