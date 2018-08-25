package com.example.teamtest_ver10;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.teamtest_ver10.bean.ActivityInfo;
import com.example.teamtest_ver10.bean.Owner;
import com.example.teamtest_ver10.bean.UserInfo;
import com.example.teamtest_ver10.database.ActivityDBHelper;
import com.example.teamtest_ver10.util.DateUtil;


public class ReleaseActivity extends Activity implements View.OnClickListener{

    private ActivityDBHelper activityDBHelper;
    private String[] activity_type={"学术","体育","游戏","其他"};
    EditText ET_title;
    Spinner sp_type;
    String string_type;
    EditText ET_date;
    EditText ET_location;
    EditText ET_participants;
    EditText ET_detail;
    Toolbar toolbar;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.release_activity);
        ET_title=findViewById(R.id.ra_et_title);
        sp_type=findViewById(R.id.ra_et_type);
        ET_date=findViewById(R.id.ra_et_date);
        ET_location=findViewById(R.id.ra_et_location);
        ET_participants=findViewById(R.id.ra_et_participants);
        ET_detail=findViewById(R.id.ra_et_detail);
        toolbar=findViewById(R.id.ra_toolbar);
        findViewById(R.id.ra_confirm).setOnClickListener(this);

        ArrayAdapter<String> typeAdapter=new ArrayAdapter<String>(this,R.layout.item_select,activity_type);
        typeAdapter.setDropDownViewResource(R.layout.item_dropdown);
        sp_type.setPrompt("选择活动类型");
        sp_type.setAdapter(typeAdapter);
        sp_type.setSelection(0);
        sp_type.setOnItemSelectedListener(new TypeSelectedListener());

        toolbar.setLogo(R.drawable.aui_icon_paper);
        toolbar.setTitle("发布");

    }

    private void showToast(String desc){
        Toast.makeText(this, desc, Toast.LENGTH_SHORT).show();
    }


    class TypeSelectedListener implements AdapterView.OnItemSelectedListener {

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            if(position==0){
                string_type=activity_type[0];
            }
            else if(position==1){
                string_type=activity_type[1];
            }
            else if(position==2){
                string_type=activity_type[2];
            }
            else{
                string_type=activity_type[3];
            }
        }

        public void onNothingSelected(AdapterView<?> arg0) {
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        activityDBHelper=ActivityDBHelper.getInstance(this,2);
        activityDBHelper.writeLink();
    }

    @Override
    protected void onStop() {
        super.onStop();
        activityDBHelper.closeDatabase();
    }

    @Override
    public void onClick(View v) {

        if(v.getId()==R.id.ra_confirm){
            String title=ET_title.getText().toString();
            String type=string_type;
            String date=ET_date.getText().toString();
            String location=ET_location.getText().toString();
            int participants;
            String detail=ET_detail.getText().toString();
            if(title==null||title.length()<=0){
                showToast("请输入标题");
                return;
            }
            if(date==null||date.length()<=0){
                showToast("请输入活动日期");
                return;
            }
            if(location==null||location.length()<=0){
                showToast("请输入活动地点");
                return;
            }
            if(ET_participants.getText().toString()==null|ET_participants.getText().toString().length()<=0){
                showToast("请输入活动参与人数");
                return;
            }
            else {
                participants= Integer.parseInt(ET_participants.getText().toString());
            }

            ActivityInfo activityInfo=new ActivityInfo();
            activityInfo.title=title;
            activityInfo.type=type;
            activityInfo.date=date;
            activityInfo.location=location;
            activityInfo.participants=participants;
            activityInfo.detail=detail;
            activityInfo.update_date= DateUtil.getNowDateTime(null);
            activityInfo.owner_name=new Owner("活动所属用户名").owner_name;

            long i=activityDBHelper.insert(activityInfo);
            if(i==-1){
                showToast("发布失败");
            }
            else{
                showToast("发布成功");
                ET_title.setText("");
                ET_date.setText("");
                ET_location.setText("");
                ET_participants.setText("");
                ET_detail.setText("");
            }
            return;
        }
    }
}
