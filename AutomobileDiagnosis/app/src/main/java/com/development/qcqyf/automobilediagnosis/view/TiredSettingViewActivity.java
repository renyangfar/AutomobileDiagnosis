package com.development.qcqyf.automobilediagnosis.view;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.R;
import com.development.qcqyf.automobilediagnosis.SettingActivity;

public class TiredSettingViewActivity extends AppCompatActivity {

    private EditText et_tired;
    private SharedPreferences sp;
    private int time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setting_over_tired);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        et_tired = (EditText) findViewById(R.id.et_tired);

        sp = getSharedPreferences(SettingActivity.CONFIG, MODE_PRIVATE);

        int time_old = sp.getInt(SettingActivity.TIRED_LIMIT, 3);
        et_tired.setText(String.valueOf(time_old));

    }

    public void tired_ok(View v) {
        String get_tired = et_tired.getText().toString();
        try {
            time = Integer.parseInt(get_tired);
        } catch (Exception e) {
            Toast.makeText(getApplicationContext(), "请输入合法的数哦", 0).show();
            return;
        }
        Toast.makeText(getApplicationContext(),
                "设置的疲劳驾驶时间为： " + get_tired + "H", 0).show();
        Editor edit = sp.edit();
        edit.putInt(SettingActivity.TIRED_LIMIT, time);
        edit.putBoolean(SettingActivity.CB_TIRED, true);

        edit.commit();

        finish();
    }

}
