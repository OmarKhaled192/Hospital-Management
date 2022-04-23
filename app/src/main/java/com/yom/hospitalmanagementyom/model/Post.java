package com.yom.hospitalmanagementyom.model;

import java.util.List;

public class Post {
  private String Id, IdDoctor,Time,Post,Image,Video;
  private List<String> Likes,DisLikes,Stars;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
  }

  public String getIdDoctor() {
    return IdDoctor;
  }

  public void setIdDoctor(String idDoctor) {
    IdDoctor = idDoctor;
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

  public List<String> getLikes() {
    return Likes;
  }

  public void setLikes(List<String> likes) {
    Likes = likes;
  }

  public List<String> getDisLikes() {
    return DisLikes;
  }

  public void setDisLikes(List<String> disLikes) {
    DisLikes = disLikes;
  }

  public List<String> getStars() {
    return Stars;
  }

  public void setStars(List<String> stars) {
    Stars = stars;
  }
}
