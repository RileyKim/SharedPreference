package com.taeksukim.android.sharedpreference;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Switch;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {

    // 내부 저장소 절대경로 가져오기 (/data/data/패키지명/files)
    String internalStorePath;
    String propertyFile = "test.properties";

    EditText editName;
    Switch switchShuffle;

    PropertyUtil propertyUtil;

    RelativeLayout layout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        propertyUtil = propertyUtil.getInstance(this);

        internalStorePath = getFilesDir().getAbsolutePath();

        editName = (EditText) findViewById(R.id.editName);
        switchShuffle = (Switch) findViewById(R.id.switchShuffle);
        layout = (RelativeLayout) findViewById(R.id.layout2);

        // firstOpen 체크가 되어 있으면 도움말 레이아웃을 달아준다.
        if("false".equals(propertyUtil.getProperty("firstOpen"))){
            layout.setVisibility(View.GONE);
        }

    }

    public void saveSetting(View view){

    }

    public void closeHelp(View view){
        layout.setVisibility(View.GONE);
        propertyUtil.saveProperty("firstOpen", "false");
    }


    public void saveProperty(String key, String value){
        Properties prop = new Properties();
        prop.put(key, value);


        try {
            //앱의 내부 저장소/ files/test.properties 파일을 저장
            FileOutputStream fos = new FileOutputStream(internalStorePath + "/" + propertyFile);

            prop.store(fos, "코멘트테스트");//firstOpen= false

            // 저장 후 파일을 닫아준다.
            fos.close();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public String getProperty(String key){
        String value = "";

        Properties prop = new Properties();

        try {
            FileInputStream fis = new FileInputStream(internalStorePath + "/" + propertyFile);
            prop.load(fis);
            fis.close();
        }catch (Exception e){
            e.printStackTrace();
        }

        prop.list(System.out);// 프로퍼티 목록 전체 나열하기

        value = prop.getProperty(key);
        return value;
    }
}
