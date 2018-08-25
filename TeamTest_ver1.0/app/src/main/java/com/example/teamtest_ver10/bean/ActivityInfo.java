package com.example.teamtest_ver10.bean;

public class ActivityInfo {
    public int row_id;
    public String title;
    public String type;
    public String date;
    public String duration;
    public String location;
    public int participants;
    public String detail;

    public String owner_name;
    public String update_date;

    public ActivityInfo(){
        row_id=01;
        title="";
        type="";
        date="";
        duration="0d";
        location="";
        participants=1;
        detail="";
        owner_name="";
        update_date="";
    }
}
