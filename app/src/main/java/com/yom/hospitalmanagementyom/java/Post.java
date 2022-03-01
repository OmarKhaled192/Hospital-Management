package com.yom.hospitalmanagementyom.java;


public class Post {
  private String id, Profile,Name,Time,Post,Image,Video;
  private long Like,DisLike,Star;
  public String getId() {
    return id;
  }

  public void setId(String id) {
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
