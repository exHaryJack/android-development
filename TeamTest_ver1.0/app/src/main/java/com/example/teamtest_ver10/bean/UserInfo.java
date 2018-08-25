package com.example.teamtest_ver10.bean;

public class UserInfo {
    public String username;
    public String password;
    private static UserInfo user=null;
    private UserInfo(){

    }

    private UserInfo(String username,String password){
        this.username=username;
        this.password=password;
    }
    public static synchronized UserInfo getUser(String username,String password){
        if(user==null){
            user=new UserInfo(username,password);
        }
        return user;
    }
}
