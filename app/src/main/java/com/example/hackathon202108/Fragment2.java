package com.example.hackathon202108;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.applikeysolutions.cosmocalendar.dialog.CalendarDialog;
import com.applikeysolutions.cosmocalendar.dialog.OnDaysSelectionListener;
import com.applikeysolutions.cosmocalendar.listeners.OnMonthChangeListener;
import com.applikeysolutions.cosmocalendar.model.Day;
import com.applikeysolutions.cosmocalendar.model.Month;
import com.applikeysolutions.cosmocalendar.selection.OnDaySelectedListener;
import com.applikeysolutions.cosmocalendar.selection.RangeSelectionManager;
import com.applikeysolutions.cosmocalendar.selection.SingleSelectionManager;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteria;
import com.applikeysolutions.cosmocalendar.settings.lists.DisabledDaysCriteriaType;
import com.applikeysolutions.cosmocalendar.view.CalendarView;
import com.google.android.material.slider.LabelFormatter;
import com.google.android.material.slider.RangeSlider;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class Fragment2 extends Fragment {
    CalendarView calendarView;
    Context context;
    DateDBOpenHelper dbHelper;
    Cursor cursor;
    String todayDate;
    EditText editTextStartHour2;
    EditText editTextStartMin2;
    EditText editTextEndHour2;
    EditText editTextEndMin2;
    Button saveButton;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        ViewGroup rootView = (ViewGroup) inflater.inflate(R.layout.fragment2,container,false);
        initUI(rootView);
        context = container.getContext();
        setTodaySelected();

        return rootView;
    }
    private void initUI(ViewGroup rootView){
        calendarView = (CalendarView) rootView.findViewById(R.id.calendar_view);
        editTextStartHour2 = (EditText) rootView.findViewById(R.id.editTextStartH2);
        editTextStartMin2 = (EditText) rootView.findViewById(R.id.editTextStartM2);
        editTextEndHour2 = (EditText) rootView.findViewById(R.id.editTextEndH2);
        editTextEndMin2 = (EditText) rootView.findViewById(R.id.editTextEndM2);
        saveButton = (Button) rootView.findViewById(R.id.button2);

        setDisableDates_m();

        calendarView.setOnMonthChangeListener(new OnMonthChangeListener() {
            @Override
            public void onMonthChanged(Month month) {
                setDisableDates(month);
            }
        });

        calendarView.setSelectionManager(new SingleSelectionManager(new OnDaySelectedListener() {
            @Override
            public void onDaySelected() {
                List<Calendar> selectedDay = calendarView.getSelectedDates();
                Calendar c = selectedDay.get(0);
                int check=0;
                int selDay = c.get(c.DATE);
                int selMon = c.get(c.MONTH)+1;
                int selYear = c.get(c.YEAR);
                todayDate=selYear+" "+selMon+" "+selDay;

                if(context!=null) {
                    dbHelper = new DateDBOpenHelper(context);
                    dbHelper.open();
                    dbHelper.create();
                    cursor = dbHelper.selectColumns();
                    try {
                        while(cursor.moveToNext()){
                            String date = cursor.getString((int)cursor.getColumnIndex(DataBases.createDateDB.DATE));
                            int sH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.STARTBEDTIME))/100;
                            int sMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.STARTBEDTIME))%100;
                            int eH = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.ENDBEDTIME))/100;
                            int eMin = cursor.getInt((int)cursor.getColumnIndex(DataBases.createDateDB.ENDBEDTIME))%100;
                            if(date.equals(todayDate)){
                                editTextStartHour2.setText(Integer.toString(sH));
                                editTextStartMin2.setText(Integer.toString(sMin));
                                editTextEndHour2.setText(Integer.toString(eH));
                                editTextEndMin2.setText(Integer.toString(eMin));
                                check=1;
                            }
                        }
                    } catch (Exception e) {
                        Toast.makeText(context, "오류가 발생하였습니다.", Toast.LENGTH_LONG).show();
                    }
                    if(check==0){
                        editTextStartHour2.setText("");
                        editTextStartMin2.setText("");
                        editTextEndHour2.setText("");
                        editTextEndMin2.setText("");
                    }
                }
            }
        }));


        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int startH = Integer.parseInt(editTextStartHour2.getText().toString().trim());
                int startMin = Integer.parseInt(editTextStartMin2.getText().toString().trim());
                int endH = Integer.parseInt(editTextEndHour2.getText().toString().trim());
                int endMin = Integer.parseInt(editTextEndMin2.getText().toString().trim());

                if((startH>=0&&startH<=23)&&(startMin>=0&&startMin<=59)&&(endH>=0&&endH<=23)&&(endMin>=0&&endMin<=59)&&(!todayDate.isEmpty())){
                    try{
                        saveDateData(todayDate,startH*100+startMin,endH*100+endMin);
                        Toast.makeText(context, "저장 완료!",Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e){
                        Toast.makeText(context, "데이터 저장 실패, 다시 시도하세요.",Toast.LENGTH_LONG).show();
                    }
                }
                else if(!todayDate.isEmpty())
                    Toast.makeText(context, "시는 0~23 사이의 정수, 분은 0~59 사이의 정수로 입력해야 합니다.",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "시는 0~23 사이의 정수, 분은 0~59 사이의 정수로 입력해야 합니다.",Toast.LENGTH_LONG).show();
            }
        });

    }

    public void saveDateData(String date, int startTime, int endTime){
        int check=0;
        dbHelper = new DateDBOpenHelper(context);
        dbHelper.open();
        dbHelper.create();
        cursor = dbHelper.selectColumns();
        try {
            while (cursor.moveToNext()) {
                String dat = cursor.getString((int) cursor.getColumnIndex(DataBases.createDateDB.DATE));
                if (dat.equals(date)) {
                    check=1;
                    dbHelper.updateColumn(cursor.getLong((int) cursor.getColumnIndex("_id")), date, startTime, endTime);
                }
            }
        }
        catch(Exception e){
            Toast.makeText(context, "업데이트 실패!",Toast.LENGTH_LONG).show();
        }
        if(check==0){
            dbHelper.insertColumn(date, startTime, endTime);
        }
    }

    private void setTodaySelected(){
        calendarView.clearSelections();
        Date today = new Date(System.currentTimeMillis());
        Day day = new Day(today);
        calendarView.getSelectionManager().toggleDay(day);
    }

    private void setDisableDates(Month month){
        Calendar cal = Calendar.getInstance(Locale.KOREA);
        String m = month.getMonthName();
        String calMonS = m.substring(0,m.length()-4);
        calMonS=calMonS.trim();
        int calMon=0;
        int calYear = Integer.parseInt(m.substring(m.length()-4));
        int curMon = cal.get(cal.MONTH)+1;
        int curYear = cal.get(cal.YEAR);
        if(calMonS.equals("January"))   calMon=1;
        else if(calMonS.equals("February"))   calMon=2;
        else if(calMonS.equals("March"))   calMon=3;
        else if(calMonS.equals("April"))   calMon=4;
        else if(calMonS.equals("May"))   calMon=5;
        else if(calMonS.equals("June"))   calMon=6;
        else if(calMonS.equals("July"))   calMon=7;
        else if(calMonS.equals("August"))   calMon=8;
        else if(calMonS.equals("September"))   calMon=9;
        else if(calMonS.equals("October"))   calMon=10;
        else if(calMonS.equals("November"))   calMon=11;
        else if(calMonS.equals("December"))   calMon=12;

        if(calYear>calYear){
            DisabledDaysCriteria creteria = new DisabledDaysCriteria(Calendar.SUNDAY, Calendar.SATURDAY, DisabledDaysCriteriaType.DAYS_OF_WEEK);
            calendarView.setDisabledDaysCriteria(creteria);
        }
        else if(calYear==curYear&&calMon>curMon){
            DisabledDaysCriteria creteria = new DisabledDaysCriteria(Calendar.SUNDAY, Calendar.SATURDAY, DisabledDaysCriteriaType.DAYS_OF_WEEK);
            calendarView.setDisabledDaysCriteria(creteria);
        }
        else if(calYear==curYear&&calMon==curMon){
            setDisableDates_m();
        }
    }

    private void setDisableDates_m(){
        Set<Long> disabledDaysSet = new HashSet<>();
        long tomorrow_long=System.currentTimeMillis()+24*60*60*1000;
        disabledDaysSet.add(tomorrow_long);
        Date today = new Date(tomorrow_long);
        Calendar cal = Calendar.getInstance();
        cal.setTime(today);
        GregorianCalendar grecal = new GregorianCalendar(cal.YEAR,cal.MONTH-1,cal.DATE);
        int lastDay = grecal.getActualMaximum(Calendar.DAY_OF_MONTH);
        for(int i=cal.DATE;i<lastDay-1;i++){
            tomorrow_long+=+24*60*60*1000;
            disabledDaysSet.add(tomorrow_long);
        }
        calendarView.setDisabledDays(disabledDaysSet);
    }

}
