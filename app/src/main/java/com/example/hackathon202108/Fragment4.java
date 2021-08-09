package com.example.hackathon202108;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

public class Fragment4 extends Fragment {

    ViewGroup rootView;
    InfoDBOpenHelper dbOpenHelper;
    Button saveButton;
    EditText editTextName;
    EditText editTextStartHour;
    EditText editTextStartMin;
    EditText editTextEndHour;
    EditText editTextEndMin;
    Context context;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        rootView = (ViewGroup) inflater.inflate(R.layout.fragment4,container,false);
        initUI(rootView);
        context = container.getContext();

        return rootView;
    }
    private void initUI(ViewGroup rootView){
        saveButton = rootView.findViewById(R.id.button);
        editTextName = rootView.findViewById(R.id.editTextNickName);
        editTextStartHour = rootView.findViewById(R.id.editTextStartH);
        editTextStartMin = rootView.findViewById(R.id.editTextStartM);
        editTextEndHour = rootView.findViewById(R.id.editTextEndH);
        editTextEndMin = rootView.findViewById(R.id.editTextEndM);


        saveButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                String name = editTextName.getText().toString().trim();
                int startH = Integer.parseInt(editTextStartHour.getText().toString().trim());
                int startMin = Integer.parseInt(editTextStartMin.getText().toString().trim());
                int endH = Integer.parseInt(editTextEndHour.getText().toString().trim());
                int endMin = Integer.parseInt(editTextEndMin.getText().toString().trim());

                if((startH>=0&&startH<=23)&&(startMin>=0&&startMin<=59)&&(endH>=0&&endH<=23)&&(endMin>=0&&endMin<=59)&&(!name.isEmpty())){
                    try{
                        saveInfoData(name,startH*100+startMin,endH*100+endMin);
                        Toast.makeText(context, "저장 완료!",Toast.LENGTH_LONG).show();
                    }
                    catch(Exception e){
                        Toast.makeText(context, "데이터 저장 실패, 다시 시도하세요.",Toast.LENGTH_LONG).show();
                    }
                }
                else if(name.isEmpty())
                    Toast.makeText(context, "닉네임을 입력해주세요.",Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(context, "시는 0~23 사이의 정수, 분은 0~59 사이의 정수로 입력해야 합니다.",Toast.LENGTH_LONG).show();
            }
        });
    }

    public void saveInfoData(String name, int startTime, int endTime){
        dbOpenHelper = new InfoDBOpenHelper(context);
        dbOpenHelper.open();
        dbOpenHelper.create();

        try{
            dbOpenHelper.deleteAllColumns();
            dbOpenHelper.insertColumn(name,startTime,endTime);
        }
        catch(Exception e){
            Toast.makeText(context, "업데이트 실패!",Toast.LENGTH_LONG).show();
        }
    }
}
