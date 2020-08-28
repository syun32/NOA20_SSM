package com.example.ictproject;

import android.app.Activity;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
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
    private String condiment0;
    private String condiment1;

//    SQLiteDatabase recipeDB = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);
        mContext = this;

        condiment0 = getIntent().getStringExtra("condiment0");
        condiment1 = getIntent().getStringExtra("condiment1");

        TextView[] textView = {findViewById(R.id.condiment0), findViewById(R.id.condiment1)};
        final EditText[] inputText = {findViewById(R.id.title), findViewById(R.id.gram0), findViewById(R.id.gram1)};

        if(condiment1 == null && condiment0 == null ){
            Log.d(TAG, "onCreate: condiment is null");
            condiment0 = PreferenceManager.getString(this, "condiment0");
            condiment1 = PreferenceManager.getString(this, "condiment1");
        }

        textView[0].setText(condiment0);
        textView[1].setText(condiment1);

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

                try{
//                    recipeDB = openOrCreateDatabase(DataBase.RecipeEntry.DB_NAME, MODE_PRIVATE, null);

                    InsertData task = new InsertData();
                    if(position == -1) {
//                        String insertSQL = "INSERT INTO " + DataBase.RecipeEntry.TABLE_NAME + " ("
//                                + DataBase.RecipeEntry.COLUMN_NAME_TITLE + ", "
//                                + DataBase.RecipeEntry.COLUMN_NAME_CONDIMENTS0 + ", "
//                                + DataBase.RecipeEntry.COLUMN_NAME_CONDIMENTS1 + ", "
//                                + DataBase.RecipeEntry.COLUMN_NAME_GRAM0 + ", "
//                                + DataBase.RecipeEntry.COLUMN_NAME_GRAM1
//                                + ")  VALUES ('"
//                                + title + "', '" + condiment0 + "', '" + condiment1 + "', " + gram0 + ", " + gram1 + ");";
//                        recipeDB.execSQL(insertSQL);

                        task.execute("setData.php",
                                getString(R.string.DB_colTitle), title,
                                getString(R.string.DB_colCondiment0), gram0,
                                getString(R.string.DB_colCondiment1), gram1
                        );
                    } else {
//                        String updateSQL = "UPDATE "+ DataBase.RecipeEntry.TABLE_NAME + " SET "
//                                + DataBase.RecipeEntry.COLUMN_NAME_TITLE + " = '" + title + "', "
//                                + DataBase.RecipeEntry.COLUMN_NAME_CONDIMENTS0 + " = '" + condiment0 + "', "
//                                + DataBase.RecipeEntry.COLUMN_NAME_CONDIMENTS1 + " = '" + condiment1 + "', "
//                                + DataBase.RecipeEntry.COLUMN_NAME_GRAM0 + " = " + gram0 + ", "
//                                + DataBase.RecipeEntry.COLUMN_NAME_GRAM1 + " = " + gram1
//                                + " WHERE ID = " + id;
//                        recipeDB.execSQL(updateSQL);

                        task.execute("editData.php",
                                getString(R.string.DB_colID), Integer.toString(id),
                                getString(R.string.DB_colTitle), title,
                                getString(R.string.DB_colCondiment0), gram0,
                                getString(R.string.DB_colCondiment1), gram1
                        );
                    }
//                    recipeDB.close();

                } catch (Exception e){
                    e.printStackTrace();
                }

                ((MainActivity)MainActivity.mContext).updateLV();
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
        finish();
    }
}