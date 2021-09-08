package com.epro.webview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import static android.webkit.WebSettings.MIXED_CONTENT_ALWAYS_ALLOW;
import static com.epro.webview.MainActivity.WEB_VIEW_EXTRA;

public class WebviewActivity extends AppCompatActivity {
    private WebView webView;
    private ProgressBar progressBar;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview);
        url = getIntent().getStringExtra(WEB_VIEW_EXTRA);
        progressBar = findViewById(R.id.progress_bar);
        webView = findViewById(R.id.webview);

        initWebView();

        if(!url.startsWith("http")) {
            url = "http://" + url;
        }

        if (webView != null) {
            try {
                webView.loadUrl(url);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void initWebView() {

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setDomStorageEnabled(true);

        //允许webview对文件的操作
        settings.setAllowUniversalAccessFromFileURLs(true);
        settings.setAllowFileAccessFromFileURLs(true);
        settings.setUseWideViewPort(true); // 关键点
        settings.setAllowFileAccess(true); // 允许访问文件
        settings.setSupportZoom(true); // 支持缩放
        settings.setLoadWithOverviewMode(true);
//        settings.setPluginsEnabled(true);
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE); // 不加载缓存内容
        settings.setMixedContentMode(MIXED_CONTENT_ALWAYS_ALLOW);

        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if (url.startsWith("http:") || url.startsWith("https:")) {
                    return super.shouldOverrideUrlLoading(view, url);
                }
                try {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return true;
            }
        });
        initProgress();
    }

    private void initProgress() {
        webView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                if (!isFinishing()) {
                    setTitle(title);
                }
            }

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                super.onProgressChanged(view, newProgress);
                if (!isFinishing()) {
                    if (newProgress >= 100) {
                        progressBar.setVisibility(View.GONE);
                    }
                    progressBar.setProgress(newProgress);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        breakWebView();
        super.onDestroy();
    }

    private void breakWebView() {
        if (webView != null) {
            webView.clearHistory();
            webView.clearCache(true);
            webView.stopLoading();
            webView.removeAllViews();
            webView.destroy();
        }
        webView = null;
    }

}
