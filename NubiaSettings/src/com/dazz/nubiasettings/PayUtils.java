package com.dazz.nubiasettings;

import java.net.URLEncoder;

import android.content.Context;
import android.content.Intent;

public class PayUtils {
	public static void openAlipayPayPage(Context context) {
        openUri(context, "https://qr.alipay.com/a6x02926zf0gzm5dr6fz9b2");
    }

	private static void openUri(Context context, String url) {
		Intent i = new Intent(context,WebActivity.class);
		i.putExtra("url", url);
		context.startActivity(i);
	}
}
