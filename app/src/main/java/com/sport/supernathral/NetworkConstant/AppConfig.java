package com.sport.supernathral.NetworkConstant;

/**
 * Created by developer on 11/5/18.
 */

public class AppConfig
{

    /// lab url
    private static String BASE_URL = "http://lab-3.sketchdemos.com/P1159_Basketball/api/";

    // live
   // public static String BASE_URL = "https://www.supernahtralsports.com/api/";



    public static final String REGISTER           = BASE_URL + "registration";
    public static final String LOGIN              = BASE_URL + "login";

    public static final String ABOUT_US           = BASE_URL + "about";
    public static final String NEWSDETAILS        = BASE_URL + "news_details";



    public static final String CHAT_USER_LIST     = BASE_URL + "chat_list";
    public static final String CHAT_MSG_LIST      = BASE_URL + "get_user_chat";
    public static final String POST_USER_CHAT     = BASE_URL + "post_user_chat";


}