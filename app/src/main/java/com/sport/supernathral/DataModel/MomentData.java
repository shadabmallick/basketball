package com.sport.supernathral.DataModel;

import java.util.ArrayList;

public class MomentData {

    private String id;
    private String user_id;
    private String content;
    private String file_type;
    private String delete_flag;
    private String is_active;
    private String entry_date;
    private String modified_date;
    private String user_name;
    private String user_image;
    private String moment_like_count;
    private String moment_comment_count;
    private ArrayList<String> moment_files;
    private ArrayList<CommentData> list_comment;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getFile_type() {
        return file_type;
    }

    public void setFile_type(String file_type) {
        this.file_type = file_type;
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

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getUser_image() {
        return user_image;
    }

    public void setUser_image(String user_image) {
        this.user_image = user_image;
    }

    public String getMoment_like_count() {
        return moment_like_count;
    }

    public void setMoment_like_count(String moment_like_count) {
        this.moment_like_count = moment_like_count;
    }

    public String getMoment_comment_count() {
        return moment_comment_count;
    }

    public void setMoment_comment_count(String moment_comment_count) {
        this.moment_comment_count = moment_comment_count;
    }

    public ArrayList<String> getMoment_files() {
        return moment_files;
    }

    public void setMoment_files(ArrayList<String> moment_files) {
        this.moment_files = moment_files;
    }

    public ArrayList<CommentData> getList_comment() {
        return list_comment;
    }

    public void setList_comment(ArrayList<CommentData> list_comment) {
        this.list_comment = list_comment;
    }
}
