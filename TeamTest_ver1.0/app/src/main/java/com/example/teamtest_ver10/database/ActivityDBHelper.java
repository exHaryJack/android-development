package com.example.teamtest_ver10.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.example.teamtest_ver10.bean.ActivityInfo;

import java.util.ArrayList;

public class ActivityDBHelper extends SQLiteOpenHelper {
    private static final String TAG="ActivityDBHelper()";
    private static final String DB_NAME="developer.db";
    private static final int DB_VERSION=1;

    private static ActivityDBHelper mHelper=null;

    private SQLiteDatabase mDB=null;

    private static final String DB_TABLE="ActivityDetails";

    private ActivityDBHelper(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    private ActivityDBHelper(Context context,int version){
        super(context,DB_NAME,null,version);
    }
    //单件类,创建唯一的数据库连接
    public static ActivityDBHelper getInstance(Context context,int version){
        if(version>0&&mHelper==null){
            mHelper=new ActivityDBHelper(context,version);
        }
        else if (mHelper==null){
            mHelper=new ActivityDBHelper(context);
        }
        return mHelper;
    }

    public SQLiteDatabase readLink() {
        if(mDB==null||mDB.isOpen()==false){
            mDB=mHelper.getReadableDatabase();
        }
        return mDB;
    }

    public SQLiteDatabase writeLink() {
        if(mDB==null||mDB.isOpen()==false){
            mDB=mHelper.getWritableDatabase();
        }
        return mDB;
    }

    public void closeDatabase(){
        if(mDB!=null&&mDB.isOpen()==true){
            mDB.close();
            mDB=null;
        }
    }

    public String getDBName() {
        if(mHelper!=null){
            return mHelper.getDBName();
        }
        else
            return DB_NAME;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.d(TAG,"onCreate");
        String drop_sql="DROP TABLE IF EXISTS "+DB_TABLE+";";
        Log.d(TAG,"drop_sql:"+drop_sql);
        db.execSQL(drop_sql);

        String create_sql="CREATE TABLE IF NOT EXISTS "+DB_TABLE+"("
                +"_id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,"
                +"title VARCHAR NOT NULL,"
                +"type VARCHAR NOT NULL,"
                +"date VARCHAR NOT NULL,"
                +"location VARCHAR NOT NULL,"
                +"participants INTEGER NOT NULL,"
                +"update_date VARCHAR NOT NULL,"
                +"detail VARCHAR"
                +",owner_name VARCHAR"
                +");";
        Log.d(TAG,"create_sql"+create_sql);
        db.execSQL(create_sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.d(TAG,"onUpgrade:oldVersion "+oldVersion+",newVersion "+newVersion);
        if(newVersion>1){
            String alter_sql="ALTER TABLE "+DB_TABLE+" ADD COLUMN "+"owner_name VARCHAR;";
            Log.d(TAG,"alter_sql:"+alter_sql);
            db.execSQL(alter_sql);

            alter_sql="ALTER TABLE "+DB_TABLE+" ADD COLUMN "+"password VARCHAR;";
            Log.d(TAG,"alter_sql:"+alter_sql);
            db.execSQL(alter_sql);
        }
    }

    public int delete(String condition){
        int count=mDB.delete(DB_TABLE,condition,null);
        return count;
    }

    public int deleteAll(){
        int count=mDB.delete(DB_TABLE,"1=1",null);
        return count;
    }

    public long insert(ActivityInfo activityInfo){
        ArrayList<ActivityInfo> activityInfoArrayList=new ArrayList<ActivityInfo>();
        activityInfoArrayList.add(activityInfo);
        return insert(activityInfoArrayList);
    }

    public long insert(ArrayList<ActivityInfo> activityInfoArrayList){
        long result=-1;
        for(int i=0;i<activityInfoArrayList.size();i++){
            ActivityInfo activityInfo=activityInfoArrayList.get(i);
            ArrayList<ActivityInfo> tempArray=new ArrayList<ActivityInfo>();
            //如果用户名相同，活动名字相同就启用更新
            //否则的话，认为是创建了新活动
//            if(activityInfo.owner_name!=null&&activityInfo.owner_name.length()>0&&activityInfo.title!=null&&activityInfo.title.length()>0){
//                String condition=String.format("title='%s' and owner_name='%s'",activityInfo.title,activityInfo.owner_name);
//                //查询操作
//                tempArray=query(condition);
//                if(tempArray.size()>0){
//                    update(activityInfo,condition);
//                    result=tempArray.get(0).row_id;
//                    continue;
//                }
//            }
//            //否则认为创建新活动
//            else{
//                ContentValues cv=new ContentValues();
//                cv.put("title",activityInfo.title);
//                cv.put("type",activityInfo.type);
//                cv.put("date",activityInfo.date);
//                cv.put("location",activityInfo.location);
//                cv.put("participants",activityInfo.participants);
//                cv.put("update_date",activityInfo.update_date);
//                cv.put("detail",activityInfo.detail);
//                cv.put("owner_name",activityInfo.owner_name);
//                result=mDB.insert(DB_TABLE,"",cv);
//                // 添加成功后返回行号，失败后返回-1
//                if(result==-1){
//                    return result;
//                }
//
//            }
            ContentValues cv=new ContentValues();
            cv.put("title",activityInfo.title);
            cv.put("type",activityInfo.type);
            cv.put("date",activityInfo.date);
            cv.put("location",activityInfo.location);
            cv.put("participants",activityInfo.participants);
            cv.put("update_date",activityInfo.update_date);
            cv.put("detail",activityInfo.detail);
            cv.put("owner_name",activityInfo.owner_name);
            result=mDB.insert(DB_TABLE,"",cv);
            if(result==-1){
                return result;
            }
        }
        return result;
    }

    public ArrayList<ActivityInfo> query(String condition){
        String sql;
        if(condition=="1=1"){
            sql=String.format("SELECT _id,title,type,date,location,participants,update_date,detail,owner_name from %s order by update_date desc;",DB_TABLE);
        }
        else {
            sql=String.format("select _id,title,type,date,location,participants,update_date,detail,"+
                    "owner_name from %s where %s order by update_date desc;",DB_TABLE,condition);
        }
        Log.d(TAG, "query sql: "+sql);
        ArrayList<ActivityInfo> activityInfoArrayList=new ArrayList<ActivityInfo>();
        Cursor cursor=mDB.rawQuery(sql,null);
//        if(cursor.moveToFirst()){
//            while(cursor.moveToNext()!=false){
//                ActivityInfo activityInfo=new ActivityInfo();
//                activityInfo.row_id=cursor.getInt(0);
//                activityInfo.title=cursor.getString(1);
//                activityInfo.type=cursor.getString(2);
//                activityInfo.date=cursor.getString(3);//需要做改动
//                activityInfo.location=cursor.getString(4);
//                activityInfo.participants=cursor.getInt(5);
//                activityInfo.update_date=cursor.getString(6);
//                activityInfo.detail=cursor.getString(7);
//                activityInfo.owner_name=cursor.getString(8);
//                activityInfoArrayList.add(activityInfo);
//                i++;
//                if(cursor.isLast()==true){
//                    break;
//                }
//            }
//            Log.v("i","i:"+i);
//            System.out.print(i);
//        }

        if (cursor.moveToFirst()) {
            for (;; cursor.moveToNext()) {
                ActivityInfo activityInfo=new ActivityInfo();
                activityInfo.row_id=cursor.getInt(0);
                activityInfo.title=cursor.getString(1);
                activityInfo.type=cursor.getString(2);
                activityInfo.date=cursor.getString(3);//需要做改动
                activityInfo.location=cursor.getString(4);
                activityInfo.participants=cursor.getInt(5);
                activityInfo.update_date=cursor.getString(6);
                activityInfo.detail=cursor.getString(7);
                activityInfo.owner_name=cursor.getString(8);
                activityInfoArrayList.add(activityInfo);
                if (cursor.isLast() == true) {
                    break;
                }
            }
        }
        cursor.close();
        return activityInfoArrayList;
    }

    public int update(ActivityInfo activityInfo,String condition){
        ContentValues cv=new ContentValues();
        cv.put("title",activityInfo.title);
        cv.put("type",activityInfo.type);
        cv.put("date",activityInfo.date);
        cv.put("location",activityInfo.location);
        cv.put("participants",activityInfo.participants);
        cv.put("update_date",activityInfo.update_date);
        cv.put("detail",activityInfo.detail);
        cv.put("owner_name",activityInfo.owner_name);
        int count=mDB.update(DB_TABLE,cv,condition,null);
        return count;
    }
}
