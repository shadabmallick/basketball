package com.sport.supernathral.NetworkConstant;

/**
 * Created by developer on 11/5/18.
 */

public class AppConfig
{

    /// lab url
    private static String BASE_URL = "http://lab-3.sketchdemos.com/P1159_Basketball/api/";

    // live
    ///public static String LIVE_URL = "https://www.supernahtralsports.com/api/";

    public static String LIVE_URL = "http://lab-3.sketchdemos.com/P1159_Basketball/api/";


    public static final String REGISTER           = LIVE_URL + "registration";
    public static final String LOGIN              = LIVE_URL + "login";

    public static final String ABOUT_US           = LIVE_URL + "about";




    public static final String CHAT_USER_LIST     = LIVE_URL + "chat_list";
    public static final String CHAT_MSG_LIST      = LIVE_URL + "get_user_chat";
    public static final String POST_USER_CHAT     = LIVE_URL + "post_user_chat";
    public static final String FORGOT_PASSWORD     = LIVE_URL + "forgotPassword";
    public static final String RESET_PASSWORD     = LIVE_URL + "resetPassword";
    public static final String USER_PROFILE       = LIVE_URL + "user_profile";
    public static final String USER_PROFILE_UPDATE     = LIVE_URL + "user_profile_update";
    public static final String CHANGE_PASSWORD     = LIVE_URL + "changePassword";
    public static final String UPDATE_NOTIFICATION     = LIVE_URL + "update_user_notification";
    public static final String NEWS     =    LIVE_URL + "news";
    public static final String NEWS_SEARCH     = LIVE_URL + "news_search";
    public static final String NEWS_DETAIlS     = LIVE_URL + "news_details";
    public static final String GET_PLAYER_INFO     = LIVE_URL + "get_player_info";
    public static final String SEARCH_USER     = LIVE_URL + "search_user";
    public static final String SPONSOR     = LIVE_URL + "sponsor";
    public static final String GAMELIST     = LIVE_URL + "game_list";

    public static final String post_news_comment                = LIVE_URL + "post_news_comment";
    public static final String post_news_comment_on_comment     = LIVE_URL + "post_news_comment_on_comment";
    public static final String post_news_like_on_comment     = LIVE_URL + "post_news_like_on_comment";
    public static final String news_comment_delete     = LIVE_URL + "news_comment_delete";
    public static final String user_profile     = LIVE_URL + "user_profile";


  /*  public static final String CHAT_USER_LIST     = BASE_URL + "chat_list";
    public static final String CHAT_MSG_LIST      = BASE_URL + "get_user_chat";
    public static final String POST_USER_CHAT     = BASE_URL + "post_user_chat";
    public static final String FORGOT_PASSWORD    = BASE_URL + "forgotPassword";
    public static final String RESET_PASSWORD     = BASE_URL + "resetPassword";
    public static final String USER_PROFILE       = BASE_URL + "user_profile";*/


    public static final String MOMENT             = LIVE_URL + "moment";
    public static final String NEWS_COMMENT       = LIVE_URL + "news_comment";



}