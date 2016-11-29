/**     
*/
package lyu.klt.graduationdesign.util;

import java.util.ArrayList;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;
import lyu.klt.graduationdesign.module.main.FargmentActivity;
import lyu.klt.graduationdesign.module.main.FargmentActivity2;
import lyu.klt.graduationdesign.module.main.FargmentActivity3;
import lyu.klt.graduationdesign.module.main.FargmentActivity4;
import lyu.klt.graduationdesign.module.po.UserPo;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/** 
* @ClassName: FargmentTabUtil 
* @Description: TODO(Tab导航栏相关操作) 
* @author 康良涛 
* @date 2016年11月29日 下午2:21:49 
*  
*/
public class FargmentTabUtil {
	
	/**
	 * 用于展示消息的Fragment
	 */
	private FargmentActivity FargmentActivity;

	/**
	 * 用于展示联系人的Fragment
	 */
	private FargmentActivity2 FargmentActivity2;

	/**
	 * 用于展示动态的Fragment
	 */
	private FargmentActivity3 FargmentActivity3;

	/**
	 * 用于展示设置的Fragment
	 */
	private FargmentActivity4 FargmentActivity4;

	/**
	 * 消息界面布局
	 */
	private View messageLayout;

	/**
	 * 联系人界面布局
	 */
	private View contactsLayout;

	/**
	 * 动态界面布局
	 */
	private View newsLayout;

	/**
	 * 设置界面布局
	 */
	private View settingLayout;

	/**
	 * 在Tab布局上显示消息图标的控件
	 */
	private ImageView messageImage;

	/**
	 * 在Tab布局上显示联系人图标的控件
	 */
	private ImageView contactsImage;

	/**
	 * 在Tab布局上显示动态图标的控件
	 */
	private ImageView newsImage;

	/**
	 * 在Tab布局上显示设置图标的控件
	 */
	private ImageView settingImage;

	/**
	 * 在Tab布局上显示消息标题的控件
	 */
	private TextView messageText;

	/**
	 * 在Tab布局上显示联系人标题的控件
	 */
	private TextView contactsText;

	/**
	 * 在Tab布局上显示动态标题的控件
	 */
	private TextView newsText;

	/**
	 * 在Tab布局上显示设置标题的控件
	 */
	private TextView settingText;

	/**
	 * 用于对Fragment进行管理
	 */
	private FragmentManager fragmentManager;
	
	private Activity context;
	
	public FargmentTabUtil(Activity context){
		this.context=context;
		fragmentManager = context.getFragmentManager();
		init();
		// 第一次启动时选中第0个tab
		setTabSelection(0);
	}

	public void init() {
		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();
	}


	public void initUtil() {

	}


	public void initData() {

	}


	public void initView() {
		messageLayout = context.findViewById(R.id.message_layout);
		contactsLayout = context.findViewById(R.id.contacts_layout);
		newsLayout = context.findViewById(R.id.news_layout);
		settingLayout = context.findViewById(R.id.setting_layout);
		messageImage = (ImageView) context.findViewById(R.id.message_image);
		contactsImage = (ImageView) context.findViewById(R.id.contacts_image);
		newsImage = (ImageView) context.findViewById(R.id.news_image);
		settingImage = (ImageView) context.findViewById(R.id.setting_image);
		messageText = (TextView) context.findViewById(R.id.message_text);
		contactsText = (TextView) context.findViewById(R.id.contacts_text);
		newsText = (TextView) context.findViewById(R.id.news_text);
		settingText = (TextView) context.findViewById(R.id.setting_text);
		
		
	}


	public void initViewData() {
		ImageViewUtils.setImageViewWidth_div(context, messageImage,14);
		ImageViewUtils.setImageViewWidth_div(context, contactsImage,14);
		ImageViewUtils.setImageViewWidth_div(context, newsImage,14);
		ImageViewUtils.setImageViewWidth_div(context, settingImage,14);
		
		
	}


	public void initEvent() {
		messageLayout.setOnClickListener(onClickListener);
		contactsLayout.setOnClickListener(onClickListener);
		newsLayout.setOnClickListener(onClickListener);
		settingLayout.setOnClickListener(onClickListener);
	}

	
	public void startGame() {
		
	}

	
	/**
	 * 根据传入的index参数来设置选中的tab页。
	 * 
	 * @param index
	 *            每个tab页对应的下标。0表示消息，1表示联系人，2表示动态，3表示设置。
	 */
	public void setTabSelection(int index) {
		// 每次选中之前先清楚掉上次的选中状态
		clearSelection();
		// 开启一个Fragment事务
		FragmentTransaction transaction = fragmentManager.beginTransaction();
		// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
		hideFragments(transaction);
		switch (index) {
		case 0:
			// 当点击了消息tab时，改变控件的图片和文字颜色
			messageImage.setImageResource(R.drawable.main_my_press);
			messageText.setTextColor(Color.WHITE);
			if (FargmentActivity == null) {
				// 如果MessageFragment为空，则创建一个并添加到界面上
				FargmentActivity = new FargmentActivity();
				transaction.add(R.id.content, FargmentActivity);
			} else {
				// 如果MessageFragment不为空，则直接将它显示出来
				transaction.show(FargmentActivity);
			}
			break;
		case 1:
			// 当点击了联系人tab时，改变控件的图片和文字颜色
			contactsImage.setImageResource(R.drawable.main_shop_press);
			contactsText.setTextColor(Color.WHITE);
			if (FargmentActivity2 == null) {
				// 如果ContactsFragment为空，则创建一个并添加到界面上
				FargmentActivity2 = new FargmentActivity2();
				transaction.add(R.id.content, FargmentActivity2);
			} else {
				// 如果ContactsFragment不为空，则直接将它显示出来
				transaction.show(FargmentActivity2);
			}
			break;
		case 2:
			// 当点击了动态tab时，改变控件的图片和文字颜色
			newsImage.setImageResource(R.drawable.main_order_press);
			newsText.setTextColor(Color.WHITE);
			if (FargmentActivity3 == null) {
				// 如果NewsFragment为空，则创建一个并添加到界面上
				FargmentActivity3 = new FargmentActivity3();
				transaction.add(R.id.content, FargmentActivity3);
			} else {
				// 如果NewsFragment不为空，则直接将它显示出来
				transaction.show(FargmentActivity3);
			}
			break;
		case 3:
			// 当点击了设置tab时，改变控件的图片和文字颜色
						settingImage.setImageResource(R.drawable.main_news_press);
						settingText.setTextColor(Color.WHITE);
						if (FargmentActivity4 == null) {
							// 如果SettingFragment为空，则创建一个并添加到界面上
							FargmentActivity4 = new FargmentActivity4();
							transaction.add(R.id.content, FargmentActivity4);
						} else {
							// 如果SettingFragment不为空，则直接将它显示出来
							transaction.show(FargmentActivity4);
						}
						break;
		default:
			break;
		}
		transaction.commit();
	}
	
	/**
	 * 清除掉所有的选中状态。
	 */
	private void clearSelection() {
		messageImage.setImageResource(R.drawable.main_my_unpress);
		messageText.setTextColor(Color.parseColor("#82858b"));
		contactsImage.setImageResource(R.drawable.main_shop_unpress);
		contactsText.setTextColor(Color.parseColor("#82858b"));
		newsImage.setImageResource(R.drawable.main_order_unpress);
		newsText.setTextColor(Color.parseColor("#82858b"));
		settingImage.setImageResource(R.drawable.main_news_unpress);
		settingText.setTextColor(Color.parseColor("#82858b"));
	}

	/**
	 * 将所有的Fragment都置为隐藏状态。
	 * 
	 * @param transaction
	 *            用于对Fragment执行操作的事务
	 */
	private void hideFragments(FragmentTransaction transaction) {
		if (FargmentActivity != null) {
			transaction.hide(FargmentActivity);
		}
		if (FargmentActivity2 != null) {
			transaction.hide(FargmentActivity2);
		}
		if (FargmentActivity3 != null) {
			transaction.hide(FargmentActivity3);
		}
		if (FargmentActivity4 != null) {
			transaction.hide(FargmentActivity4);
		}
	}
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
//			case R.id.btn_test:
//				TestAPI.testForMobile(context, "Kanglt", abStringHttpResponseListener);
//				break;
			case R.id.message_layout:
				// 当点击了消息tab时，选中第1个tab
				setTabSelection(0);
				break;
			case R.id.contacts_layout:
				// 当点击了联系人tab时，选中第2个tab
				setTabSelection(1);
				break;
			case R.id.news_layout:
				// 当点击了动态tab时，选中第3个tab
				setTabSelection(2);
				break;
			case R.id.setting_layout:
				// 当点击了设置tab时，选中第4个tab
				setTabSelection(3);
				break;

			default:
				break;
			}
		}
		
	};
}
