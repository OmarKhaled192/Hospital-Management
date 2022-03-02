package com.yom.hospitalmanagementyom.model;


import java.io.Serializable;

public class Patient implements Serializable {
  private String Profile, Name, Phone, EarthPhone, Email, Password, State;

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

  public String getEarthPhone() {
    return EarthPhone;
  }

  public void setEarthPhone(String earthPhone) {
    EarthPhone = earthPhone;
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

  public String getState() {
    return State;
  }

  public void setState(String state) {
    State = state;
  }
}
