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
        list.add(new QuestionDataPage("          [초기화면]\n            사용할 조미료를 설정합니다",1));
        list.add(new QuestionDataPage("          [메인화면]\n          ① 사용을 위한 도움말을 보여줍니다 \n          ② 레시피를 추가합니다",2));
        list.add(new QuestionDataPage("          ③ 처음에 설정한 조미료를 변경합니다 \n          ④ 조미료값을 입력해 레시피 저장 없이 바로 실행합니다 \n          ⑤ 음식 이름을 입력하여 레시피를 검색합니다",3));
        list.add(new QuestionDataPage("          음식 추가시[메인에서 ② 선택] \n          음식의 이름, 조미료의 양을 입력하여 레시피를 추가합니다",4));
        list.add(new QuestionDataPage("          레시피 목록에서 음식을 \n          짧게 클릭하여 실행, \n          길게 클릭하여 수정 또는 삭제합니다",5));

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