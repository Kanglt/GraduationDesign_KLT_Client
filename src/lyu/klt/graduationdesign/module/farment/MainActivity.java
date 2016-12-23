package lyu.klt.graduationdesign.module.farment;

import java.util.ArrayList;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.ResideMenu.ResideMenu;
import lyu.klt.graduationdesign.module.ResideMenu.ResideMenuItem;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

public class MainActivity extends BaseActivity implements View.OnClickListener {
	private static final String TAG = MainActivity.class.getSimpleName();
	private Activity context;

	private ResideMenu resideMenu;
	private ResideMenuItem recommendedItem;//推荐
	private ResideMenuItem fitnessItem;//健身
	private ResideMenuItem dynamicItem;//动态
	private ResideMenuItem healthItem;//健康
	private ResideMenuItem personalItem;//个人

	private Button title_bar_left_menu;
	private Button title_bar_right_menu;
	private View layout_top;
	


	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		/*
		 * 隐藏虚拟键盘
		 */
		// window = getWindow();
		// WindowManager.LayoutParams params = window.getAttributes();
		// params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;
		// window.setAttributes(params);
		setAbContentView(R.layout.main);
		init();
		if (savedInstanceState == null)
			changeFragment(new RecommendedFargmentActivity());
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();
	}

	@Override
	public void initUtil() {
		// TODO Auto-generated method stub
		super.initUtil();
		context = this;
		MyApplication.getInstance().addActivity(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		initSharedData();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		layout_top=(LinearLayout)findViewById(R.id.layout_top);
		
		title_bar_left_menu = (Button) findViewById(R.id.title_bar_left_menu);
		title_bar_right_menu = (Button) findViewById(R.id.title_bar_right_menu);

		// attach to current activity;
		resideMenu = new ResideMenu(this);
		resideMenu.setUse3D(true);
		resideMenu.setBackground(R.drawable.menu_background2);
		resideMenu.attachToActivity(this);
		resideMenu.setMenuListener(menuListener);
		// valid scale factor is between 0.0f and 1.0f. leftmenu'width is
		// 150dip.
		resideMenu.setScaleValue(0.6f);
		MyApplication.resideMenu=resideMenu;

		// create menu items;
		recommendedItem = new ResideMenuItem(this, R.drawable.icon_home, "推荐");
		fitnessItem = new ResideMenuItem(this, R.drawable.icon_profile, "健身");
		dynamicItem = new ResideMenuItem(this, R.drawable.icon_profile, "动态");
		healthItem = new ResideMenuItem(this, R.drawable.icon_profile, "健康");
		personalItem = new ResideMenuItem(this, R.drawable.icon_profile, "个人");
		
		
		

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		resideMenu.addMenuItem(recommendedItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(fitnessItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(dynamicItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(healthItem, ResideMenu.DIRECTION_LEFT);
		resideMenu.addMenuItem(personalItem, ResideMenu.DIRECTION_LEFT);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		recommendedItem.setOnClickListener(this);
		fitnessItem.setOnClickListener(this);
		dynamicItem.setOnClickListener(this);
		healthItem.setOnClickListener(this);
		personalItem.setOnClickListener(this);
		title_bar_left_menu.setOnClickListener(this);
		title_bar_right_menu.setOnClickListener(this);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

//	@Override
//	public boolean dispatchTouchEvent(MotionEvent ev) {
//		return resideMenu.dispatchTouchEvent(ev);
//	}

	@Override
	public void onClick(View view) {

		if (view == recommendedItem) {
			changeFragment(new RecommendedFargmentActivity());
			resideMenu.closeMenu();
		} else if (view == fitnessItem) {
			changeFragment(new FitnessFargmentActivity());
			resideMenu.closeMenu();
		} else if (view == dynamicItem) {
			changeFragment(new TrainingFargmentActivity());
			resideMenu.closeMenu();
		} else if (view == healthItem) {
			changeFragment(new DietFargmentActivity());
			resideMenu.closeMenu();
		} else if (view == personalItem) {
			changeFragment(new FitnessFargmentActivity());
			resideMenu.closeMenu();
		} else if (view == title_bar_left_menu) {
			resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
		}
//		} else if (view == title_bar_right_menu) {
//			resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
//		}

	}

	private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
		@Override
		public void openMenu() {
			// Toast.makeText(context, "Menu is opened!",
			// Toast.LENGTH_SHORT).show();
			// AbToastUtil.showToast(context, "Menu is opened!");
		}

		@Override
		public void closeMenu() {
			// Toast.makeText(context, "Menu is closed!",
			// Toast.LENGTH_SHORT).show();
			// AbToastUtil.showToast(context, "Menu is closed!");
		}
	};

	private void changeFragment(android.app.Fragment targetFragment) {
		resideMenu.clearIgnoredViewList();
		getFragmentManager().beginTransaction().replace(R.id.main_fragment, targetFragment, "fragment")
				.setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE).commit();
	}

	// What good method is to access resideMenu锛�
	public ResideMenu getResideMenu() {
		return resideMenu;
	}
	
	private void initSharedData(){
		AbSharedUtil.putString(context, Constant.VIEWFILPPER_TRAINING_FILEID, "viewfilpper_training_img1.jpg,viewfilpper_training_img2.jpg,viewfilpper_training_img3.jpg");
		AbSharedUtil.putString(context, Constant.VIEWFILPPER_DIET_FILEID, "viewfilpper_diet_img1.jpg,viewfilpper_diet_img2.jpg,viewfilpper_diet_img3.jpg");
	}
	
	public interface MyTouchListener {
	    public boolean onTouchEvent(MotionEvent event);
	}
		
	// 保存MyTouchListener接口的列表
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();
		
	/**
	* 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	* @param listener
	*/
	public void registerMyTouchListener(MyTouchListener listener) {
	     myTouchListeners.add(listener);
	}
		
	/**
	* 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	* @param listener
	*/
	public void unRegisterMyTouchListener(MyTouchListener listener) {
	    myTouchListeners.remove( listener );
	}
		
	/**
	* 分发触摸事件给所有注册了MyTouchListener的接口
	*/
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) { 
		//resideMenu.dispatchTouchEvent(ev);
	    for (MyTouchListener listener : myTouchListeners) {
		listener.onTouchEvent(ev);
	    }
	    return super.dispatchTouchEvent(ev);
	}
	
	
}
