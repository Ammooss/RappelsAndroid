package com.ynov.projetrappels.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.ynov.projetrappels.R;
import com.ynov.projetrappels.Rappel;

import java.util.List;

public class RappelAdapter extends BaseAdapter {
    private Context context;
    private List<Rappel> rappelList;
    private LayoutInflater inflater;

    public RappelAdapter(Context context, List<Rappel> rappelList){
        this.context=context;
        this.rappelList=rappelList;
        this.inflater=LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return rappelList.size();
    }

    @Override
        public Rappel getItem(int position) {
        return rappelList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.rappels, null);
        return convertView;
    }
}
