package com.sport.supernathral.Utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Common {

    public static final String Key_SingleChatNoti = "getSingleChatNoti";
    public static final String Key_GroupNoti = "Key_GroupNoti";





    public static void hideSoftKeyboard(View view, Activity activity) {
        InputMethodManager imm = (InputMethodManager)
                activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(),
                InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    public static void showSoftKeyboard(View view, Activity activity) {
        if (view.requestFocus()) {
            InputMethodManager imm = (InputMethodManager)
                    activity.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
    }

}
