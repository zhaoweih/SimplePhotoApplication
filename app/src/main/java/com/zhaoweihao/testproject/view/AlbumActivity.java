package com.zhaoweihao.testproject.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.zhaoweihao.testproject.R;
import com.zhaoweihao.testproject.adapter.PhotoViewAdapter;
import com.zhaoweihao.testproject.utils.PhotoUtil;

import java.util.List;


/**
 * 相册页面
 *
 * @author zhaoweihao
 */
public class AlbumActivity extends AppCompatActivity {

    private static final String TAG = "AlbumActivity";

    //相册列表
    private RecyclerView albumRecyclerview;

    //相册数据
    private List<String> albumDataList;

    private PhotoViewAdapter photoViewAdapter;

    //权限申请吗
    private static final int permissionRequestCode = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_album);
        initViews();
        //设定水平3格布局
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        albumRecyclerview.setLayoutManager(gridLayoutManager);

        //初始化adapter
        photoViewAdapter = new PhotoViewAdapter(albumRecyclerview);
        //设置adapter到recyclerview
        albumRecyclerview.setAdapter(photoViewAdapter);

        //是否有权限
        boolean hasPermission = ContextCompat.checkSelfPermission(AlbumActivity.this,
                Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        Log.d(TAG, "hasPermissio" + hasPermission);
        if (hasPermission) {
            //有权限将数据填充到RecyclewView里
            albumDataList = PhotoUtil.getSystemPhotoList(this);
            photoViewAdapter.setData(albumDataList);

        } else {
            //请求权限
            ActivityCompat.requestPermissions(AlbumActivity.this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                    permissionRequestCode);
        }
    }

    //初始化组件
    private void initViews() {
        albumRecyclerview = findViewById(R.id.rv_album);

    }


    /**
     * 权限回调
     *
     * @param requestCode  请求码
     * @param permissions  权限
     * @param grantResults 获取权限结果
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case permissionRequestCode: {
                boolean hasGrantPermission = grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED;
                Log.d(TAG, "hasGrantPermission" + hasGrantPermission);
                if (hasGrantPermission) {

                    albumDataList = PhotoUtil.getSystemPhotoList(this);
                    photoViewAdapter.setData(albumDataList);
                } else {
                    //没有获取权限提醒用户需要赋予权限,并解析权限的用处
                }
            }
        }
    }

//    @Override
//    protected void onPause() {
//        super.onPause();
//        finish();
//    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }



}
