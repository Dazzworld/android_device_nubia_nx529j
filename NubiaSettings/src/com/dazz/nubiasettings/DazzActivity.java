package com.dazz.nubiasettings;

import com.dazz.nubiasettings.modem.ModemActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

public class DazzActivity extends Activity implements OnClickListener {

	TextView mNetCheckSer;
	Switch mDisableNetCheck;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_dazz);
		findViewById(R.id.modem_layout).setOnClickListener(this);
		findViewById(R.id.about_layout).setOnClickListener(this);
		mNetCheckSer = (TextView) findViewById(R.id.net_checkser);
		mDisableNetCheck = (Switch) findViewById(R.id.disable_network_check);
		findViewById(R.id.rp_netcheck).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				showSettingDialog();
			}
		});
		initState();
		mDisableNetCheck.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				int i = isChecked?0:1;
				Settings.Global.putInt(getContentResolver(), "captive_portal_detection_enabled", i);
			}
		});
	}

	protected void showSettingDialog() {
		final EditText et = new EditText(this);
		AlertDialog.Builder builder = new AlertDialog.Builder(this)
		.setTitle("请输入网址(不需要http://)")
		.setView(et)
		.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				if (et.getText().toString().isEmpty()) {
					Toast.makeText(DazzActivity.this, "不能为空", Toast.LENGTH_SHORT).show();
					return;
				}else{
					Settings.Global.putString(getContentResolver(), "captive_portal_server", et.getText().toString());
					initState();
				}
				dialog.dismiss();
			}
		})
		.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		builder.show();
	}

	private void initState() {
		String checkSer = Settings.Global.getString(getContentResolver(), "captive_portal_server");
		if (checkSer==null||checkSer.isEmpty()) {
			mNetCheckSer.setText("暂未设置,请自行寻找~");
		}else{
			mNetCheckSer.setText(checkSer);
		}
		boolean st =  Settings.Global.getInt(getContentResolver(),
                "captive_portal_detection_enabled", 1) == 0;
		mDisableNetCheck.setChecked(st);
	}

	@Override
	public void onClick(View v) {
		Intent i = null;
		switch (v.getId()) {
		case R.id.modem_layout:
			i = new Intent(this, ModemActivity.class);
			break;
			
		case R.id.about_layout:
			i = new Intent(this, AboutActivity.class);
			break;
		}
		startActivity(i);
	}

}
