package com.example.teamtest_ver10;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TabHost;

public class Main_TabActivity_Tabhost extends TabActivity implements View.OnClickListener {
    private Bundle mBundle=new Bundle();
    private TabHost tab_Host;
    private FrameLayout frameLayout;
    private LinearLayout ll_first,ll_second,ll_third;
    private String FIRST_TAG="first";
    private String SECOND_TAG="second";
    private String THIRD_TAG="third";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);//好像都是用的父类的
        setContentView(R.layout.activity_main_tabhost);
        tab_Host=getTabHost();
        //mBundle.putString("tag","Come from Tabhost");
        frameLayout=(FrameLayout)findViewById(android.R.id.tabcontent);
        ll_first=(LinearLayout)findViewById(R.id.ll_first);
        ll_second=(LinearLayout)findViewById(R.id.ll_second);
        ll_third=(LinearLayout)findViewById(R.id.ll_third);

        ll_first.setOnClickListener(this);
        ll_second.setOnClickListener(this);
        ll_third.setOnClickListener(this);

        tab_Host.addTab(setNewTab(FIRST_TAG,R.string.menu_first,TabFirstActivity.class));
        tab_Host.addTab(setNewTab(SECOND_TAG,R.string.menu_second,TabSecondActivity.class));
        tab_Host.addTab(setNewTab(THIRD_TAG,R.string.menu_third,TabThirdActivity.class));

        tab_Host.setCurrentTabByTag(FIRST_TAG);
        changeContainerView(ll_first);
    }

    private TabHost.TabSpec setNewTab(String TAG,int lable,Class<?> cls){
        Intent intent=new Intent(this,cls).putExtras(mBundle);
        return tab_Host.newTabSpec(TAG).setContent(intent).setIndicator(getString(lable));

    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.ll_first||v.getId()==R.id.ll_second||v.getId()==R.id.ll_third){
            changeContainerView(v);
        }
    }

    private void changeContainerView(View v){
        ll_first.setSelected(false);
        ll_second.setSelected(false);
        ll_third.setSelected(false);
        v.setSelected(true);
        if(v==ll_first){
            toStartActivity("第一个视图",TabFirstActivity.class);
        }
        else if(v==ll_second){
//            mBundle.putString("第二个activity","第二个activity");
//            startActivity(new Intent(this,ReleaseActivity.class).putExtras(mBundle));
            toStartActivity("第二个视图",ReleaseActivity.class);
        }
        else if(v==ll_third){
            toStartActivity("第三个视图测试",TabThirdActivity_offline.class);
        }
    }

    private void toStartActivity(String string_1,Class<?> cla){
        mBundle.putString("视图1",string_1);
        Intent intent=new Intent(this,cla).putExtras(mBundle);
        View v = getLocalActivityManager().startActivity(string_1,intent).getDecorView();//固定搭配
        v.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        frameLayout.removeAllViews();
        frameLayout.addView(v);//用addView去把别的视图加入，但是加入前要先remove
    }
}
