package com.example.dolphin.activity.tools.notepad;

import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王景阳
 * @date 2022/11/24 19:46
 */
public class Add extends AppCompatActivity {

    private ImageButton btn_back; //返回按钮
    private ImageButton btn_finish;//完成按钮
    private ImageButton btn_clear;//清楚所有数据按钮


    private TextView now_time;//当前时间
    private EditText data_information;//存储数据




    //与数据库操作相关的成员变量
    private MyDbHelper myDbHelper;//数据库帮助类
    private SQLiteDatabase db;//数据库类
    private ContentValues values;//数据表的一些参数
    private static final String mTablename = "mymemo";//数据表的名称



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_add_page);

        //建立联系
        btn_back = findViewById(R.id.btn_back);
        data_information = findViewById(R.id.data_information);
        now_time = findViewById(R.id.now_time);
        btn_finish = findViewById(R.id.btn_finish);


        btn_clear = findViewById(R.id.btn_clear);


        //初始化数据库工具类实例
        myDbHelper  = new MyDbHelper(this);


        //获取手机当前时间
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date(System.currentTimeMillis());
        final String str_time = simpleDateFormat.format(date);
        now_time.setText(str_time);


        //返回主界面
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(Add.this,NotePadActivity.class);
                startActivity(intent);
                queryData();

            }
        });



        //完成并执行添加操作
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = data_information.getText().toString().trim();
                if (input.isEmpty()){
                    showMsg("请输入要记录的内容");
                }else {
                    addData(str_time.trim(),input);
                    finish();
                    queryData();
                }
            }
        });

        btn_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearData();
            }
        });

    }


    //添加数据方法
    private void addData(String now_time,String data_information){
        db = myDbHelper.getWritableDatabase();
        values = new ContentValues();
        values.put("now_time",now_time);
        values.put("information",data_information);
        db.insert(mTablename,null,values);
        showMsg("添加成功");

    }

    //清空所有数据的方法
    private void clearData(){
        showMsgDialog();
    }

    //显示消息方法
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }


    //查询方法
    private void queryData(){
        db = myDbHelper.getReadableDatabase();
        Cursor cursor = db.query(mTablename,null,null,null,
                null,null,null);
        String srtResult = "";
        while (cursor.moveToNext()){
            srtResult += "\n" + cursor.getString(1);
            srtResult += "\n内容：" + cursor.getString(2);
            srtResult += "\n";
        }
        Intent intent = new Intent(Add.this,NotePadActivity.class);
        intent.putExtra("data",srtResult);
        startActivity(intent);
    }
    /*
     *
     *
     * 自定义数据库帮助类，sqllite部分
     *
     * */
    class MyDbHelper extends SQLiteOpenHelper {

        public MyDbHelper(@Nullable Context context) {
            super(context, "mymemo.db", null, 2);
        }

        //只在创建数据库时，创建一次这个表
        @Override
        public void onCreate(SQLiteDatabase db) {
            db.execSQL("create table " + mTablename + "(_id integer primary key autoincrement, " +
                    "now_time varchar(50) unique,information varchar(100) )");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        }
    }
    //显示提示消息对话框
    private void showMsgDialog() {
        //创建AlertDialog构造器Builder对象，AlertDialog建议使用android.support.v7.app包下的。
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //设置对话框标题
        builder.setTitle("提示");
        //设置提示信息
        builder.setMessage("是否确定删除所有备忘信息？");
        //设置对话框图标
        builder.setIcon(R.drawable.kunkun);
        //添加确定按钮
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //添加确定按钮点击的处理代码
                db = myDbHelper.getWritableDatabase();
                db.delete(mTablename,null,null);
                String srtResult = "";
                Intent intent = new Intent(Add.this,NotePadActivity.class);
                intent.putExtra("data",srtResult);
                startActivity(intent);
                showMsg("已经全部清空");
            }
        });
        //添加取消按钮
        builder.setNegativeButton("取消",null);
        //创建并显示对话框
        builder.show();
    }
}
