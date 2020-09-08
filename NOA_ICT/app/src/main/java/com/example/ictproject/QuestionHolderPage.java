package com.example.ictproject;

import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.recyclerview.widget.RecyclerView;

//
public class QuestionHolderPage extends RecyclerView.ViewHolder {

    private TextView tv_title;
    private ImageView iv_image;

    QuestionDataPage data;

    QuestionHolderPage(View itemView) {
        super(itemView);
        tv_title = itemView.findViewById(R.id.tv_title);
        iv_image = itemView.findViewById(R.id.imageView);
    }

    public void onBind(QuestionDataPage data){
        this.data = data;

        tv_title.setText(data.getTitle());
        if(data.idx == 1)
            iv_image.setImageResource(R.drawable.ques1);
        else if(data.idx == 2)
            iv_image.setImageResource(R.drawable.ques2);
        else if(data.idx == 3)
            iv_image.setImageResource(R.drawable.ques3);
        else if(data.idx == 4)
            iv_image.setImageResource(R.drawable.ques4);
        else if(data.idx == 5)
            iv_image.setImageResource(R.drawable.ques5);
    }
}