package com.mobcb.dialog;

import android.content.Context;

/**
 * Created by lvmenghui
 * on 2018/4/9.
 */

public class MyDialog {

    public static DialogOne dialogOne(Context context) {
        return new DialogOne(context);
    }

    public static DialogTwo dialogTwo(Context context) {
        return new DialogTwo(context);
    }

    public static DialogThree dialogThree(Context context) {
        return new DialogThree(context);
    }

}
