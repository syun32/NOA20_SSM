package com.example.ictproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class PlayActivity extends AppCompatActivity {
    private Context mContext;
    private EditText[] gram;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        mContext = this;

        final String[] condiments = {PreferenceManager.getString(this, "condiment0"), PreferenceManager.getString(this, "condiment1")};
        TextView[] textView = {findViewById(R.id.condiment0), findViewById(R.id.condiment1)};
        textView[0].setText(condiments[0]);
        textView[1].setText(condiments[1]);

        gram = new EditText[]{findViewById(R.id.gram0), findViewById(R.id.gram1)};

        Button bt_submit = findViewById(R.id.bt_sumbit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String gram0 = gram[0].getText().toString();
                String gram1 = gram[1].getText().toString();

                InsertData task = new InsertData();
                task.execute("executeData.php",
                        getString(R.string.DB_colCondiment0), gram0,
                        getString(R.string.DB_colCondiment1), gram1
                );

                finish();
            }
        });

        ImageButton backbtn = findViewById(R.id.play_back);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
