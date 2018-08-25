package com.example.teamtest_ver10;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

import com.example.teamtest_ver10.bean.ActivityInfo;
import com.example.teamtest_ver10.bean.Allactivity;

import java.util.ArrayList;

public class ActivityDetail extends Activity {
    Intent intent=null;
    int position;
    TextView tv_title;
    TextView tv_date;
    TextView tv_duration;
    TextView tv_location;
    TextView tv_participants;
    TextView tv_detail;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        intent=getIntent();
        position=intent.getIntExtra("position",-1);

        tv_title=findViewById(R.id.ad_tv_title);
        tv_date=findViewById(R.id.ad_tv_date);
        tv_duration=findViewById(R.id.ad_tv_duration);
        tv_location=findViewById(R.id.ad_tv_location);
        tv_participants=findViewById(R.id.ad_tv_participants);
        tv_detail=findViewById(R.id.ad_tv_details);

        int check=setInformation(position);
        System.out.print(check);

    }

    private int setInformation(int position){
        if(position==-1){
            return -1;
        }
        else {
            ArrayList<ActivityInfo> activityInfoArrayList= Allactivity.getActivityInfoArrayList();
            ActivityInfo activityInfo=activityInfoArrayList.get(position);
            if(activityInfo==null){
                return -2;
            }
            String title=activityInfo.title;
            String date=activityInfo.date;
            String duration=activityInfo.duration;//在ActivityInfo里面有
            String location=activityInfo.location;
            String participants= String.valueOf(activityInfo.participants);
            String detail=activityInfo.detail;

            tv_title.setText(title);
            tv_date.setText(date);
            tv_duration.setText(duration);
            tv_location.setText(location);
            tv_participants.setText(participants);
            tv_detail.setText("\t"+detail);

            return 1;
        }
    }
}
