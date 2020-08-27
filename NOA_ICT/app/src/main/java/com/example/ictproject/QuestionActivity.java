package com.example.ictproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import java.util.ArrayList;

public class QuestionActivity extends AppCompatActivity {

    ViewPager2 viewPager2;
    private ImageButton addbackbtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        viewPager2 = findViewById(R.id.viewPager2);

        ArrayList<QuestionDataPage> list = new ArrayList<>();
        list.add(new QuestionDataPage("          사용하실 조미료의 이름을 정해줍니다.",1));
        list.add(new QuestionDataPage("          1) 앱을 종료합니다. \n          2) 도움말을 볼 수 있습니다. \n          3) 레시피를 추가합니다.",2));
        list.add(new QuestionDataPage("          4) 처음에 설정한 조미료를 변경합니다. \n          5) 조미료값을 입력해 저장 없이 바로 실행합니다.",3));
        list.add(new QuestionDataPage("          3번 버튼을 클릭해 \n          레시피의 이름, 조미료의 양을 입력합니다.",3));
        list.add(new QuestionDataPage("          추가된 레시피를 \n          짧게 클릭하여 실행, \n          길게 클릭하여 수정 또는 삭제합니다.",4));

        viewPager2.setAdapter(new QuestionPagerAdapter(list));
        viewPager2.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);


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