package com.dazz.nubiasettings.modem;

import java.util.ArrayList;

import com.dazz.nubiasettings.R;
import com.qualcomm.qcrilhook.QcRilHook;
import com.qualcomm.qcrilhook.QcRilHookCallback;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ModemActivity extends Activity implements OnClickListener {

	String cm = "OTA_/system/etc/modem_ota/CM/mcfg_sw.mbn1351663707:295912";
	private QcRilHook mQcRilHook;

	private RelativeLayout mCard1Layout, mCard2Layout;
	private TextView[] mCardConfig = new TextView[2];
	private String configs[] = new String[2];
	private String selectedConfig = null;
	ProgressDialog progressDialog = null;
	
	private boolean result = true;
	
	Handler handler = new Handler();
	private QcRilHookCallback mQcrilHookCb = new QcRilHookCallback() {

		public void onQcRilHookDisconnected() {
		}

		public void onQcRilHookReady() {

			logd("QcRilHookReady");
			
			handler.postDelayed(new Runnable() {
				public void run() {
					progressDialog.dismiss();
				}
			}, 1000);
			init();

		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_modem);
		progressDialog = showProgressDialog("请稍候..");
		progressDialog.show();

		mQcRilHook = new QcRilHook(this, mQcrilHookCb);
	}

	private void init() {
		findViews();
		loadConfig();
		mCard1Layout.setOnClickListener(this);
		mCard2Layout.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		mQcRilHook.dispose();
		super.onDestroy();
	}

	private void loadConfig() {
		logd("defualtp phone:"+mQcRilHook.qcRilGetPrioritySubscription());
		for (int i = 0; i < 2; i++) {
			String tmp = mQcRilHook.qcRilGetConfig(i);
			if (tmp != null) {
				configs[i] = mQcRilHook.qcRilGetMetaInfoForConfig(tmp);
				mCardConfig[i].setText(configs[i]);
			} else {
				//mQcRilHook.qcRilSelectConfig(ModemConfig.CT, 0,i);
				mCardConfig[i].setText(R.string.get_config_err);
			}

		}
	}

	private void findViews() {
		mCard1Layout = (RelativeLayout) findViewById(R.id.card_1_layout);
		mCard2Layout = (RelativeLayout) findViewById(R.id.card_2_layout);
		mCardConfig[0] = (TextView) findViewById(R.id.card1config);
		mCardConfig[1] = (TextView) findViewById(R.id.card2config);
	}

	public void logd(String log) {
		Log.d("MedomSettings", log);
	}

	@Override
	public void onClick(View v) {
		int slot = 0;
		if (v.getId() == R.id.card_2_layout) {
			slot = 1;
		}
		showSelectDialog(slot);
	}

	private void showSelectDialog(final int slot) {
		selectedConfig = configs[slot];
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		final ArrayList<String> aList = new ArrayList<String>(
				ModemConfig.mOperatorMBN.keySet());
		builder.setTitle(String.format(getString(R.string.card), slot))
				.setSingleChoiceItems(aList.toArray(new String[] {}),
						aList.indexOf(selectedConfig),
						new DialogInterface.OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								logd("old select:" + selectedConfig
										+ ",new select" + aList.get(which));
								selectedConfig = aList.get(which);
							}
						});
		builder.setCancelable(false);
		builder.setPositiveButton(R.string.ok,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						if (selectedConfig.equals(configs[slot])) {
							toast("已经是当前配置了,无须设置~");
							dialog.dismiss();
							return;
						}
						dialog.dismiss();
						progressDialog = showProgressDialog("正在更新配置..");
						progressDialog.show();
						updateConfig(slot,selectedConfig);
					}
				});
		builder.setNegativeButton(R.string.cancel,
				new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				});
		builder.show();
	}

	protected void updateConfig(int slot, String selectedConfig) {
		String realConfig = ModemConfig.mOperatorMBN.get(selectedConfig);
		//result = mQcRilHook.qcRilDeactivateConfigsBySub(slot);
		//logd("注销配置!slot="+slot+",result="+result);
		result =mQcRilHook.qcRilSelectConfig(realConfig, slot+1);
		logd("更新配置!slot="+slot+",result="+result);
		
		handler.postDelayed(new Runnable() {
			public void run() {
				progressDialog.dismiss();
				toast(result?"更新成功,请重启手机":"更新失败");
				loadConfig();
			}
		}, 1000);
		
	}

	public ProgressDialog showProgressDialog(String msg) {
		ProgressDialog progressDialog = new ProgressDialog(this);
		progressDialog.setIndeterminate(true);
		progressDialog.setMessage(msg);
		progressDialog.setCancelable(false);
		return progressDialog;
	}
	
	public void toast(String msg){
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
}
