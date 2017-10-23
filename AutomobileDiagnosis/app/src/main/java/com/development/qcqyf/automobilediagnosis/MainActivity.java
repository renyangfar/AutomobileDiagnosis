package com.development.qcqyf.automobilediagnosis;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.development.qcqyf.automobilediagnosis.tools.LoginActivity;
import com.development.qcqyf.automobilediagnosis.tools.LoveCarBookActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private GridView gv_home_gridview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        // 初始化gradview控件
        gv_home_gridview = (GridView) findViewById(R.id.gv_home_gridview);
        gv_home_gridview.setAdapter(new Myadapter());

        // gradview点击事件
        gv_home_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                switch (position) {
                    case 0:// 汽车体检
                        // 跳转到汽车体检页面
                        if (!LoginActivity.isLoginSuccess) {
                            Toast.makeText(getApplicationContext(), "请先登录哦", Toast.LENGTH_LONG).show();
                            break;
                        }
                        Intent intent0 = new Intent(getApplicationContext(),
                                CarHeathActivity.class);
                        startActivity(intent0);
                        break;
                    case 1:// 行车记录
                        // 跳转到行车记录页面
                        if (!LoginActivity.isLoginSuccess) {
                            Toast.makeText(getApplicationContext(), "请先登录哦", Toast.LENGTH_LONG).show();
                            break;
                        }
                        Intent intent1 = new Intent(getApplicationContext(),
                                MonitorCurrentActivity.class);
                        startActivity(intent1);
                        break;
                    case 2:// 周边服务
                        // 跳转到周边服务页面
                        Intent intent2 = new Intent(getApplicationContext(),
                                SurroundServiceActivity.class);
                        startActivity(intent2);
                        break;
                    case 3:// 辅助功能
                        // 跳转到辅助功能页面
                        Intent intent3 = new Intent(getApplicationContext(),
                                AssistActivity.class);
                        startActivity(intent3);
                        break;
                    case 4:// 设置中心
                        // 跳转到设置页面
                        Intent intent4 = new Intent(getApplicationContext(),
                                SettingActivity.class);
                        startActivity(intent4);
                        break;
                    default:
                        break;
                }

            }
        });
    }


    private class Myadapter extends BaseAdapter {
        int[] imageId = {R.drawable.ic_menu_clear_playlist,
                R.drawable.ic_menu_recent_history, R.drawable.ic_menu_compass,
                R.drawable.ic_menu_btn_add,
                R.drawable.ic_menu_settings_holo_light};
        String[] names = {"汽车体检", "行车记录", "周边服务", "辅助功能", "设置中心"};

        // 设置条目的个数
        @Override
        public int getCount() {
            return 5;
        }

        // 设置条目的样式
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // TextView textView = new TextView(getApplicationContext());
            // textView.setText("第"+position+"个条目");//position : 代表是条目的位置,从0开始
            // 0-8
            // 将布局文件转化成view对象
            View view = View.inflate(getApplicationContext(),
                    R.layout.item_home, null);
            // 每个条目的样式都不一样,初始化控件,去设置控件的值
            // view.findViewById是从item_home布局文件中找控件,findViewById是从activity_home中找控件
            ImageView iv_itemhome_icon = (ImageView) view
                    .findViewById(R.id.iv_itemhome_icon);
            TextView tv_itemhome_text = (TextView) view
                    .findViewById(R.id.tv_itemhome_text);
            // 设置控件的值
            iv_itemhome_icon.setImageResource(imageId[position]);// 给imageview设置图片,根据条目的位置从图片数组中获取相应的图片
            tv_itemhome_text.setText(names[position]);
            return view;
        }

        // 获取条目对应的数据
        @Override
        public Object getItem(int position) {
            // TODO Auto-generated method stub
            return null;
        }

        // 获取条目的id
        @Override
        public long getItemId(int position) {
            // TODO Auto-generated method stub
            return 0;
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_login) {
            //to login
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.action_isOnline) {
        }

        return true;
    }


    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_mycar) {
            // Handle the camera action
            Intent intent = new Intent(getApplicationContext(), MycarActivity.class);
            startActivity(intent);
        } else if (id == R.id.nav_love_carbook) {
            Intent intent = new Intent(getApplicationContext(), LoveCarBookActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_zhihu) {
            Intent intent = new Intent(getApplicationContext(), ZhihuActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_data) {

        } else if (id == R.id.nav_tools) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

}
