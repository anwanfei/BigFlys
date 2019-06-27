package com.anfly.bigfly.distance.activity;


import android.os.Bundle;
import android.webkit.WebView;
import android.widget.LinearLayout;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;
import com.just.agentweb.AgentWeb;

import butterknife.BindView;

public class WebViewActivity extends BaseActivity {

    @BindView(R.id.ll_root)
    LinearLayout llRoot;
    @BindView(R.id.webview)
    WebView webview;
    private AgentWeb mAgentWeb;

    @Override
    protected void initData(Bundle savedInstanceState) {
        Bundle bundle = getIntent().getBundleExtra("data");
        String url = bundle.getString("url");
        webview.loadUrl(url);
        mAgentWeb = AgentWeb.with(this)
                .setAgentWebParent((LinearLayout) llRoot, new LinearLayout.LayoutParams(-1, -1))
                .useDefaultIndicator()
                .createAgentWeb()
                .ready()
                .go(url);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_web_view;
    }
}
