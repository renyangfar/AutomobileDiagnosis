package com.development.qcqyf.automobilediagnosis;

import android.content.Intent;
import android.os.Bundle;
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

public class MonitorHistoryActivity extends MyHttpUtil {
    private ListView lv_monitor_history;
    private String readMessage;

    // 行为习惯数据
    private String MAXRPM;
    private String MINRPM;
    private String MAXSPD;
    private String AVGSPD;
    private String MAXACL;
    private String MILE_T;
    private String FUEL_T;
    private String MILES;
    private String FUELS;
    private String TIMES;
    private String STARTS;
    private String BRAKES;
    private String RACLS;
    private String POWER;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monitor_history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // 行为习惯数据流初始化
        lv_monitor_history = (ListView) findViewById(R.id.lv_monitor_history);
        // 联网获取当前车辆参数
        HashMap<String, String> params = new HashMap<>();
        params.put(ATCommand.COMMAND, ATCommand.AT_DRON);
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
            lv_monitor_history.setAdapter(new MyAdapterHistory());
        }
    }

    private void readDataFromReceive(String receiver) {
        try {


            if (receiver.contains("$OBD-DR$")) {

                MAXRPM = receiver.substring(receiver.indexOf("MAXRPM:") + 7,
                        receiver.indexOf("MINRPM:") - 1);
                MINRPM = receiver.substring(receiver.indexOf("MINRPM:") + 7,
                        receiver.indexOf("MAXSPD:") - 1);
                MAXSPD = receiver.substring(receiver.indexOf("MAXSPD:") + 7,
                        receiver.indexOf("AVGSPD:") - 1);
                AVGSPD = receiver.substring(receiver.indexOf("AVGSPD:") + 7,
                        receiver.indexOf("MAXACL:") - 1);
                MAXACL = receiver.substring(receiver.indexOf("MAXACL:") + 7,
                        receiver.indexOf("MILE-T:") - 1);
                MILE_T = receiver.substring(receiver.indexOf("MILE-T:") + 7,
                        receiver.indexOf("FUEL-T:") - 1);
                FUEL_T = receiver.substring(receiver.indexOf("FUEL-T:") + 7,
                        receiver.indexOf("MILES:") - 1);
                MILES = receiver.substring(receiver.indexOf("MILES:") + 6,
                        receiver.indexOf("FUELS:") - 1);
                FUELS = receiver.substring(receiver.indexOf("FUELS:") + 6,
                        receiver.indexOf("TIMES:") - 1);
                TIMES = receiver.substring(receiver.indexOf("TIMES:") + 6,
                        receiver.indexOf("STARTS:") - 1);
                STARTS = receiver.substring(receiver.indexOf("STARTS:") + 7,
                        receiver.indexOf("BRAKES:") - 1);
                BRAKES = receiver.substring(receiver.indexOf("BRAKES:") + 7,
                        receiver.indexOf("RACLS:") - 1);
                RACLS = receiver.substring(receiver.indexOf("RACLS:") + 6,
                        receiver.indexOf("POWER:") - 1);
                POWER = receiver.substring(receiver.indexOf("POWER:") + 6,
                        receiver.length());
            }

        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "获取错误", Toast.LENGTH_SHORT).show();
            System.out.println("获取错误");
            finish();
        }
    }


    /**
     * 习惯数据流
     *
     * @author LarryLong
     */
    public class MyAdapterHistory extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return 14;
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
                    tv_monitor_name.setText("最大发动机转速");
                    tv_monitor_value.setText(MAXRPM + "rpm");
                    break;
                case 1:
                    tv_monitor_name.setText("最小发动机转速");
                    tv_monitor_value.setText(MINRPM + "rpm");
                    break;
                case 2:
                    tv_monitor_name.setText("最大车速");
                    tv_monitor_value.setText(MAXSPD + "km/h");
                    break;
                case 3:
                    tv_monitor_name.setText("平均车速");
                    tv_monitor_value.setText(AVGSPD + "km/h");
                    break;
                case 4:
                    tv_monitor_name.setText("最大加速度");
                    tv_monitor_value.setText(MAXACL + "km/h");
                    break;
                case 5:
                    tv_monitor_name.setText("此次里程");
                    tv_monitor_value.setText(MILE_T + "km/h");
                    break;
                case 6:
                    tv_monitor_name.setText("此次油耗");
                    tv_monitor_value.setText(FUEL_T + "L/h");
                    break;
                case 7:
                    tv_monitor_name.setText("累计总里程");
                    tv_monitor_value.setText(MILES + "km");
                    break;
                case 8:
                    tv_monitor_name.setText("累计总油耗");
                    tv_monitor_value.setText(FUELS + "L");
                    break;
                case 9:
                    tv_monitor_name.setText("行车时间");
                    tv_monitor_value.setText(TIMES + "S");
                    break;
                case 10:
                    tv_monitor_name.setText("点火启动次数");
                    tv_monitor_value.setText(STARTS + "次");
                    break;
                case 11:
                    tv_monitor_name.setText("刹车次数");
                    tv_monitor_value.setText(BRAKES + "次");
                    break;
                case 12:
                    tv_monitor_name.setText("急加速次数");
                    tv_monitor_value.setText(RACLS + "次");
                    break;
                case 13:
                    tv_monitor_name.setText("汽车当前运行状态");
                    tv_monitor_value.setText(POWER);
                    break;

                default:
                    break;
            }
            return view;
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

    }

    /**
     * 当前行驶数据
     *
     * @param v
     */
    public void turn_current(View v) {
        Intent intent = new Intent(getApplicationContext(),
                MonitorCurrentActivity.class);
        startActivity(intent);
        finish();
    }
    public void historyRefresh(View v) {
        Intent intent = new Intent(getApplicationContext(),
                MonitorHistoryActivity.class);
        startActivity(intent);
        finish();
    }

}
