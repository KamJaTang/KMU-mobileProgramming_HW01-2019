package com.example.hw01;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import android.widget.RadioButton;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

public class SecondActivity extends Activity {
    TextView joinText;
    EditText id, password, name, hp, adress;
    RadioButton rbtn;
    Boolean isError;
    String errorStr;
    String string1 = "1. 개인정보의 수집항목\n" +
            "예술경영지원센터는 홈페이지 회원 가입 시 회원 서비스 제공에 필요한 최소한의 정보를 수집하고 있으며 개인정보파일에 수집되는 항목은 다음과 같습니다.\n" +
            "<회원가입>\n" +
            "- 이름, 아이디, 연락처, 이메일, 주소\n" +
            "\n" +
            "2. 개인정보 수집목적\n" +
            "(재)예술경영지원센터가 제공하는 맞춤화된 서비스(예술경영 컨텐츠 제공, 각종 서비스 안내 및 참가신청 등) 및 개발(사전조사 및 만족도 설문조사, 고객문의 등)을 위해 수집합니다. (재)예술경영지원센터는 원칙적으로 이용자의 개인정보를 수집 및 이용 목적범위 내에서 처리하며, 이용자의 사전 동의 없이는 본래의 범위를 초과하여 처리하거나 제3자에게 제공하지 않습니다.\n" +
            "\n" +
            "가. 회원관리\n" +
            "회원 맞춤 서비스 제공, 개인식별, 전체 서비스의 원활한 운영을 위한 관리, 회원탈퇴 의사 확인\n" +
            "\n" +
            "나. 고유서비스 이용 및 신규 개발\n" +
            "예술경영지원센터에서 제공하는 컨설팅을 비롯한 각종 고유 서비스 제공의 필요 시, 신규 서비스 개발 시 의견수렴 및 안내\n" +
            "\n" +
            "3. 개인정보의 보유기간\n" +
            "- 홈페이지 회원가입에 따라 수집된 개인정보 보유기간은 2년입니다.\n" +
            "\n" +
            "4. 기타사항(거부할 권리 등)\n" +
            "- 회원가입에 따른 개인정보의 수집, 이용, 제공에 대해 귀하께서 동의하신 내용은 언제든지 철회 또는 거부하실 수 있습니다.";

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.second);
        setTitle("회원가입");


        id = (EditText) findViewById(R.id.id);
        password = (EditText) findViewById(R.id.password);
        name = (EditText) findViewById(R.id.name);
        hp = (EditText) findViewById(R.id.hp);
        adress = (EditText) findViewById(R.id.adress);

        rbtn = (RadioButton) findViewById(R.id.rbtn);

        joinText = (TextView) findViewById(R.id.textBox);

        Button joinBtn = (Button) findViewById(R.id.joinBtn);

        joinText.setText(string1);
        joinText.setMovementMethod(new ScrollingMovementMethod());


        joinBtn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                errorStr = "[회원가입을 할 수 없습니다.]";
                isError = false;

                if (hasTxt(id) && hasTxt(password) && hasTxt(name) && hasTxt(hp) && hasTxt(adress)) {
                    if (!chkID(id)) {
                        //[수정] 아이디 중복 체크
                        errorStr += "\n이미 사용중인 아이디 입니다.";
                        isError = true;
                    }
                    if (!chkPW(password)) {
                        errorStr += "\n비밀번호가 양식에 맞지 않습니다.";
                        isError = true;
                    }
                }
                else {
                        isError = true;
                        errorStr += "\n모든 항목을 채워주세요.";
                    }

                if(!rbtn.isChecked()){
                    isError = true;
                    errorStr += "\n개인정보수집에 동의가 필요합니다";
                }

                if(isError){
                    Toast.makeText(getApplicationContext(), errorStr, Toast.LENGTH_SHORT).show();
                }
                else{
                    //회원가입 성공

                    //file에 정보 저장
                    try{
                        FileOutputStream fos = null;
                        String fn = chnStr(id)+".txt";
                        fos = openFileOutput(fn, Context.MODE_PRIVATE);
                        PrintWriter writer = new PrintWriter(fos);
                        writer.println(chnStr(password));
                        writer.close();
                        Toast.makeText(getApplicationContext(), "회원가입이 완료 되었습니다.", Toast.LENGTH_SHORT).show();

                    }catch(FileNotFoundException e){
                        e.printStackTrace();
                    }catch(IOException e){
                        e.printStackTrace();;
                    }

                    finish();

                }

            }
        });
    }

    private boolean chkID(EditText et){
        String nId = chnStr(et) + ".txt";
        try {
            openFileInput(nId);
        } catch(Exception e){
            e.printStackTrace();
            return true;
        }
        return false;
    }

    private String chnStr(EditText et){ return (et.getText().toString());}

    private boolean hasTxt(EditText et){
        return (et.getText().toString().trim().length() > 0);
    }

    private boolean chkPW(EditText et){
        String str = et.getText().toString();
        char[] chr = str.toCharArray();
        int len = str.trim().length();
        int num = 0;
        int eng = 0;

        if(len >= 6){
            for(int i=0; i<len; i++){
                if (chr[i] >= 48 && chr[i] <= 57) num++;
                else if((chr[i]>=97 && chr[i]<=122)||(chr[i]>=65&&chr[i]<=90)) eng++;
            }
            if(num>0 && eng>0) return true;
        }

        return false;
    }
}
