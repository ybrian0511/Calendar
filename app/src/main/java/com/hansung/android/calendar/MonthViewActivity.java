package com.hansung.android.calendar;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class MonthViewActivity extends AppCompatActivity {

    private TextView month_year_text;
    private Calendar cal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent intent = getIntent();
        month_year_text = findViewById(R.id.YearMonthTitle);
        if(intent.hasExtra("Date")) { // 키 값이 있는지 체크
            cal = (Calendar) intent.getSerializableExtra("Date"); // 전달 받는다.
        }
        else cal = Calendar.getInstance(); // 현재 시간 정보
        CalendarView(); // 캘린더 뷰 함수 호출
        left_buttonClick(); // 이전 버튼 클릭 함수 호출
        rigth_buttonClick(); // 다음 버튼 클릭 함수 호출
    }

    public ArrayList<String> daysArray(Calendar date){ //
        ArrayList <String> days_in_month = new ArrayList();
        Calendar cal = date;
        cal.set(Calendar.DATE,1); // 날짜 지정(1일)

        int day_of_week = cal.get(Calendar.DAY_OF_WEEK)-1; // 현재 월의 1일의 요일 (일:1~토:7) -1을 한 이유는 밑에서 설명
        int num_of_day = cal.getActualMaximum(Calendar.DATE); // 현재 월의 날짜의 최대 수 (1월:31일 2월:28일..)

        for (int i = 1; i <= 42; i++) { // 6행 7열 >> 42개
            if(i<= day_of_week || i> (num_of_day + day_of_week)) // 첫째날이나 마지막날이 아닌 경우
                days_in_month.add(""); // 공백 추가 (빈칸)
            else // 날짜 내에 있는 경우
                days_in_month.add(String.valueOf(i-day_of_week));
                // 예를들어 4월의 경우 6번째가 1일,금요일(6) -1을 하지 않으면 첫째날이 1이 아닌 0이 나타나게된다.
        }
        return days_in_month;
    }

    private String month_year_format(Calendar date){ // 현재 날짜를 String으로 불러온다.
        SimpleDateFormat today = new SimpleDateFormat("yyyy년 MM월"); // 날짜 포맷 지정
        return today.format(date.getTime()); // 현재 날짜
    }

    private void CalendarView() { // 캘린더 뷰 함수
        month_year_text.setText(month_year_format(cal)); // 현재 년도와 월 저장
        ArrayList<String> days = daysArray(cal); // 데이터 원본 준비

        GridAdapter adapter = new GridAdapter(this,R.layout.day, days);  // 어댑터 생성

        GridView GridView = (GridView) findViewById(R.id.gridview); // 어댑터 연결
        GridView.setAdapter(adapter);

        GridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View vClicked, int position, long id) {
                String date = adapter.getItem(position);
                int Calendar_Month = cal.get(Calendar.MONTH) + 1; // +1을 하는 이유는 1월이 0이기 때문이다.
                int Calendar_Year = cal.get(Calendar.YEAR); // 년도는 그대로(+-필요x)
                Toast.makeText(MonthViewActivity.this, Calendar_Year+"."+Calendar_Month+"."+date ,
                        Toast.LENGTH_SHORT).show(); // 날짜 클릭 시 년.월.일 토스트 메시지 출력
            }
        });
    }

    private void left_buttonClick() { // 이전 버튼 클릭 함수
        Button leftButton = (Button) findViewById(R.id.left_button);
        leftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 버튼 클릭 시
                int monthText = cal.get(Calendar.MONTH); // monthText는 현재 월
                cal.set(Calendar.MONTH, monthText - 1); // 현재 월에서 1이 빼진 이전 월이 된다.
                Intent intent = new Intent(MonthViewActivity.this, MonthViewActivity.class); // 현재 액티비티에서 현재 액티비티 호출
                intent.putExtra("Date", cal); //
                startActivity(intent); // 인텐트 객체 전달
                finish(); // 현재의 액티비티를 종료
            }
        });
    }

    private void rigth_buttonClick() { // 다음 버튼 클릭 함수
        Button rightButton = (Button) findViewById(R.id.right_button);
        rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) { // 버튼 클릭 시
                int month = cal.get(Calendar.MONTH); // monthText는 현재 월
                cal.set(Calendar.MONTH, month + 1); // 현재 월에서 1이 더해진 다음 월이 된다.
                Intent intent = new Intent(MonthViewActivity.this, MonthViewActivity.class); // 현재 액티비티에서 현재 액티비티 호출
                intent.putExtra("Date", cal); //
                startActivity(intent); // 인텐트 객체 전달
                finish(); // 현재의 액티비티를 종료
            }
        });
    }
}

