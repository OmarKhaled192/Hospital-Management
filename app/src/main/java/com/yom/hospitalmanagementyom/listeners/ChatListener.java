package com.yom.hospitalmanagementyom.listeners;

import com.yom.hospitalmanagementyom.model.Doctor;

public interface ChatListener {
    void getLastMessageFinish();
    void onClickItem(String idChat, String idReceiver);
}
