package com.example.ictproject;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private final static String TAG="@@@MainActivity";
    ListView listView;
    listAdapter adapter;
    List<Data> list;
    private long pressedTime = 0;
    public static Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mContext = this;

        // 조미료통을 설정하지 않았으면 설정화면으로 이동
        String[] condiment = {PreferenceManager.getString(this, "condiment0"), PreferenceManager.getString(this, "condiment1")};
        if(condiment[0].equals("") || condiment[1].equals("")){
            Log.d(TAG, "onCreate: InitialActivity 이동");
            Intent intent = new Intent(MainActivity.this, InitialActivity.class);
            startActivity(intent);
        }

        updateLV();
        setListner();

        FloatingActionButton fab = findViewById(R.id.floatingActionButton);
        fab.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });

        ImageButton quesbtn = findViewById(R.id.main_ques);
        quesbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, QuestionActivity.class);
                startActivity(intent);
            }
        });


        Button bt_rm = findViewById(R.id.bt_remove);
        bt_rm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceManager.clear(mContext);
                Intent intent = new Intent(MainActivity.this, InitialActivity.class);
                startActivity(intent);
            }
        });

        Button bt_play = findViewById(R.id.bt_play);
        bt_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, PlayActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent resultIntent){
        updateLV();
    }

    public void updateLV() {
        Log.d(TAG, "updateLV: ");

        list = new ArrayList<>();
        listView = findViewById(R.id.list_main);
        try {
            String[] condiment = {PreferenceManager.getString(this, "condiment0"), PreferenceManager.getString(this, "condiment1")};
            String myJSON = new GetData().execute("getData.php", "recipe", "all").get();
            Log.d(TAG, "onCreate: " + myJSON);

            if (myJSON.equals("")) {
                findViewById(R.id.tv_navigation).setVisibility(View.VISIBLE);
                list.clear();
                adapter = new listAdapter(list);
                listView.setAdapter(adapter);
            }
            // 조미료가 하나라도 있을 경우 데이터를 불러옴

            JSONObject jsonObject = new JSONObject(myJSON);
            JSONArray dataJSON = jsonObject.getJSONArray(getString(R.string.DB_tableName));

            int num = dataJSON.length();
            for (int i = 0; i < num; i++) {
                Log.d(TAG, "updateLV: for loop number : " + i);
                JSONObject c = dataJSON.getJSONObject(i);
                int id = c.getInt(getString(R.string.DB_colID));
                int[] gram = {Integer.parseInt(c.getString(getString(R.string.DB_colCondiment0))), Integer.parseInt(c.getString(getString(R.string.DB_colCondiment1)))};
                Data data = new Data();

                data.setId(id);
                data.setTitle(c.getString(getString(R.string.DB_colTitle)));
                data.setCondiment0(condiment[0]);
                data.setCondiment1(condiment[1]);
                data.setGram0(gram[0]);
                data.setGram1(gram[1]);

                list.add(data);
            }
            adapter = new listAdapter(list);
            listView.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void setListner(){

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                intent.putExtra("id", list.get(position).getId());
                intent.putExtra("position", position);
                intent.putExtra("title", list.get(position).getTitle());
                intent.putExtra("gram0", Integer.toString(list.get(position).getGram0()));
                intent.putExtra("gram1", Integer.toString(list.get(position).getGram1()));

                startActivity(intent);
            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener(){
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                builder.setTitle("레시피 삭제").setMessage("삭제하시겠습니까?");

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: 삭제 요청");
                        InsertData task = new InsertData();
                        task.execute("delData.php", getString(R.string.DB_colID), Integer.toString(list.get(position).getId()));
                    }
                });

                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener(){
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        Log.d(TAG, "onClick: 삭제 취소");
                    }
                });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();
                return true;
            }
        });
    }

    @Override
    public void onBackPressed() {
        if ( pressedTime == 0 ) {
            Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
            pressedTime = System.currentTimeMillis();
        }
        else {
            int seconds = (int) (System.currentTimeMillis() - pressedTime);

            if ( seconds > 2000 ) {
                Toast.makeText(MainActivity.this, " 한 번 더 누르면 종료됩니다." , Toast.LENGTH_LONG).show();
                pressedTime = 0 ;
            }
            else {
                super.onBackPressed();
                finish(); // app 종료 시키기
            }
        }
    }


}
