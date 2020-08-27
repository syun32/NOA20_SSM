package com.example.ictproject;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class QuestionPagerAdapter extends RecyclerView.Adapter<QuestionHolderPage> {

    private ArrayList<QuestionDataPage> listData;

    QuestionPagerAdapter(ArrayList<QuestionDataPage> data) {
        this.listData = data;
    }

    @Override
    public QuestionHolderPage onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        View view = LayoutInflater.from(context).inflate(R.layout.question_item, parent, false);
        return new QuestionHolderPage(view);
    }

    @Override
    public void onBindViewHolder(QuestionHolderPage holder, int position) {
        if(holder instanceof QuestionHolderPage){
            QuestionHolderPage viewHolder = (QuestionHolderPage) holder;
            viewHolder.onBind(listData.get(position));
        }
    }

    @Override
    public int getItemCount() {
        return listData.size();
    }
}
