package lyu.klt.graduationdesign.moudle.api;

import android.app.Activity;
import android.content.Intent;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.moudle.client.MyApplication;



public class ApiHandler {

	public static boolean isSccuss(Activity context, String type, String data) {
		if (!"Message".equals(type)) {
			if ("RequireLoginException".equals(type)) {	
				//需要重新登录
//				Intent intent = new Intent(context, LoginActivity.class);
//				intent.putExtra("mydata", data);
//				context.startActivity(intent);
//				MyApplication.getInstance().exit();
			}
			AbToastUtil.showToast(context, data);
			return false;

		}
		return true;
	}

}
