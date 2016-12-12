package com.dazz.nubiasettings;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class WebActivity extends Activity {

	WebView webVeiw;
	String url;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent i = getIntent();
		url = i.getStringExtra("url");
		webVeiw = new WebView(this);
		setContentView(webVeiw);
		WebSettings webSettings = webVeiw.getSettings();
		webSettings.setJavaScriptEnabled(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setBuiltInZoomControls(true);
		webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
		webSettings.setDomStorageEnabled(true);

		webSettings.setGeolocationEnabled(true);
		webVeiw.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				Log.e("WebActivity", "访问的url地址：" + url);
				if (parseScheme(url)) {
					try {
						Uri uri = Uri.parse(url);
						Intent intent;
						intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
						intent.addCategory("android.intent.category.BROWSABLE");
						intent.setComponent(null);
						startActivity(intent);
						finish();
					} catch (Exception e) {

					}
				} else {
					view.loadUrl(url);
				}

				return true;

			}

		});
		
		webVeiw.loadUrl(url);
	}

	public boolean parseScheme(String url) {
		if (url.contains("platformapi/startapp")) {
			return true;
		} else {
			return false;
		}
	}
}
