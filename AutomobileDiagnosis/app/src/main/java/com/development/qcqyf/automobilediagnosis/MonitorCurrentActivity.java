package com.development.qcqyf.automobilediagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.http.ATCommand;
import com.development.qcqyf.automobilediagnosis.http.MyHttpUtil;

import java.util.HashMap;

/**
 * 行车记录
 *
 * @author LarryLong
 */

public class MonitorCurrentActivity extends MyHttpUtil {
    private ListView lv_monitor_now;
    private String readMessage;
    // 当前车辆参数
    private String vBAT;
    private String rPM;
    private String sPD;
    private String tP;
    private String lOD;
    private String eCT;
    private String fLI;
    private String mPH;

    /**
     * handler定时发送任务
     */
    private Handler handler_send = new Handler();
    private Runnable runnable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_current);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 当前车辆参数初始化
        lv_monitor_now = (ListView) findViewById(R.id.lv_monitor_now);


        // 联网获取当前车辆参数
        HashMap<String, String> params = new HashMap<>();
        params.put(ATCommand.COMMAND,ATCommand.AT_BDAT );
        sendCommandToWeb(params);
        progressBarDisplay(this);
    }

    @Override
    public void getBadResponse(String response) {
                //finish();
    }
    @Override
    public void getResponse(String response) {
        System.out.println("the http get data is :" + response);
        if (response.contains(ATCommand.GET_DATA)) {
            int start = response.indexOf(ATCommand.GET_DATA)
                    + ATCommand.GET_DATA.length();
            String value_data = response.substring(start);
            System.out.println("value_data: " + value_data);

            readDataFromReceive(value_data);
            Toast.makeText(getApplicationContext(),value_data,Toast.LENGTH_SHORT).show();
            lv_monitor_now.setAdapter(new MyAdapterNow());
        }

    }

    private void readDataFromReceive(String receiver) {
        if (receiver.contains("$OBD-BASE DR DAT$")) {
            try {
                vBAT = receiver.substring(receiver.indexOf("VBAT:") + 5,
                        receiver.indexOf("RPM:") - 1);
                rPM = receiver.substring(receiver.indexOf("RPM:") + 4,
                        receiver.indexOf("SPD:") - 1);
                sPD = receiver.substring(receiver.indexOf("SPD:") + 4,
                        receiver.indexOf("TP:") - 1);
                tP = receiver.substring(receiver.indexOf("TP:") + 3,
                        receiver.indexOf("LOD:") - 1);
                lOD = receiver.substring(receiver.indexOf("LOD:") + 4,
                        receiver.indexOf("ECT:") - 1);
                eCT = receiver.substring(receiver.indexOf("ECT:") + 4,
                        receiver.indexOf("FLI:") - 1);
                fLI = receiver.substring(receiver.indexOf("FLI:") + 4,
                        receiver.indexOf("MPH:") - 1);
                mPH = receiver.substring(receiver.indexOf("MPH:") + 4,
                        receiver.length());
                System.out.println(vBAT + rPM + sPD + tP + lOD + eCT + fLI + mPH);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "获取错误", Toast.LENGTH_SHORT).show();
                System.out.println("获取错误");
                finish();
            }


        }
    }


    /**
     * 当前参数界面
     *
     * @author LarryLong
     */
    public class MyAdapterNow extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 8;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // 将布局文件转化成view对象
            View view = View.inflate(getApplicationContext(),
                    R.layout.item_monitor, null);
            // 初始化控件
            TextView tv_monitor_name = (TextView) view
                    .findViewById(R.id.tv_monitor_name);
            TextView tv_monitor_value = (TextView) view
                    .findViewById(R.id.tv_monitor_value);
            switch (position) {
                case 0:
                    tv_monitor_name.setText("电瓶电压");
                    tv_monitor_value.setText(vBAT + "v");
                    break;
                case 1:
                    tv_monitor_name.setText("发动机转速");
                    tv_monitor_value.setText(rPM + "rpm");
                    break;
                case 2:
                    tv_monitor_name.setText("车速");
                    tv_monitor_value.setText(sPD + "km/h");
                    break;
                case 3:
                    tv_monitor_name.setText("汽节门开度");
                    tv_monitor_value.setText(tP + "%");
                    break;
                case 4:
                    tv_monitor_name.setText("发动机负荷");
                    tv_monitor_value.setText(lOD + "%");
                    break;
                case 5:
                    tv_monitor_name.setText("水温");
                    tv_monitor_value.setText(eCT + "C");
                    break;
                case 6:
                    tv_monitor_name.setText("剩余油量");
                    tv_monitor_value.setText(fLI + "%");
                    break;
                case 7:
                    tv_monitor_name.setText("顺时油耗");
                    tv_monitor_value.setText(mPH + "L/h");
                    break;

                default:
                    break;
            }
            return view;
        }

        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }

    }

    /**
     * 驾驶行为数据
     *
     * @param v
     */
    public void turn_history(View v) {
        Intent intent = new Intent(getApplicationContext(),
                MonitorHistoryActivity.class);
        startActivity(intent);
        finish();
    }

    public void currentRefresh(View v) {
        Intent intent = new Intent(getApplicationContext(),
               MonitorCurrentActivity.class );
        startActivity(intent);
        finish();
    }

}
