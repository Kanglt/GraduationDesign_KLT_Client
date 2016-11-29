/*   
 * Copyright (c) 2015-2020 FJZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */ 
package lyu.klt.graduationdesign.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import lyu.klt.frame.ab.util.AbDialogUtil;





/**   
 * APP基础类，继承 Fragment
 * @author   沈龙琴
 * @version   1.0 
 * @datetime 2016年7月14日 下午5:50:28   
 */ 
@TargetApi(Build.VERSION_CODES.HONEYCOMB)

public class BaseFragment extends Fragment{

	private Activity context;
	public Handler handler = new Handler(){
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 移除进度框
				AbDialogUtil.removeDialog(context);
				break;

			default:
				break;
			}
		}
	};
	
	
	public void HideProgressDialog(Activity context){
		this.context = context;
		handler.sendEmptyMessageDelayed(0, 500);//避免UI线程忙，导致关闭不了
	}

	
}
