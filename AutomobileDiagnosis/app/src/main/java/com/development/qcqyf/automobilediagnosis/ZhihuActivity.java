package com.development.qcqyf.automobilediagnosis;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class ZhihuActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zhihu);
        WebView wv_zhihu = (WebView) findViewById(R.id.wv_zhihu);
        wv_zhihu.getSettings().setJavaScriptEnabled(true);
        wv_zhihu.setWebViewClient(new WebViewClient());
        wv_zhihu.loadUrl("https://www.zhihu.com/question/19854130");
    }
}
