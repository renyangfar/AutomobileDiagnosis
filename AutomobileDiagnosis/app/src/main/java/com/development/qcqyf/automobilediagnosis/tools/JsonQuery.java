package com.development.qcqyf.automobilediagnosis.tools;

import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

import static com.development.qcqyf.automobilediagnosis.R.id.tv_home_location;

/**
 * Created by qcqyf on 2017/5/10.
 */

public class JsonQuery extends AppCompatActivity {

    public String querryHomeLocation() {
        try {
            // 1、获取数据库路径
            File file = new File(getApplicationContext().getFilesDir(),
                    "carNum.json");
            BufferedInputStream bi = new BufferedInputStream(
                    new FileInputStream(file));
            byte[] bt = new byte[1024];
            int len;
            StringBuffer jsonStr = new StringBuffer();
            while ((len = bi.read(bt)) != -1) {
                jsonStr.append(new String(bt, 0, len));

            }
            return jsonStr.toString();

        } catch (IOException e) {
            // TODO: handle exception
            System.out.println("carNum文件出错啦。。。");
        }
        return "";

    }

    public String parseJSONWithGSON(int what,String code) {

        boolean isFind = false;
        Gson gson = new Gson();
        String jsonData = querryHomeLocation();

        List<HomeLocation> homeList = gson.fromJson(jsonData,
                new TypeToken<List<HomeLocation>>() {
                }.getType());

        for (HomeLocation homeLocation : homeList) {
            //System.out.println("the city is " + homeLocation.getCity());
            if (what == 0) {
                //查询归属地
                if (homeLocation.getCode().equals(code)) {
                    // 查找到了
                    String location = homeLocation.getProvince() + " "
                            + homeLocation.getCity();
                    return location;
                }
            } else if (what == 1) {
                //查询简称
                if (homeLocation.getProvince().equals(code)) {
                    // 查找到了
                    String provincCode = homeLocation.getCode().substring(0,1);
                    return provincCode;
                }
            }

        }
        Toast.makeText(getApplicationContext(),
                "对不起，未能查询到对应的信息", Toast.LENGTH_SHORT).show();
        return "";
    }
}
