package com.example.hackathon202108;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.devadvance.circularseekbar.CircularSeekBar;
import com.devadvance.circularseekbar.CircularSeekBar.OnCircularSeekBarChangeListener;
import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;

public class Fragment1 extends Fragment {

    ViewGroup rootView;
    InfoDBOpenHelper dbHelper;
    DateDBOpenHelper dateDBHelper;
    Cursor cursor;
    Context context;
    TextView userText;
    TextView scoreText;
    TextView todayOrYester;
    TextView courageText;
    String todayDate;
    String yesterdayDate;
    CircularSeekBar seekbar;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment1,container,false);
        initUI(rootView);
        context = container.getContext();
        showName();
        calculateScore();

        return rootView;
    }
    private void initUI(ViewGroup rootView) {
        seekbar = (CircularSeekBar) rootView.findViewById(R.id.circularSeekBar1);
        scoreText = (TextView) rootView.findViewById(R.id.textView7);
        todayOrYester = (TextView) rootView.findViewById(R.id.textView8);
        courageText =(TextView) rootView.findViewById(R.id.textView9);
        seekbar.setIsTouchEnabled(false);
        seekbar.getProgress();
        calculateScore();
        userText = (TextView) rootView.findViewById(R.id.textView);

    }

    private void showName(){
        if(context!=null) {
            dbHelper = new InfoDBOpenHelper(context);
            dbHelper.open();
            dbHelper.create();
            try {
                cursor = dbHelper.selectColumns();
                cursor.moveToLast();
                String userName = cursor.getString((int)cursor.getColumnIndex(DataBases.createInfoDB.NAME));
                userText.setText(userName + "???,");
            } catch (Exception e) {
                userText.setText("00" + "???,");
                Toast.makeText(context, "?????? ????????? ????????? ???????????????!", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void calculateScore(){
        if(context!=null) {
            Calendar c = Calendar.getInstance(Locale.KOREA);
            int check=0;
            int selDay = c.get(c.DATE);
            int selMon = c.get(c.MONTH)+1;
            int selYear = c.get(c.YEAR);
            todayDate=selYear+" "+selMon+" "+selDay;
            yesterdayDate=selYear+" "+selMon+" "+(selDay-1);

            dbHelper = new InfoDBOpenHelper(context);
            dbHelper.open();
            dbHelper.create();
            dateDBHelper = new DateDBOpenHelper(context);
            dateDBHelper.open();
            dateDBHelper.create();
            try {
                cursor = dbHelper.selectColumns();
                cursor.moveToLast();
                int goalMin;
                int realMin;
                int percent=0;
                int startSetTimeH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createInfoDB.STARTTIME))/100;
                int startSetTimeMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createInfoDB.STARTTIME))%100;
                int endSetTimeH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createInfoDB.ENDTIME))/100;
                int endSetTimeMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createInfoDB.ENDTIME))%100;

                cursor =dateDBHelper.selectColumns();
                while(cursor.moveToNext()){
                    String date = cursor.getString((int)cursor.getColumnIndex(DataBases.createDateDB.DATE));
                    int sH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.STARTBEDTIME))/100;
                    int sMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.STARTBEDTIME))%100;
                    int eH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.ENDBEDTIME))/100;
                    int eMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.ENDBEDTIME))%100;
                    if(date.equals(todayDate)){
                        goalMin = calBTime(startSetTimeH,startSetTimeMin,endSetTimeH,endSetTimeMin);
                        if(comTime(startSetTimeH,startSetTimeMin,sH,sMin)==1){
                            if(comTime(endSetTimeH,endSetTimeMin,eH,eMin)==1){
                                realMin =calBTime(sH,sMin,endSetTimeH,endSetTimeMin)-calBTime(endSetTimeH,endSetTimeMin,eH,eMin);
                            }
                            else{
                                realMin =calBTime(sH,sMin,eH,eMin);
                            }
                        }
                        else{
                            if(comTime(endSetTimeH,endSetTimeMin,eH,eMin)==1){
                                realMin = calBTime(startSetTimeH,startSetTimeMin,endSetTimeH,endSetTimeMin)-calBTime(endSetTimeH,endSetTimeMin,eH,eMin);
                            }
                            else{
                                realMin = calBTime(startSetTimeH,startSetTimeMin,eH,eMin);
                            }
                        }
                        percent = (int)((double)realMin/goalMin*100);
                        check=1;
                        todayOrYester.setText("????????? ?????? ??????");
                        break;
                    }
                    else if(date.equals(yesterdayDate)){
                        goalMin = calBTime(startSetTimeH,startSetTimeMin,endSetTimeH,endSetTimeMin);
                        if(comTime(startSetTimeH,startSetTimeMin,sH,sMin)==1){
                            if(comTime(endSetTimeH,endSetTimeMin,eH,eMin)==1){
                                realMin =calBTime(sH,sMin,endSetTimeH,endSetTimeMin);
                            }
                            else{
                                realMin =calBTime(sH,sMin,eH,eMin);
                            }
                        }
                        else{
                            if(comTime(endSetTimeH,endSetTimeMin,eH,eMin)==1){
                                realMin = calBTime(startSetTimeH,startSetTimeMin,endSetTimeH,endSetTimeMin);
                            }
                            else{
                                realMin = calBTime(startSetTimeH,startSetTimeMin,eH,eMin);
                            }
                        }
                        percent = (int)((double)realMin/goalMin*100);
                        check=1;
                        todayOrYester.setText("????????? ?????? ??????");
                        break;
                    }
                }
                if(check==1){
                    seekbar.getProgress();
                    seekbar.setProgress(percent);
                    scoreText.setText(percent+"???");
                    setScoreEncourageText(percent);
                }
                else{
                    seekbar.getProgress();
                    seekbar.setProgress(percent);
                    scoreText.setText(percent+"???");
                    setScoreEncourageText(percent);
                    Toast.makeText(context, "????????? ????????????.", Toast.LENGTH_LONG).show();
                }
            } catch (Exception e) {
                Toast.makeText(context, "??????: ?????? ??? ?????? ???????????????.", Toast.LENGTH_LONG).show();
            }
        }
    }

    private int comTime(int x1, int x2, int y1, int y2) {
        if(x1<y1||(x1==y1&&x2<y2)){
            return 1;
        }
        else{
            return 0;
        }
    }

    private int calBTime(int x1,int x2, int y1, int y2){
        int h;
        int m;
        if(y1<x1||(x1==y1&&y2<x2)){
            h=23-x1+y1;
            m=60-x2+y2;
        }
        else{
            h=y1-x1;
            m=y2-x1;
        }
        return h*60+m;
    }

    private void setScoreEncourageText(int x){
        String text;
        if(x>=80){
            text="?????? ?????? ????????? ???????????? ???????????????!";
        }
        else if(x>=60){
            text="????????? ????????? ????????????! ???????????? ?????? ????????? ??????????????????.";
        }
        else if(x>=40){
            text="?????? ??? ???????????? ?????? ??????????????? ???????????? ??????????????????.";
        }
        else{
            text="?????? ????????? ????????? ??? ????????? ?????? ??? ???????????? ?????? ?????????????";
        }

        courageText.setText(text);
    }


}

