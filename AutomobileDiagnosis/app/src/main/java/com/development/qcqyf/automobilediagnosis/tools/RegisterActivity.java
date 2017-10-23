package com.development.qcqyf.automobilediagnosis.tools;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.baidu.mapapi.map.Text;
import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.http.ATCommand;
import com.development.qcqyf.automobilediagnosis.http.MyHttpUtil;

import java.util.HashMap;

public class RegisterActivity extends MyHttpUtil {

    private EditText register_carID;
    private EditText register_serial;
    private EditText register_password_again;
    private EditText register_password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        register_carID = (EditText) findViewById(R.id.register_carID);
        register_serial = (EditText) findViewById(R.id.register_serial);
        register_password = (EditText) findViewById(R.id.register_password);
        register_password_again = (EditText) findViewById(R.id.register_password_agin);
    }

    public void register(View view) {
        String carID = register_carID.getText().toString().trim();
        String password = register_password.getText().toString().trim();
        String password_agin = register_password_again.getText().toString().trim();
        String serial = register_serial.getText().toString().trim();
        if (TextUtils.isEmpty(carID)||TextUtils.isEmpty(password)||TextUtils.isEmpty(password_agin)||TextUtils.isEmpty(serial)){
            Toast.makeText(getApplicationContext(),"请填写全部字段", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.equals(password_agin)){
            Toast.makeText(getApplicationContext(),"两次输入密码不一致", Toast.LENGTH_SHORT).show();
            return;
        }
        // 获取汽车故障码
        HashMap<String, String> params = new HashMap<>();
        params.put(ATCommand.STYLE, ATCommand.REGISTER);
        params.put("carID", carID);
        params.put("password", password);
        sendCommandToWeb(params);
        progressBarDisplay(this);
    }

    @Override
    public void getResponse(String response) {
        if (response.contains("pass")){
            Toast.makeText(getApplicationContext(),"注册成功",Toast.LENGTH_SHORT).show();
            finish();
        }
        else {
            Toast.makeText(getApplicationContext(),"注册失败，请重试",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void getBadResponse(String response) {

    }
}
