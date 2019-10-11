package com.example.hw01;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;

public class MainActivity extends Activity {
    Button loginBtn, joinPageBtn;
    EditText id, pw;
    String loginStr;
    Boolean canLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setTitle("로그인");

        loginBtn = (Button) findViewById(R.id.loginBtn);
        joinPageBtn = (Button) findViewById(R.id.joinPageBtn);

        id = (EditText) findViewById(R.id.loginId);
        pw = (EditText) findViewById(R.id.loginPw);


        loginBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                loginStr = "[로그인을 할 수 없습니다.]";
                canLogin = true;

                if (!hasTxt(id) || !hasTxt(pw)) {
                    loginStr += "\n로그인 정보를 입력해주세요.";
                    canLogin = false;
                }
                else{

                    String nId = id.getText().toString() + ".txt";
                    String nPW = pw.getText().toString();
                    String rPW ="";
                    Boolean chkID;
                    try {
                        FileInputStream fis = openFileInput(nId);
                        BufferedReader reader = new BufferedReader(new InputStreamReader(fis));
                        rPW = reader.readLine();
                        chkID = true;
                    } catch(Exception e){
                        e.printStackTrace();
                        chkID = false;
                    }

                    if(chkID){ //id가 파일에 존재하면

                        if(!nPW.equals(rPW)){ //id와 비밀번호가 맞지 않음
                            loginStr += "\n비밀번호가 맞지 않습니다.";
                            canLogin = false;
                        }
                    }
                    else{ //아이디가 파일에 존재하지 않다면
                        loginStr += "\n등록되지 않은 아이디입니다.";
                        canLogin = false;
                    }
                }

                if(!canLogin){
                    Toast.makeText(getApplicationContext(), loginStr, Toast.LENGTH_SHORT).show();
                }
                else{
                    //세번째 페이지로 이동
                    Intent intentThird = new Intent(getApplicationContext(), ThirdActivity.class);
                    startActivity(intentThird);
                }
            }
        });

        joinPageBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intentSecond = new Intent(getApplicationContext(), SecondActivity.class);
                startActivity(intentSecond);
            }
        });
    }

    private boolean hasTxt(EditText et){
        return (et.getText().toString().trim().length() > 0);
    }
}
