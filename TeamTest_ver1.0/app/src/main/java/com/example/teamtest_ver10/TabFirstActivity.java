package com.example.teamtest_ver10;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.teamtest_ver10.adapter.LinearAdapter;
import com.example.teamtest_ver10.bean.ActivityInfo;
import com.example.teamtest_ver10.bean.Allactivity;
import com.example.teamtest_ver10.database.ActivityDBHelper;
import com.example.teamtest_ver10.widget.RecyclerExtras;
import com.example.teamtest_ver10.widget.SpacesItemDecoration;

import java.util.ArrayList;

/**
 * Created by Jackson on 2018/5/9.
 */

public class TabFirstActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener {
    private RecyclerView recyclerView;
    private android.support.v7.widget.Toolbar toolbar;
    private SwipeRefreshLayout swipeRefreshLayout;
    private ActivityDBHelper activityDBHelper;
    private LinearLayoutManager linearLayoutManager;
    LinearAdapter linearAdapter;
    Context context;
    ArrayList<ActivityInfo> activityInfoArrayList;
    ArrayList<Allactivity> allactivities;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tab_first);
        recyclerView=findViewById(R.id.rv_tab_first);
        toolbar=findViewById(R.id.toolbar_1);
        swipeRefreshLayout=findViewById(R.id.srl_tab_first);
        swipeRefreshLayout.setOnRefreshListener(this);
        toolbar.setTitle("首页");
        context=this;


        linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        activityDBHelper= ActivityDBHelper.getInstance(this,2);
        activityDBHelper.readLink();
        if(activityDBHelper==null){
            Toast.makeText(getApplicationContext(),"数据库连接失败",Toast.LENGTH_SHORT).show();
            return;
        }
        activityInfoArrayList=Allactivity.readSQLite(activityDBHelper);

        if(activityInfoArrayList==null||activityInfoArrayList.size()<=0){
            Toast.makeText(this,"没有活动信息",Toast.LENGTH_SHORT).show();
        }
        //设置监视器
        allactivities=Allactivity.getList();
        linearAdapter=new LinearAdapter(this, allactivities);
        linearAdapter.setOnItemClickListener(linearAdapter);
        linearAdapter.setOnItemLongClickListener(linearAdapter);
        recyclerView.setAdapter(linearAdapter);

        //项目动态动作
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        //这是设置各个项目视图间的分界线
        recyclerView.addItemDecoration(new SpacesItemDecoration(1));
    }

    @Override
    protected void onStart() {
        super.onStart();
    }


    @Override
    protected void onStop() {
        super.onStop();
        activityDBHelper.closeDatabase();
    }

    @Override
    public void onRefresh() {
        handler.postDelayed(runnable,2000);
    }

    private Handler handler=new Handler();
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
            swipeRefreshLayout.setRefreshing(false);
            if(activityDBHelper!=null){
                activityInfoArrayList=Allactivity.readSQLite(activityDBHelper);
            }
            else {
                activityDBHelper=ActivityDBHelper.getInstance(context,2);
                activityInfoArrayList=Allactivity.readSQLite(activityDBHelper);
            }

            allactivities.clear();
            allactivities.addAll(Allactivity.getList());
            linearAdapter.notifyDataSetChanged();
            recyclerView.scrollToPosition(0);
        }
    };
}
