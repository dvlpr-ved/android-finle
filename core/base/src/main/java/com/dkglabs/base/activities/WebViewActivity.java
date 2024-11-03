package com.dkglabs.base.activities;

import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dkglabs.base.databinding.ActivityWebViewBinding;
import com.dkglabs.base.utils.UIUtils;

/**
 * Created by Himanshu Srivastava on 3/22/2023.
 */
public class WebViewActivity extends BaseActivity {

    private ActivityWebViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityWebViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        initializeView();
    }

    private void initializeView() {


        String url = getIntent().getStringExtra("url");
        final String title = getIntent().getStringExtra("title");

        binding.webView.getSettings().setJavaScriptEnabled(true);
        binding.webView.getSettings().setLoadWithOverviewMode(true);
        binding.webView.getSettings().setUseWideViewPort(true);
        binding.webView.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                progressDialog = UIUtils.showProgressDialog(WebViewActivity.this);
                view.loadUrl(url);
                return true;
            }

            @Override
            public void onPageFinished(WebView view, final String url) {
                UIUtils.dismissDialog(progressDialog);
            }
        });

        binding.webView.loadUrl(url);
    }
}