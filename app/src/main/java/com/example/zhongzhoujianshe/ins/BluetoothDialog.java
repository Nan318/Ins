package com.example.zhongzhoujianshe.ins;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.zhongzhoujianshe.ins.BluetoothPair;

import java.util.ArrayList;


public class BluetoothDialog extends BaseAdapter {

    private ArrayList<BluetoothPair> listData;

    private LayoutInflater layoutInflater;

    public BluetoothDialog(Context context, ArrayList<BluetoothPair> listData) {
        this.listData = listData;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_row_dialog, null);
            holder = new ViewHolder();
            holder.nameView = (TextView) convertView.findViewById(R.id.name);
            holder.deviceView = (TextView) convertView.findViewById(R.id.device);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.nameView.setText(listData.get(position).getDevice().getName());
        holder.deviceView.setText(listData.get(position).getDevice().getAddress());

        return convertView;
    }

    static class ViewHolder {
        TextView nameView;
        TextView deviceView;
    }
}
