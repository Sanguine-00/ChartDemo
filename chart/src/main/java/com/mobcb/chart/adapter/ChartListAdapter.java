package com.mobcb.chart.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.mobcb.chart.R;
import com.mobcb.chart.bean.ChartListBean;

import java.util.List;

/**
 * Created by Sanguine on 2018/4/4.
 */

public class ChartListAdapter extends BaseAdapter {

    private List<ChartListBean> list;
    private Context mContext;

    public ChartListAdapter(List<ChartListBean> list, Context mContext) {
        this.list = list;
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public ChartListBean getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.view_normal_chart_list_item, null, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        ChartListBean chartListBean = list.get(position);
        if (chartListBean != null) {
            holder.mTvName.setText(chartListBean.getGroupTitle());
        }

        return convertView;
    }

    static class ViewHolder {
        View view;
        TextView mTvName;

        ViewHolder(View view) {
            this.view = view;
            this.mTvName = (TextView) view.findViewById(R.id.tv_name);
        }
    }
}
