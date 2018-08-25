package com.example.teamtest_ver10;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.Toolbar;

public class TabThirdActivity_offline extends Activity {
    android.support.v7.widget.Toolbar toolbar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_third_offline);
        toolbar=findViewById(R.id.toolbar2);
        toolbar.setTitle("我的资料");
    }
}
