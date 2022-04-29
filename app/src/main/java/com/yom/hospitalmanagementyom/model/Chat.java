package com.yom.hospitalmanagementyom.model;

public class Chat {
    String id, idSender, idReceiver, message, time, seen, delete;
    int numberOfMessageNotSeen;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIdSender() {
        return idSender;
    }

    public void setIdSender(String idSender) {
        this.idSender = idSender;
    }

    public String getIdReceiver() {
        return idReceiver;
    }

    public void setIdReceiver(String idReceiver) {
        this.idReceiver = idReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSeen() {
        return seen;
    }

    public void setSeen(String seen) {
        this.seen = seen;
    }

    public void setNumberOfMessageNotSeen(int numberOfMessageNotSeen) {
        this.numberOfMessageNotSeen = numberOfMessageNotSeen;
    }

    public int getNumberOfMessageNotSeen() {
        return numberOfMessageNotSeen;
    }

    public String getDelete() {
        return delete;
    }

    public void setDelete(String delete) {
        this.delete = delete;
    }
}
