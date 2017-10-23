package com.development.qcqyf.automobilediagnosis;

import android.content.Intent;
import android.content.res.AssetManager;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Thread() {
            public void run() {
                SystemClock.sleep(1500);
                //跳转主页面
                toHomeActivity();
            }
        }.start();

        new Thread() {
            public void run() {

                // 拷贝数据库
                copyFile("faultcode.db");
                copyFile("carNum.json");
                System.out.println("拷贝完成了");
            }

            ;

        }.start();
    }

    /**
     * 跳转到主界面
     */
    public void toHomeActivity() {
        Intent intent = new Intent(getApplicationContext(),
                MainActivity.class);
        startActivity(intent);
        finish();
    }

    /**
     * 拷贝故障码数据库
     */
    private void copyFile(String dbname) {
        File file = new File(getFilesDir(), dbname);
        // 判断文件是否存在
        if (!file.exists()) {
            // 从assets目录中将数据库读取出来
            // 1.获取assets的管理者
            AssetManager am = getAssets();
            InputStream in = null;
            FileOutputStream out = null;
            try {
                // 2.读取数据库
                in = am.open(dbname);
                // 写入流
                // getCacheDir : 获取缓存的路径
                // getFilesDir : 获取文件的路径
                out = new FileOutputStream(file);
                // 3.读写操作
                // 设置缓冲区
                byte[] b = new byte[1024];
                int len = -1;
                while ((len = in.read(b)) != -1) {
                    out.write(b, 0, len);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                // in.close();
                // out.close();
                IOUtils.closeQuietly(in);
                IOUtils.closeQuietly(out);
            }
        }

    }
}
