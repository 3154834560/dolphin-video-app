package com.example.dolphin.activity.tools.notepad;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.dolphin.R;

/**
 * @author 王景阳
 * @date 2022/11/24 19:45
 */
public class NotePadActivity extends AppCompatActivity {
    private ImageButton btn_add;
    private TextView data;
    private ImageButton btn_select;
    private EditText view_select;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notepad_page);
        data = findViewById(R.id.data);
        btn_select = findViewById(R.id.btn_select);
        view_select = findViewById(R.id.view_select);



        //跳转
        btn_add = (ImageButton) findViewById(R.id.btn_add);
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setClass(NotePadActivity.this, Add.class);
                startActivity(intent);
            }
        });


        /*
         * 页面传递值
         * */
        Intent i = getIntent();
        String get_data = i.getStringExtra("data");
        data.setText(get_data);


        //查询监听
        btn_select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = view_select.getText().toString().trim();
                String get = data.getText().toString().trim();

                //判断查询信息输入是否为空，并返回值
                if (!input.isEmpty()) {
                    int a = get.indexOf(input); //内容第一次出现的位置

                    //判断查询内容是否存在
                    if ( a != -1 ){

                        String front = get.substring(0,a+1);    //查询内容字段的位置的前一段数据截取
                        String after = get.substring(a+1,get.length()); //查询内容字段的位置的的后一段数据截取

                        int a1 = front.lastIndexOf("2022"); //前一段数据最后一次出现2022的位置
                        int b1 = after.indexOf("2022"); //后一段数据第一次出现2022的位置


                        String front2 ;//传值位
                        String after2 ;//传值位

                        //防止查询数据为第一位，出现错误
                        if (a1 == -1) {
                            front2 = front;
                        }else{
                            String front1 = front.substring(a1, front.length());
                            front2 = front1;
                        }

                        //防止查询数据为最后一位,出现错误
                        if (b1 == -1){
                            after2 = after;
                        }else {
                            String after1 = after.substring(0,b1);
                            after2 = after1;
                        }

                        showMsg(front2 + after2 );//显示查询结果信息

                    }else {
                        showMsg("无匹配数据");
                    }

                }else{
                    showMsg("请输入查询的数据");
                }
            }

        });

    }

    //显示提示消息
    private void showMsg(String msg){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show();
    }
}

