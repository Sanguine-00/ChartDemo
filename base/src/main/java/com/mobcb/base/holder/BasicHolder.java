package com.mobcb.base.holder;

import android.view.View;

import java.util.List;

/**
 * Created by lvmenghui
 * on 2018/4/8.
 */

public abstract class BasicHolder<T> {
    public View holderView;
    public List<T> mLists;

    public BasicHolder(List<T> mLists) {
        this.mLists = mLists;
        holderView = getInflateView();
    }

    public abstract View getInflateView();

    public abstract void bindData(int position);

}
