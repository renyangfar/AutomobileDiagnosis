package com.development.qcqyf.automobilediagnosis;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.http.MyHttpUtil;
import com.development.qcqyf.automobilediagnosis.http.ATCommand;
import com.development.qcqyf.automobilediagnosis.tools.FaultCodeQuery;

import java.util.HashMap;

public class CarHeathActivity extends MyHttpUtil {
    private int num;
    private String[] faultCodeArrays;
    private TextView tv_faultcode_list;
    private ListView lv_car_heath;
    private TextView tv_title_fault;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_car_heath_fault);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tv_faultcode_list = (TextView) findViewById(R.id.tv_faultcode_list);
        lv_car_heath = (ListView) findViewById(R.id.lv_car_heath);
        tv_title_fault = (TextView) findViewById(R.id.tv_title_fault);

        // 获取汽车故障码
        HashMap<String, String> params = new HashMap<>();
        params.put(ATCommand.COMMAND, ATCommand.AT_DTC);
        sendCommandToWeb(params);
        progressBarDisplay(this);
    }

    @Override
    public void getBadResponse(String response) {
        finish();
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
        }
    }

    /**
     * 提取数据
     */
    private void readDataFromReceive(String faultCode) {
        try {
            // 判断是否正确接收
            if (faultCode.contains("RDTC")) {
                // 获取故障码个数
                num = faultCode.charAt(5) - 48;
                if (num!=0)
                {
                    System.out.println("故障码个数： " + num);

                    faultCodeArrays = new String[num];
                    // 根据故障码个数提取出故障码
                    for (int i = 0; i < num; i++) {
                        faultCodeArrays[i] = faultCode.substring(8 + i * 6,
                                8 + i * 6 + 5);
                        System.out.println(faultCodeArrays[i]);
                    }

                    // 显示
                    tv_title_fault.setText("扫描到的故障码：");
                    String faultCodeString = String.valueOf(num) + "   ";

                    for (int i = 0; i < faultCodeArrays.length; i++) {
                        faultCodeString += faultCodeArrays[i] + ";";
                    }
                    tv_faultcode_list.setText(faultCodeString);

                    lv_car_heath.setAdapter(new Myadapter());
                }
                else {
                    Toast.makeText(getApplicationContext(), "汽车无故障码", Toast.LENGTH_SHORT).show();
                    finish();
                }

            } else {
                System.out.println("发送错误了");
                // 重新发送
            }

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println("出错啦");
            Toast.makeText(getApplicationContext(), "获取错误", Toast.LENGTH_SHORT).show();
            finish();

        } finally {

        }

    }


    private class Myadapter extends BaseAdapter {

        @Override
        public int getCount() {
            // TODO Auto-generated method stub
            return num;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TODO Auto-generated method stub
            View view = View.inflate(getApplicationContext(),
                    R.layout.item_carheath_faultcode, null);
            TextView carheath_fault_code = (TextView) view
                    .findViewById(R.id.carheath_fault_code);
            TextView carheath_fault_defination = (TextView) view
                    .findViewById(R.id.carheath_fault_defination);
            TextView carheath_fault_des = (TextView) view
                    .findViewById(R.id.carheath_fault_des);

            // 获取故障码信息
            String faultCodeInfarmation = FaultCodeQuery.queryFaultCode(
                    faultCodeArrays[position], getApplicationContext());
            if (!TextUtils.isEmpty(faultCodeInfarmation)) {
                // 显示
                carheath_fault_defination.setText(faultCodeInfarmation
                        .subSequence(0, faultCodeInfarmation.indexOf("#")));
                carheath_fault_des.setText(faultCodeInfarmation
                        .substring(faultCodeInfarmation.indexOf("#") + 1));
                carheath_fault_code.setText(faultCodeArrays[position]);

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

}
