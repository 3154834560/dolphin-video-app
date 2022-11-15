package com.example.dolphin.infrastructure.media;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author 王景阳
 * @date 2022/11/12 10:26
 */
public class CaptureClass {
    /**
     * 图片文件
     */
    private File mphotoFile;
    /**
     * 图片Uri
     */
    private Uri mphotoUri;

    private Context context;

    public CaptureClass(AppCompatActivity c_base) {
        context = c_base;
    }

    /**
     * 打开相机
     */
    public Uri openCamera() {
        //Action为多媒体库的拍照
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //未指定categories
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        //从相册中获取指定路径名称的空图片
        mphotoFile = getPhotoFileFromDcim();
        //空图片的uri
        Uri photouri = null;
        //根据Android版本不同，uri获取方式不同
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//Android api 大于 24 ， Android版本7.0
            //获得临时读取权限
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            photouri = FileProvider.getUriForFile(context,
                    "com.ilikexy.picturetest.fileprovider", mphotoFile);
        } else {//Android api 小于 24 ， Android版本小于7.0
            photouri = Uri.fromFile(mphotoFile);
        }//指定生成图片文件类型,将Uri通过intent传给相机活动，key名是绝对化固定的
        if (photouri != null) {
            mphotoUri = photouri;
            //Uri传入
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mphotoUri);
            //图片类型传入
            intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
            //回调跳转，指明唯一请求码
            ((AppCompatActivity) context).startActivityForResult(intent, 1);
            return mphotoUri;
        } else {
            Toast.makeText(context, "图片Uri生成失败！", Toast.LENGTH_SHORT).show();
            return null;
        }
    }

    /**
     * 获取相册路径，用于将拍照的图片插入相册
     */
    public File getPhotoFileFromDcim() {
        try {
            //获取相册路径
            String dcimpath = context.getExternalFilesDir(Environment.DIRECTORY_DCIM).getAbsolutePath();
            //图片名称
            String filename = ""+System.currentTimeMillis();
            //父类文件
            File fatherFile = new File(dcimpath);
            fatherFile.mkdirs();
            //生成子类文件
            File sonFile = File.createTempFile(filename, ".jpg", fatherFile);
            return sonFile;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    //-----------------------------------相册-------------------------------------
    //相册
    public void openDcim() {
        //动态申请本地存储读取权限
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions((AppCompatActivity) context, new String[]{
                    Manifest.permission.READ_EXTERNAL_STORAGE}, 11);
        } else {
            openDcimright();
        }
    }

    //打开相册
    public void openDcimright() {
        //打开系统相册， 未指定具体的uri, ACTION_PICK
        Intent intentToPickPic = new Intent(Intent.ACTION_PICK, null);
        intentToPickPic.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        ((AppCompatActivity) context).startActivityForResult(intentToPickPic, 2);
    }
    //对于相机或者本地相册图片结果处理需要在活动中实现

}


