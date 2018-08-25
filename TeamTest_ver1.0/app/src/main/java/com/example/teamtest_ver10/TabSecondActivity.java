package com.example.teamtest_ver10;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.TextView;

/**
 * Created by Jackson on 2018/5/9.
 */

public class TabSecondActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_second);

        TextView textView=findViewById(R.id.tv_tab_second);
    }
}
