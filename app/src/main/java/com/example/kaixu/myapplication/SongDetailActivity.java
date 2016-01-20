package com.example.kaixu.myapplication;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class SongDetailActivity extends ActionBarActivity {

    private String songLink;
    private WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        songLink = getIntent().getStringExtra(SongListActivity.ARG_SONG_URL);
        if (songLink != null) {
            webView  = new WebView(this);
            webView.getSettings().setJavaScriptEnabled(true);
            webView.setWebViewClient(new WebViewClient()
            {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url)
                {
                    return false;
                }
            });
            webView.loadUrl(songLink);
            setContentView(webView);
        }
    }


    @Override
    public void onStop() {
        super.onStop();

        // stop the web page activity
        webView.stopLoading();
        webView.destroy();
    }



}
