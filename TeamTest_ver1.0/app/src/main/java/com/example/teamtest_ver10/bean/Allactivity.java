package com.example.teamtest_ver10.bean;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.example.teamtest_ver10.R;
import com.example.teamtest_ver10.database.ActivityDBHelper;

import java.util.ArrayList;

/**
 * Created by Jackson on 2018/7/14.
 */
//负责把所有的活动从数据库里读取出来
@SuppressLint("Registered")
public class Allactivity{

    public static String[] type={"学术","体育","游戏","其他"};
    private static ActivityDBHelper activityDBHelper;



    static ArrayList<ActivityInfo> activityInfoArrayList;
    public int pic_id;
    public String title;
    public String update_date;
    public boolean bPressed;
    public int id;
    private static int seq = 0;


    public static ArrayList<ActivityInfo> getActivityInfoArrayList() {
        return activityInfoArrayList;
    }

    public Allactivity(int pic_id,String title,String update_date){
        this.pic_id=pic_id;
        this.title=title;
        this.update_date=update_date;
        this.bPressed=false;
    }

    private static int[] listImageArray = {R.drawable.icon_study48, R.drawable.icon_sports48
            , R.drawable.icon_game48, R.drawable.icon_others48};

    public static ArrayList<ActivityInfo> readSQLite(ActivityDBHelper external_activityDBHelper){
        activityDBHelper=external_activityDBHelper;
        //所有的活动对象都在这个ArrayList里面了
        activityInfoArrayList=activityDBHelper.query("1=1");
        int count=activityInfoArrayList.size();
        System.out.println(count);
        Log.v("count","count:"+count);
        return activityInfoArrayList;
    }

    public static ArrayList<Allactivity> getList(){
        ArrayList<Allactivity> listArray = new ArrayList<Allactivity>();
        for(int i=0;i<activityInfoArrayList.size();i++){
            if (activityInfoArrayList.get(i).type.toString().equals(type[0])==true){
                listArray.add(new Allactivity(listImageArray[0],
                        activityInfoArrayList.get(i).title.toString(),
                        activityInfoArrayList.get(i).update_date.toString()));
            }
            else if(activityInfoArrayList.get(i).type.toString().equals(type[1])==true){
                listArray.add(new Allactivity(listImageArray[1],
                        activityInfoArrayList.get(i).title.toString(),
                        activityInfoArrayList.get(i).update_date.toString()));
            }
            else if(activityInfoArrayList.get(i).type.toString().equals(type[2])==true){
                listArray.add(new Allactivity(listImageArray[2],
                        activityInfoArrayList.get(i).title.toString(),
                        activityInfoArrayList.get(i).update_date.toString()));
            }
            else{
                listArray.add(new Allactivity(listImageArray[3],
                        activityInfoArrayList.get(i).title.toString(),
                        activityInfoArrayList.get(i).update_date.toString()));
            }

        }
        return listArray;
    }

}
