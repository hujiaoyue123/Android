package com.boxin.beautypine.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.boxin.beautypine.R;
import com.boxin.beautypine.utils.LogUtils;

/**
 * 登录
 * User: zouyu
 * Date: :2017/10/18 0018
 * Version: 1.0
 */

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private EditText userName;
    private EditText password;
    private CheckBox remember;
    private Button login;
    private Button traveler;
    private Button register;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        remember = (CheckBox) findViewById(R.id.login_remember);
        userName = (EditText) findViewById(R.id.login_user_name);
        password = (EditText) findViewById(R.id.login_user_password);
        login = (Button) findViewById(R.id.login_button);
        traveler = (Button) findViewById(R.id.traveler_button);
        register = (Button) findViewById(R.id.register_button);

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_button:
                LogUtils.d("login_button");
                break;
            case R.id.register_button:
                LogUtils.d("register_button");
                break;
            case R.id.traveler_button:
                LogUtils.d("traveler_button");
                break;
        }
    }
}
