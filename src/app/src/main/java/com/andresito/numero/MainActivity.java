package com.andresito.numero;

import android.app.*;
import android.os.*;
import android.webkit.*;
import android.view.*;
import android.net.*;
import java.util.*;
import android.widget.*;

public class MainActivity extends Activity {
    
    private WebView mWebView;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
		
		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		setContentView(R.layout.main);
        
        mWebView = findViewById(R.id.activity_main_webview);
		
		mWebView.setVerticalScrollBarEnabled(false);
       
       	// Enable Javascript
		WebSettings webSettings = mWebView.getSettings();
		webSettings.setJavaScriptEnabled(true);
       	webSettings.setDomStorageEnabled(true);
		webSettings.setSaveFormData(false);
		webSettings.setUserAgentString("hcm-android-app");
		
		mWebView.setOverScrollMode(View.OVER_SCROLL_NEVER);
		
      	// Force links and redirects to open in the WebView instead of in a browser
		//mWebView.setWebViewClient(new WebViewClient());

		// Stop local links and redirects from opening in browser instead of WebView
		mWebView.setWebViewClient(new MyAppWebViewClient());
      
		// Loads a Page when there is no internet
		mWebView.setWebViewClient(new WebViewClient() {
			public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
				mWebView.loadUrl("file:///android_asset/nointernet.html");
			}
		});
	
		mWebView.setDownloadListener(new DownloadListener() {       

			@Override
			public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
				DownloadManager.Request request = new DownloadManager.Request(Uri.parse(url));
				request.allowScanningByMediaScanner();
				request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED); //Notify client once download is completed!
				DownloadManager dm = (DownloadManager) getSystemService(DOWNLOAD_SERVICE);
				dm.enqueue(request);
				Toast.makeText(getApplicationContext(), "The File is Being Downloaded", Toast.LENGTH_LONG).show();
			}

		});
 
        mWebView.loadUrl("https://andresitodeguzman.github.io/project-numero");
		
      }
	  
	@Override
    public void onBackPressed() {
        if(mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            super.onBackPressed();
        }
    }
	  
  }
  
  