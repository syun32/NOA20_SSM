package com.example.ictproject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

public class AddActivity extends Activity {
    private final static String TAG="@@@AddActivity";
    private ImageButton addbackbtn;
    private Context mContext;
    private int id;
    private int position;
    private String title;
    private long pressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        final String[] condiments = {PreferenceManager.getString(this, "condiment0"), PreferenceManager.getString(this, "condiment1")};

        final EditText[] inputText = {findViewById(R.id.title), findViewById(R.id.gram0), findViewById(R.id.gram1)};
        TextView[] textView = {findViewById(R.id.condiment0), findViewById(R.id.condiment1)};
        textView[0].setText(condiments[0]);
        textView[1].setText(condiments[1]);

        position = getIntent().getIntExtra("position", -1);
        id = getIntent().getIntExtra("id", -1);
        title = getIntent().getStringExtra("title");
        String[] gram = {getIntent().getStringExtra("gram0"), getIntent().getStringExtra("gram1")};

        Log.d(TAG, "onCreate: "+id);

        if(position != -1){
            inputText[0].setText(title);
            inputText[1].setText(gram[0]);
            inputText[2].setText(gram[1]);
        }

        Button bt_submit = findViewById(R.id.bt_sumbit);
        bt_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                title =inputText[0].getText().toString();
                String gram0 =inputText[1].getText().toString();
                String gram1 =inputText[2].getText().toString();

                if(title.equals("")||gram0.equals("")||gram1.equals("")){
                    Toast.makeText(mContext, "입력 값을 다시 확인해주세요", Toast.LENGTH_SHORT).show();
                    return;
                }

                InsertData task = new InsertData();
                if(position == -1)
                    task.execute("setData.php",
                            getString(R.string.DB_colTitle), title,
                            getString(R.string.DB_colCondiment0), gram0,
                            getString(R.string.DB_colCondiment1), gram1
                    );
                else task.execute("editData.php",
                        getString(R.string.DB_colID), Integer.toString(id),
                        getString(R.string.DB_colTitle), title,
                        getString(R.string.DB_colCondiment0), gram0,
                        getString(R.string.DB_colCondiment1), gram1
                );

                finish();
            }
        });

        addbackbtn = findViewById(R.id.add_back);
        addbackbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(AddActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(AddActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }
}