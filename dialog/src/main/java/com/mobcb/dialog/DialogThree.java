package com.mobcb.dialog;

import android.content.Context;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by lvmenghui
 * on 2018/4/9.
 */

public class DialogThree extends AlertDialog {

    private TextView tv_title;
    private TextView tv_content;
    private Button btn_left;
    private Button btn_middle;
    private Button btn_right;

    public DialogThree(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_three, null);
        setView(view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        btn_left = (Button) view.findViewById(R.id.btn_left);
        btn_middle = (Button) view.findViewById(R.id.btn_middle);
        btn_right = (Button) view.findViewById(R.id.btn_right);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public DialogThree setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DialogThree setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    public DialogThree setLeftButton(String text, final View.OnClickListener onClickListener) {
        btn_left.setText(text);
        btn_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                onClickListener.onClick(view);
            }
        });
        return this;
    }

    public DialogThree setMiddleButton(String text, final View.OnClickListener onClickListener) {
        btn_middle.setText(text);
        btn_middle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                onClickListener.onClick(view);
            }
        });
        return this;
    }

    public DialogThree setRightButton(String text, final View.OnClickListener onClickListener) {
        btn_right.setText(text);
        btn_right.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                onClickListener.onClick(view);
            }
        });
        return this;
    }
}
