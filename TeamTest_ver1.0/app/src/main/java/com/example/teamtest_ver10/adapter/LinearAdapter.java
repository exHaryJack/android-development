package com.example.teamtest_ver10.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;

import com.example.teamtest_ver10.ActivityDetail;
import com.example.teamtest_ver10.R;
import com.example.teamtest_ver10.bean.Allactivity;
import com.example.teamtest_ver10.widget.RecyclerExtras.OnItemClickListener;
import com.example.teamtest_ver10.widget.RecyclerExtras.OnItemLongClickListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Jackson on 2018/7/15.
 */

public class LinearAdapter extends RecyclerView.Adapter<ViewHolder>
        implements OnItemClickListener,OnItemLongClickListener{
    Context mContext;
    LayoutInflater m_layout_Inflater;
    ArrayList<Allactivity> m_all_activity_ArrayList;
    public LinearAdapter(Context context,ArrayList<Allactivity> all_activity_ArrayList){
        mContext=context;
        m_all_activity_ArrayList=all_activity_ArrayList;
        m_layout_Inflater=LayoutInflater.from(context);
    }

    @Override
    public void onItemClick(View view, int position) {
        Bundle bundle=new Bundle();
        bundle.putInt("position",position);
        Intent intent=new Intent(mContext, ActivityDetail.class).putExtras(bundle);
        mContext.startActivity(intent);
    }

    @Override
    public void onItemLongClick(View view, int position) {

    }

    public class ItemHolder extends RecyclerView.ViewHolder {//这是内部类
        public LinearLayout ll_item;
        public ImageView iv_pic;
        public TextView tv_title;
        public TextView tv_update_date;

        public ItemHolder(View itemView) {//ll_item是单个项目视图的设置
            super(itemView);
            ll_item=itemView.findViewById(R.id.ll_item);
            iv_pic=itemView.findViewById(R.id.iv_pic);
            tv_title=itemView.findViewById(R.id.tv_title);
            tv_update_date=itemView.findViewById(R.id.tv_desc);
        }
    }

    @Override//必须重写
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v=m_layout_Inflater.inflate(R.layout.item_linear,parent,false);

        //这里的做法和书上是不一样的
        RecyclerView.ViewHolder viewHolder=new ItemHolder(v);
        return viewHolder;
    }

    @Override//必须重写
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        ItemHolder itemHolder=(ItemHolder)holder;
        //字符串转日期对象
        String update_date=m_all_activity_ArrayList.get(position).update_date;
        String format_1="yyyyMMddHHmmss";
        SimpleDateFormat sdf_1=new SimpleDateFormat(format_1);
        Date date = null;
        try {
            date=sdf_1.parse(update_date);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        //日期对象转字符串
        String format_2="于yyyy年MM月dd日 HH时mm分 更新";
        SimpleDateFormat sdf_2=new SimpleDateFormat(format_2);
        update_date=sdf_2.format(date);


        itemHolder.iv_pic.setImageResource(m_all_activity_ArrayList.get(position).pic_id);
        itemHolder.tv_title.setText(m_all_activity_ArrayList.get(position).title);
        itemHolder.tv_update_date.setText(update_date);

        itemHolder.ll_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener!=null){
                    onItemClickListener.onItemClick(v,position);
                }
            }
        });

        itemHolder.ll_item.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                return false;
            }
        });
    }

    @Override//必须重写
    public int getItemCount() {
        return m_all_activity_ArrayList.size();
    }

    private OnItemClickListener onItemClickListener;
    public void setOnItemClickListener(OnItemClickListener listener){
        onItemClickListener=listener;
    }

    private OnItemLongClickListener onItemLongClickListener;
    public void setOnItemLongClickListener(OnItemLongClickListener listener) {
        onItemLongClickListener = listener;
    }



}
