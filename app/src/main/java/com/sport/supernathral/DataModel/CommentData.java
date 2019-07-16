package com.sport.supernathral.DataModel;

import java.util.ArrayList;

public class CommentData {

    private String id;
    private String moment_id;
    private String user_id;
    private String comment;
    private String comment_id;
    private String delete_flag;
    private String is_active;
    private String entry_date;
    private String modified_date;
    private String user_name;
    private String user_image;
    private String moment_comment_like_count;
    private String moment_comment_sub_count;
    private ArrayList<SubCommentData> list_sub_comment;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMoment_id() {
        return moment_id;
    }

    public void setMoment_id(String moment_id) {
        this.moment_id = moment_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getComment_id() {
        return comment_id;
    }

    public void setComment_id(String comment_id) {
        this.comment_id = comment_id;
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

    public String getMoment_comment_like_count() {
        return moment_comment_like_count;
    }

    public void setMoment_comment_like_count(String moment_comment_like_count) {
        this.moment_comment_like_count = moment_comment_like_count;
    }

    public String getMoment_comment_sub_count() {
        return moment_comment_sub_count;
    }

    public void setMoment_comment_sub_count(String moment_comment_sub_count) {
        this.moment_comment_sub_count = moment_comment_sub_count;
    }

    public ArrayList<SubCommentData> getList_sub_comment() {
        return list_sub_comment;
    }

    public void setList_sub_comment(ArrayList<SubCommentData> list_sub_comment) {
        this.list_sub_comment = list_sub_comment;
    }
}
