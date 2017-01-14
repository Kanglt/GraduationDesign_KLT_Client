package lyu.klt.graduationdesign.moudle.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import android.support.v4.view.ViewPager;

import android.view.KeyEvent;

import android.view.MotionEvent;

import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.MainActivityViewPagerAdapter;
import lyu.klt.graduationdesign.module.fargment.DynamicFargmentActivity;
import lyu.klt.graduationdesign.module.fargment.FitnessFargmentActivity;
import lyu.klt.graduationdesign.module.fargment.PersonalFargmentActivity;
import lyu.klt.graduationdesign.module.fargment.RecommendedFargmentActivity;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.MainSlidingTabLayout;

public class MainActivity extends BaseActivity {
	private static final String TAG = MainActivity.class.getSimpleName();
	private Activity context;

	// 定义一个变量，来标识是否退出
	private static boolean isExit = false;

	private ViewPager viewPager;
	public List<Fragment> mainfragments;
	public MainSlidingTabLayout slidingTabLayout;
	
	public MainActivityViewPagerAdapter adapter;

	Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			isExit = false;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main_activity2);
		// MyApplication.getInstance().removeActivityByName("LauncherActivity");
		init();

	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		
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
		slidingTabLayout = (MainSlidingTabLayout) findViewById(R.id.id_tab2);
		viewPager = (ViewPager) findViewById(R.id.viewPager2);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		MyApplication.main_vp = viewPager;

		// SimpleAdapter simpleAdapter = new SimpleAdapter(context, listdata(),
		// R.layout.listview_moudle_layout, new String[]{"list_text"}, new
		// int[]{R.id.tv_list_title});
		mainfragments = new ArrayList<Fragment>();
		mainfragments.add(new RecommendedFargmentActivity());
		mainfragments.add(new FitnessFargmentActivity());
		mainfragments.add(new DynamicFargmentActivity());
		mainfragments.add(new PersonalFargmentActivity());

		adapter = new MainActivityViewPagerAdapter(this.getFragmentManager(), viewPager,
				mainfragments);
		// adapter.setOnExtraPageChangeListener(new
		// FragmentViewPagerAdapter.OnExtraPageChangeListener(){
		// @Override
		// public void onExtraPageSelected(int i) {
		// System.out.println("Extra...i: " + i);
		// }
		// });

		slidingTabLayout.setCustomTabView(R.layout.main_slidingtab_layout, R.id.tv_main_slidingtab,
				R.id.iv_main_slidingtab);
		slidingTabLayout.setSelectedIndicatorColors(R.color.black);
		slidingTabLayout.setViewPager(adapter.getViewPager());

		InputStream in=context.getResources().openRawResource(R.drawable.recommended_press);
		Bitmap myBitmap = BitmapFactory.decodeStream(in);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		MyApplication.imageViewList.get(0).setImageBitmap(myBitmap);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		adapter.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
		    @Override
		    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
		          
		         }

		    @Override
		    public void onPageSelected(int position) {
		        
		    	// 把得到的图片绑定在控件上显示
				MyApplication.imageViewList.get(position).setImageBitmap(MyApplication.bitmapPress.get(position));
				for (int x = 0; x < MyApplication.imageViewList.size(); x++) {
					if (x != position) {
						MyApplication.imageViewList.get(x).setImageBitmap(MyApplication.bitmapUnPress.get(x));
					}

				}
		    }


		    @Override
		    public void onPageScrollStateChanged(int state) {

		        if (state == ViewPager.SCROLL_STATE_DRAGGING) {
		            //正在滑动   pager处于正在拖拽中

		           

		        } else if (state == ViewPager.SCROLL_STATE_SETTLING) {
		            //pager正在自动沉降，相当于松手后，pager恢复到一个完整pager的过程
		           

		        } else if (state == ViewPager.SCROLL_STATE_IDLE) {
		            //空闲状态  pager处于空闲状态
		           
		        }


		    }
		});
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	public interface MyTouchListener {
		public boolean onTouchEvent(MotionEvent event);
	}

	// 保存MyTouchListener接口的列表
	private ArrayList<MyTouchListener> myTouchListeners = new ArrayList<MainActivity.MyTouchListener>();

	/**
	 * 提供给Fragment通过getActivity()方法来注册自己的触摸事件的方法
	 * 
	 * @param listener
	 */
	public void registerMyTouchListener(MyTouchListener listener) {
		myTouchListeners.add(listener);
	}

	/**
	 * 提供给Fragment通过getActivity()方法来取消注册自己的触摸事件的方法
	 * 
	 * @param listener
	 */
	public void unRegisterMyTouchListener(MyTouchListener listener) {
		myTouchListeners.remove(listener);
	}

	/**
	 * 分发触摸事件给所有注册了MyTouchListener的接口
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		// resideMenu.dispatchTouchEvent(ev);
		
		for (MyTouchListener listener : myTouchListeners) {
			listener.onTouchEvent(ev);
		}
		return super.dispatchTouchEvent(ev);
	}

	private void initSharedData() {
		AbSharedUtil.putString(context, Constant.VIEWFILPPER_TRAINING_FILEID,
				"viewfilpper_training_img1.jpg,viewfilpper_training_img2.jpg,viewfilpper_training_img3.jpg");
		AbSharedUtil.putString(context, Constant.VIEWFILPPER_DIET_FILEID,
				"viewfilpper_diet_img1.jpg,viewfilpper_diet_img2.jpg,viewfilpper_diet_img3.jpg");
		AbSharedUtil.putBoolean(context, Constant.ISLOGIN, true);
		AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, false);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub

		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	private void exit() {
		if (!isExit) {
			isExit = true;
			AbToastUtil.showToast(context, "再按一次退出程序！");
			// 利用handler延迟发送更改状态信息
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {

			// 方法 1: goto the default launcher. It's not recommended.
//			 Intent i = new Intent(Intent.ACTION_MAIN);
//			 i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			 i.addCategory(Intent.CATEGORY_HOME);
//			 startActivity(i);

			// 方法 2: goto the default launcher. It's recommended.
			//moveTaskToBack(true);
			MyApplication.getInstance().exit();
			System.exit(0);
		}
	}

}
