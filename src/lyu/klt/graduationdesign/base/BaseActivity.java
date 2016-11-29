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

import android.app.Activity;
import android.app.Dialog;
import android.os.Handler;
import android.os.Message;
import lyu.klt.frame.ab.activity.AbActivity;
import lyu.klt.frame.ab.util.AbDialogUtil;



/**
 * APP基础类，继承了abactivity，主要是用到它的顶部栏，APP中所有的新建activity都继承这个BaseActivity;如果不需要顶部栏可以不用继承abactivity
 * 子类中要记得使用setAbContentView(R.layout.XXX);来显示布局，而不是使用setContentView(R.layout.XXX);
 * 
 * @author Dai
 * @version 1.0
 * @datetime 2016年4月26日 下午9:08:38
 */
public class BaseActivity extends AbActivity {

	/**
	 * Activity初始化步骤，一定要严格按照这个顺序
	 */
	public void init() {
		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();

	}

	/**
	 * 初始化activity中用到的相关工具类
	 */
	public void initUtil() {

	}

	/**
	 * 获取从本地相关数据，如从bundle中传递过来的数据或者是从本地数据库中获取的数据
	 */
	public void initData() {

	}

	/**
	 * 初始化页面控件
	 */
	public void initView() {

	}

	/**
	 * 初始化控件数据
	 */
	public void initViewData() {

	}

	/**
	 * 初始化activity中相关事件，例如控件点击事件，相关回调函数等
	 */
	public void initEvent() {

	}

	/**
	 * 开会处理相关业务，例如联网获取数据等相关操作
	 */
	public void startGame() {

	}
	
	public Handler handler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			switch (msg.what) {
			case 0:// 移除进度框
				AbDialogUtil.removeDialog(BaseActivity.this);
				break;

			default:
				break;
			}
		}
	};
	
	
	public void HideProgressDialog(){
		handler.sendEmptyMessageDelayed(0, 500);//避免UI线程忙，导致关闭不了
	}
	
	public void showDialog(Dialog dialog) {
		if (dialog == null)
			return;
		if (!dialog.isShowing())
			dialog.show();
	}

	public void hideDialog(Dialog dialog) {
		if (dialog == null)
			return;
		if (dialog.isShowing())
			dialog.dismiss();
	}
}
