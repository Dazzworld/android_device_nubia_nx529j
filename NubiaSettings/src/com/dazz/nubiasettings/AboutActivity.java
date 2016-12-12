package com.dazz.nubiasettings;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class AboutActivity extends Activity{

	Button mPayZfb;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_about);
		mPayZfb = (Button) findViewById(R.id.pay_zfb);
		mPayZfb.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				PayUtils.openAlipayPayPage(AboutActivity.this);
			}
		});
	}

	
}
