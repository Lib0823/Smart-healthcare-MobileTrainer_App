package com.example.project2;

import androidx.annotation.IdRes;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

public class JoinActivity extends AppCompatActivity {

    EditText idEditText, pwEditText, nameEditText, ageEditText, heightEditText, weightEditText;
    Button btnJoin, btnBack;
    RadioGroup radioGender;
    String gender = "man", date;

    int version = 1;
    DatabaseOpenHelper helper, helperRecord;
    SQLiteDatabase database, databaseRecord;

    String sql;
    Cursor cursor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);

        idEditText = (EditText) findViewById(R.id.idEditText);
        pwEditText = (EditText) findViewById(R.id.pwEditText);
        nameEditText = (EditText) findViewById(R.id.nameEditText);
        ageEditText = (EditText) findViewById(R.id.ageEditText);
        heightEditText = (EditText) findViewById(R.id.heightEditText);
        weightEditText = (EditText) findViewById(R.id.weightEditText);
        radioGender = (RadioGroup) findViewById(R.id.radioGender);
        radioGender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, @IdRes int check) {
                if(check == R.id.man){
                    gender = "man";
                }
                if(check == R.id.woman){
                    gender = "woman";
                }
            }
        });

        btnJoin = (Button) findViewById(R.id.btnJoin);
        btnBack = (Button) findViewById(R.id.btnBack);

        helper = new DatabaseOpenHelper(JoinActivity.this, DatabaseOpenHelper.tableName, null, version);
        database = helper.getWritableDatabase();

        helperRecord = new DatabaseOpenHelper(JoinActivity.this, DatabaseOpenHelper.tableNameRecord, null, version);
        databaseRecord = helperRecord.getWritableDatabase();

        btnJoin.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {

                // ?????? ??? ????????? ??????????????? ????????? ???????????? ????????? ?????? ??????????????????

                if(idEditText.getText().length() == 0 || pwEditText.getText().length() == 0 || nameEditText.getText().length() == 0 ||
                        ageEditText.getText().length() == 0 || heightEditText.getText().length() == 0 || weightEditText.getText().length() == 0) {
                    //?????????, ????????????, ??????, ??????, ???, ???????????? ?????? ???????????? ?????????.
                    Toast toast = Toast.makeText(JoinActivity.this, "?????? ????????? ???????????? ?????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                if(idEditText.getText().length() < 5) {
                    //???????????? 5??? ?????? ???????????? ?????????.
                    Toast toast = Toast.makeText(JoinActivity.this, "???????????? 5??? ?????? ??????????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(pwEditText.getText().length() < 7) {
                    //??????????????? 7??? ?????? ???????????? ?????????.
                    Toast toast = Toast.makeText(JoinActivity.this, "??????????????? 7??? ?????? ??????????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                if(!pwEditText.getText().toString().contains("@") && !pwEditText.getText().toString().contains("#")
                && !pwEditText.getText().toString().contains("%") && !pwEditText.getText().toString().contains("$")) {
                    //??????????????? ??????????????? ???????????? ?????????.
                    Toast toast = Toast.makeText(JoinActivity.this, "??????????????? ??????????????? ???????????? ?????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }

                String id = idEditText.getText().toString();
                String pw = pwEditText.getText().toString();
                String name = nameEditText.getText().toString();
                int age = Integer.parseInt(ageEditText.getText().toString());
                int height = Integer.parseInt(heightEditText.getText().toString());
                int weight = Integer.parseInt(weightEditText.getText().toString());
                int run = 0;
                String login = "0";

                // ?????? ?????? ????????????
                long now = System.currentTimeMillis();
                Date date1 = new Date(now);
                SimpleDateFormat sdf = new SimpleDateFormat("yyy-MM-dd");
                date = sdf.format(date1);


                sql = "SELECT id FROM "+ helper.tableName + " WHERE id = '" + id + "'";
                cursor = database.rawQuery(sql, null);

                if(cursor.getCount() != 0){
                    //???????????? ??????????????????.
                    Toast toast = Toast.makeText(JoinActivity.this, "???????????? ??????????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                }else{
                    int time = 0;
                    double distance = 0.0;
                    int step = 0;
                    double kcal = 0.0;

                    // Record??? ??????
                    long now2 = System.currentTimeMillis();
                    Date date2 = new Date(now2);
                    SimpleDateFormat sdf2 = new SimpleDateFormat("MM/dd");
                    String date3 = sdf2.format(date2);
                    helperRecord.insertRecord(databaseRecord,id, date3, time, distance, step, kcal);

                    helper.insertUser(database,id, pw, name, age, height, weight, gender, run, login, date);
                    Toast toast = Toast.makeText(JoinActivity.this, "????????? ?????????????????????. ???????????? ????????????.", Toast.LENGTH_SHORT);
                    toast.show();
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //???????????? ??????
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}