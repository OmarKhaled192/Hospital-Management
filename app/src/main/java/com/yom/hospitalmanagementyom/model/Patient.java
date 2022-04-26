package com.yom.hospitalmanagementyom.model;

import java.io.Serializable;

public class Patient implements Serializable {
  private String Id, Profile, Name, Phone, Email, Password, DOB, Gender, Type, Status;

  public String getId() {
    return Id;
  }

  public void setId(String id) {
    Id = id;
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

  public String getPhone() {
    return Phone;
  }

  public void setPhone(String phone) {
    Phone = phone;
  }

  public String getEmail() {
    return Email;
  }

  public void setEmail(String email) {
    Email = email;
  }

  public String getPassword() {
    return Password;
  }

  public void setPassword(String password) {
    Password = password;
  }

  public String getDOB() {
    return DOB;
  }

  public void setDOB(String DOB) {
    this.DOB = DOB;
  }

  public String getGender() {
    return Gender;
  }

  public void setGender(String gender) {
    Gender = gender;
  }

  public String getType() {
    return Type;
  }

  public void setType(String type) {
    Type = type;
  }

  public String getStatus() {
    return Status;
  }

  public void setStatus(String status) {
    Status = status;
  }
}
