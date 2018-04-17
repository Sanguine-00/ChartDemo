package com.mobcb.chartdemo.manager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.mobcb.base.activity.BaseActivity;
import com.mobcb.base.mvp.BaseMvpView;
import com.mobcb.chartdemo.manager.R;
import com.mobcb.chartdemo.manager.presenter.StartPresenter;
import com.mobcb.dialog.DialogTwo;
import com.umeng.analytics.MobclickAgent;


public class StartActivity extends BaseActivity<StartPresenter> implements BaseMvpView {

    private DialogTwo dialog;//没有网络的提示框

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        mPresenter = new StartPresenter(this);
    }

    public void networkTip() {
        dialog = new DialogTwo(this)
                .setTitle(getString(R.string.app_no_network))
                .setContent(getString(R.string.app_start_no_network_text))
                .setLeftButton(getString(R.string.app_start_no_network_button_cancel), new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                                finish();
                            }
                        }
                )
                .setRightButton(getString(R.string.app_start_no_network_button_sure), new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        Intent intent = new Intent(mActivity, StartActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        dialog.show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
