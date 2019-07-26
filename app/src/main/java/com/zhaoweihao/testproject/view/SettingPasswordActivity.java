package com.zhaoweihao.testproject.view;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.zhaoweihao.testproject.R;

/**
 * 设置密码页面
 * @author zhaoweihao
 */
public class SettingPasswordActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "SettingPasswordActivity";

    //密码编辑框
    private EditText etPassword;

    //确定按钮
    private Button btnSure;

    //相册密码
    private String albumPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_password);

        initViews();

        btnSure.setOnClickListener(this);

    }

    //初始化组件
    private void initViews() {
        etPassword = findViewById(R.id.et_password);
        btnSure = findViewById(R.id.btn_sure);
    }

    /**
     * 检测密码是否吻合
     * @param passwordInSp 在SP的密码
     * @param passwordUser 用户输入的密码
     */
    private void passwordMatch(String passwordInSp, String passwordUser) {
        boolean isSamePassword = passwordInSp.trim().equals(passwordUser.trim());
        if (isSamePassword) {
            Intent intent = new Intent(SettingPasswordActivity.this, AlbumActivity.class);
            startActivity(intent);
        } else {
            //密码错误需要提醒用户密码错误
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_sure: {
                albumPassword = etPassword.getText().toString().trim();

                SharedPreferences sharedPreferences = getSharedPreferences("password", MODE_PRIVATE);
                String password = sharedPreferences.getString("password", "");
                //检测是否存在密码
                boolean hasPassword = !password.trim().isEmpty();
                if (hasPassword) {
                    //如果有密码检测是否吻合
                    passwordMatch(password, albumPassword);
                    return;
                }
                //如果没有密码则将密码存起来
                //存储密码
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putString("password", albumPassword);
                editor.apply();

                startActivity(new Intent(SettingPasswordActivity.this, AlbumActivity.class));

            }
            break;
            default:
                break;
        }
    }
}
