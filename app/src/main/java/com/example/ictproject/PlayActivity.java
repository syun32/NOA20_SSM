package com.example.ictproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mContext = this;

        final String[] condiments = {PreferenceManager.getString(this, "condiment0"), PreferenceManager.getString(this, "condiment1")};
        TextView[] textView = {findViewById(R.id.condiment0), findViewById(R.id.condiment1)};
        textView[0].setText(condiments[0]);
        textView[1].setText(condiments[1]);

        ImageButton backbtn = findViewById(R.id.play_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
