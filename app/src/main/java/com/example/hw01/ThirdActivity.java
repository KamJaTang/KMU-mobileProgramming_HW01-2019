package com.example.hw01;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class ThirdActivity extends Activity {

    Button calculateBtn, logoutBtn;
    TextView dStr, today;
    EditText inpY, inpM, inpD;
    Calendar calendar, dCalendar;
    boolean isTrue;
    String errorStr = "날짜 입력을 확인해주세요..";

    private int tYear, tMonth, tDay, resultNum, dYear, dMonth, dDay;
    private long d, t, r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.third);
        setTitle("D-day 계산기");

        calculateBtn = (Button) findViewById(R.id.calculateBtn);
        logoutBtn = (Button) findViewById(R.id.logoutBtn);
        dStr = (TextView) findViewById(R.id.dStr);
        today = (TextView) findViewById(R.id.today);
        inpY = (EditText) findViewById(R.id.inpY);
        inpM = (EditText) findViewById(R.id.inpM);
        inpD = (EditText) findViewById(R.id.inpD);

        calendar = Calendar.getInstance();
        tYear = calendar.get(Calendar.YEAR);
        tMonth = calendar.get(Calendar.MONTH);
        tDay = calendar.get(Calendar.DATE);

        today.setText("오늘은 "+ tYear + "/" + (tMonth+1) + "/" +  tDay + "입니다.");

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });

        calculateBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                if(hasTxt(inpY)&&hasTxt(inpM)&&hasTxt(inpD)){

                    dYear = Integer.parseInt(inpY.getText().toString());
                    dMonth = Integer.parseInt(inpM.getText().toString());
                    dDay = Integer.parseInt(inpD.getText().toString());
                    isTrue = false;

                    if (dMonth == 0 || dMonth == 2 || dMonth == 4 || dMonth == 6 || dMonth == 7 || dMonth == 9 || dMonth == 11) {
                        if (dDay >= 1 && dDay <= 31) isTrue = true;
                    } else if (dMonth == 3 || dMonth == 5 || dMonth == 8 || dMonth == 10) {
                        if (dDay >= 1 && dDay <= 30) isTrue = true;
                    } else if (dMonth == 1) {
                        if (dYear % 4 == 0)
                            if (dDay >= 1 && dDay <= 29) isTrue = true;
                            else if (dDay >= 1 && dDay <= 28) isTrue = true;

                    }

                    if (isTrue) {
                        resultNum = calcuateDDay();
                        if (resultNum > 0) {
                            dStr.setText(String.format("D-%d", resultNum));
                        } else if (resultNum == 0) {
                            dStr.setText(String.format("D-day"));
                        } else {
                            dStr.setText(String.format("D+%d", Math.abs(resultNum)));
                        }
                    } else {
                        Toast.makeText(getApplicationContext(), errorStr, Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(getApplicationContext(), errorStr, Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private int calcuateDDay(){
        dCalendar = Calendar.getInstance();
        dCalendar.set(dYear,(dMonth-1),dDay);

        t=calendar.getTimeInMillis() / (24*60*60*1000);
        d=dCalendar.getTimeInMillis()/(24*60*60*1000);
        r=(d-t);

        return (int)r;
    }

    private boolean hasTxt(EditText et){
        return (et.getText().toString().trim().length() > 0);
    }

}
