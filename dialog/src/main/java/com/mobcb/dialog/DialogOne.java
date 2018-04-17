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

public class DialogOne extends AlertDialog {

    private TextView tv_title;
    private TextView tv_content;
    private Button btn_confirm;

    public DialogOne(Context context) {
        super(context);
        View view = LayoutInflater.from(context).inflate(R.layout.layout_dialog_one, null);
        setView(view);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_content = (TextView) view.findViewById(R.id.tv_content);
        btn_confirm = (Button) view.findViewById(R.id.btn_confirm);
        setCanceledOnTouchOutside(false);
        setCancelable(false);
    }

    public DialogOne setTitle(String title) {
        if (!TextUtils.isEmpty(title)) {
            tv_title.setText(title);
            tv_title.setVisibility(View.VISIBLE);
        }
        return this;
    }

    public DialogOne setContent(String content) {
        tv_content.setText(content);
        return this;
    }

    public DialogOne setButton(String text, final View.OnClickListener onClickListener) {
        btn_confirm.setText(text);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cancel();
                onClickListener.onClick(view);
            }
        });
        return this;
    }
}
