package com.sport.supernathral.DataModel;

import java.io.Serializable;

public class ChatData implements Serializable {

    private String id;
    private String type;
    private String sender_id;
    private String receiver_id;
    private String message;
    private String message_type;
    private String message_type_int;
    private String datetime;
    private String delete_flag;
    private String is_active;
    private String entry_date;
    private String modified_date;
    private String receiver_name;
    private String receiver_image;
    private String sender_name;
    private String sender_image;
    private String image_from;
    private boolean is_me;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSender_id() {
        return sender_id;
    }

    public void setSender_id(String sender_id) {
        this.sender_id = sender_id;
    }

    public String getReceiver_id() {
        return receiver_id;
    }

    public void setReceiver_id(String receiver_id) {
        this.receiver_id = receiver_id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getMessage_type() {
        return message_type;
    }

    public void setMessage_type(String message_type) {
        this.message_type = message_type;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public String getDelete_flag() {
        return delete_flag;
    }

    public void setDelete_flag(String delete_flag) {
        this.delete_flag = delete_flag;
    }

    public String getIs_active() {
        return is_active;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    public String getEntry_date() {
        return entry_date;
    }

    public void setEntry_date(String entry_date) {
        this.entry_date = entry_date;
    }

    public String getModified_date() {
        return modified_date;
    }

    public void setModified_date(String modified_date) {
        this.modified_date = modified_date;
    }

    public String getReceiver_name() {
        return receiver_name;
    }

    public void setReceiver_name(String receiver_name) {
        this.receiver_name = receiver_name;
    }

    public String getReceiver_image() {
        return receiver_image;
    }

    public void setReceiver_image(String receiver_image) {
        this.receiver_image = receiver_image;
    }

    public String getSender_name() {
        return sender_name;
    }

    public void setSender_name(String sender_name) {
        this.sender_name = sender_name;
    }

    public String getSender_image() {
        return sender_image;
    }

    public void setSender_image(String sender_image) {
        this.sender_image = sender_image;
    }

    public boolean isIs_me() {
        return is_me;
    }

    public void setIs_me(boolean is_me) {
        this.is_me = is_me;
    }

    public String getMessage_type_int() {
        return message_type_int;
    }

    public void setMessage_type_int(String message_type_int) {
        this.message_type_int = message_type_int;
    }

    public String getImage_from() {
        return image_from;
    }

    public void setImage_from(String image_from) {
        this.image_from = image_from;
    }




/* {
        "id": "71",
            "type": "User",
            "sender_id": "87",
            "receiver_id": "21",
            "message": "test",
            "message_type": "1",
            "datetime": "2019-05-21 06:26:03",
            "delete_flag": "N",
            "is_active": "Y",
            "entry_date": "2019-05-21",
            "modified_date": "0000-00-00 00:00:00",
            "receiver_name": "Nahtral",
            "receiver_image": "https://www.supernahtralsports.com/uploads/user/1551682037_image_image.png",
            "sender_name": "arka",
            "sender_image": "https://www.supernahtralsports.com/uploads/user/1555169075_image_image.png"
    },*/
}
