package com.example.ictproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class listAdapter extends BaseAdapter {
    List<Data> list;

    public listAdapter(List<Data> list) {
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        Context c = parent.getContext();
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.list_card, parent, false);

        }

        TextView listTitle = view.findViewById(R.id.list_Title);
        TextView list1 = view.findViewById(R.id.list_1);
        TextView list2 = view.findViewById(R.id.list_2);

        Data data = list.get(position);

        listTitle.setText(data.getTitle());
        list1.setText(data.getCondiment0() + " : " + data.getGram0());
        list2.setText(data.getCondiment1() + " : " + data.getGram1());
        return view;
    }

    public void addData(List<Data> list){
        this.list = list;
    }

}
