package com.development.qcqyf.automobilediagnosis.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.tools.LoginActivity;
import com.development.qcqyf.automobilediagnosis.tools.Utils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public abstract class MyHttpUtil extends AppCompatActivity {

    public ProgressDialog pd = null;
    public boolean serverBackFlag = false;
    //    public static String address = "http://120.77.38.19:8080/CarDiagnoseServer/PhoneServer";
    public static String address = "http://120.77.38.19:8080/CarDiagnoseServer/PhoneServer";


    public static void sendOkHttpRequest(final String address, final RequestBody requestBody, final okhttp3.Callback callback) {
        OkHttpClient client = new OkHttpClient();
        //post数据
        Request request = new Request.Builder()
                // 指定访问的服务器地址是电脑本机
                .url(address)
                .post(requestBody)
                .build();
        client.newCall(request).enqueue(callback);
    }

    public void sendCommandToWeb(HashMap<String, String> params) {
        serverBackFlag = false;
        FormBody.Builder builder = new FormBody.Builder();
        boolean isHaveStyle = false;
        // Add Params to Builder
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().contains(ATCommand.STYLE))
                isHaveStyle = true;
            builder.add(entry.getKey(), entry.getValue());
        }
        if (!isHaveStyle) {
            builder.add(ATCommand.STYLE, ATCommand.COMMAND);
        }

        // Create RequestBody
        RequestBody formBody = builder.build();

        MyHttpUtil.sendOkHttpRequest(address, formBody, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                //这里进行异常处理
                Log.e("qcqyf", "errorResponse!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");

                ErrorExcute(e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.e("qcqyf", "onResponse!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!");
                String res = response.body().string();
                UIExcute(res);

            }
        });

    }

    public abstract void getResponse(String response);

    public void ErrorExcute(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                //取消加载界面
                if (pd != null)
                    pd.cancel();
                Toast.makeText(getApplicationContext(), "请求超时啦", Toast.LENGTH_LONG).show();
                getBadResponse(response);
                serverBackFlag = true;
            }
        });
    }

    public abstract void getBadResponse(String response);

    public void UIExcute(final String response) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 在这里进行UI操作，将结果显示到界面上
                //responseText.setText(response);
                //取消加载界面
                if (pd != null)
                    pd.cancel();
                getResponse(response);
                serverBackFlag = true;
            }
        });
    }

    public void progressBarDisplay(Context context) {
        pd = new ProgressDialog(context);
        pd.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pd.setTitle("提示");
        pd.setMessage("正在获取中，请稍后。。。");
        pd.setCancelable(false);
        //pd.setIcon(R.drawable.spinner);
        pd.setIndeterminate(true);
        pd.show();
    }
}

