package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ViewFlipper;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.DietListRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.ActivityPo;
import lyu.klt.graduationdesign.module.bean.DietDataListPo;
import lyu.klt.graduationdesign.moudle.activity.DinneTimeActivity;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.activity.WebActivity;
import lyu.klt.graduationdesign.moudle.api.ActivityAPI;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.DietDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * 
 * @ClassName: DietFargmentActivity
 * @Description: TODO(推荐模块下的子模块（饮食）的Fargment)
 * @author 康良涛
 * @date 2016年12月15日 上午8:44:24
 *
 */
public class DietFargmentActivity extends Fragment implements OnGestureListener, MainActivity.MyTouchListener {
	private static final String TAG = DietFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	public SwipeRefreshLayout swipe_refresh_widget;
	public int lastVisibleItem;
	public ViewFlipper viewfilpper_diet_top;
	private RecyclerView rv_diet;
	private DietListRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	private List<DietDataListPo> dietDataListPo;

	private GestureDetector detector; // 手势检测

	// 动画效果
	private Animation leftInAnimation;
	private Animation leftOutAnimation;
	private Animation rightInAnimation;
	private Animation rightOutAnimation;

	List<ActivityPo> activityPoList;
	// private HorizontalListView hListView;
	// private HorizontalListViewAdapter hListViewAdapter;

	private View rl_diet_snacks, rl_diet_dinner, rl_diet_lunch, rl_diet_breakfast;

	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			swipe_refresh_widget.setRefreshing(false);

		}

	};

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_diet_layout, null);
		this.init(view);
		return view;
	}

	@Override
	public void onAttach(Context context) {
		// TODO Auto-generated method stub
		super.onAttach(context);
		this.context = (Activity) context;
	}

	public void init(View view) {
		this.initUtil();
		this.initData();
		this.initView(view);
		this.initViewData();
		this.initEvent();
		this.startGame();
	}

	public void initUtil() {
		// context = this.getActivity();
		// 将myTouchListener注册到分发列表
		((MainActivity) context).registerMyTouchListener(this);
	}

	public void initData() {

	}

	public void initView(View view) {

		viewfilpper_diet_top = (ViewFlipper) view.findViewById(R.id.viewfilpper_diet_top);
		detector = new GestureDetector(this);
		rv_diet = (RecyclerView) view.findViewById(R.id.rv_diet);
		swipe_refresh_widget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

		rl_diet_snacks = (RelativeLayout) view.findViewById(R.id.rl_diet_snacks);
		rl_diet_dinner = (RelativeLayout) view.findViewById(R.id.rl_diet_dinner);
		rl_diet_lunch = (RelativeLayout) view.findViewById(R.id.rl_diet_lunch);
		rl_diet_breakfast = (RelativeLayout) view.findViewById(R.id.rl_diet_breakfast);

	}

	public void initViewData() {
		isPrepared = true;

		// mPulltorefreshview.getFooterView()
		// .setFooterProgressBarDrawable(this.getResources().getDrawable(R.drawable.progress_circular));

		

		dietDataListPo = new ArrayList<DietDataListPo>();
		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);
		LinearLayoutManager layoutManager = new LinearLayoutManager(context);
		// 设置布局管理器
		rv_diet.setLayoutManager(layoutManager);
		// 设置为垂直布局，这也是默认的
		layoutManager.setOrientation(OrientationHelper.VERTICAL);
		// 设置Adapter
		rv_diet.setAdapter(recycleAdapter);

		rv_diet.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		rv_diet.addItemDecoration(decoration);

	}

	public void initEvent() {

		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		});

		rl_diet_snacks.setOnClickListener(onClickListener);
		rl_diet_dinner.setOnClickListener(onClickListener);
		rl_diet_lunch.setOnClickListener(onClickListener);
		rl_diet_breakfast.setOnClickListener(onClickListener);

	}

	public void startGame() {

		DietDataPAI.getDietData(context, dietDataStringHttpResponseListener);
		ActivityAPI.queryActivity(context, "diet", queryActivityStringHttpResponseListener);
		
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.rl_diet_snacks:
				intent.setClass(context, DinneTimeActivity.class);
				intent.putExtra("dinneTime", "加餐");
				startActivity(intent);
				break;
			case R.id.rl_diet_dinner:
				intent.setClass(context, DinneTimeActivity.class);
				intent.putExtra("dinneTime", "晚餐");
				startActivity(intent);
				break;
			case R.id.rl_diet_lunch:
				intent.setClass(context, DinneTimeActivity.class);
				intent.putExtra("dinneTime", "午餐");
				startActivity(intent);
				break;
			case R.id.rl_diet_breakfast:
				intent.setClass(context, DinneTimeActivity.class);
				intent.putExtra("dinneTime", "早餐");
				startActivity(intent);
				break;
			default:
				break;
			}
		}

	};

	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}

	/**
	 * 
	 * @Title: doIsDownloadCarouse @Description: 操作是否需要下载图片 @param: @param
	 *         strs @return: void @throws
	 */
	private void doLoadCarouse() {
		
		viewfilpper_diet_top.removeAllViews();
		viewfilpper_diet_top.setAutoStart(false);
		viewfilpper_diet_top.stopFlipping();
		for (int i = 0; i < activityPoList.size(); i++) {
			String activityImage = activityPoList.get(i).getActivityImage();
			String activitiImageFileNameArry[] = activityImage.split("/");
			ImageView imageView1 = new ImageView(context);
			imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
			ImageLoaderUtil.displayImage(
					UrlConstant.FILE_SERVICE_DOWNLOAD_VIEWFILPPERIMAGE_URL
							+ activitiImageFileNameArry[activitiImageFileNameArry.length - 1],
					imageView1, imageLoadingListener);
			final String activityURL=activityPoList.get(i).getActivityURL();
			imageView1.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent=new Intent(context,WebActivity.class);
					intent.putExtra("activityURL", activityURL);
					startActivity(intent);
				}
			});
			viewfilpper_diet_top.addView(imageView1);
		}

		viewfilpper_diet_top.setAutoStart(true);
		viewfilpper_diet_top.startFlipping();

		// 动画效果
		leftInAnimation = AnimationUtils.loadAnimation(context, R.anim.left_in);
		leftOutAnimation = AnimationUtils.loadAnimation(context, R.anim.left_out);
		rightInAnimation = AnimationUtils.loadAnimation(context, R.anim.right_in);
		rightOutAnimation = AnimationUtils.loadAnimation(context, R.anim.right_out);

	}

	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("TrainingFargmentActivity", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("DietFargmentActivity", "onLoadingFailed");
			// viewfilpper_training_top.removeAllViews();
			// ImageView imageView1 = new ImageView(context);
			// imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
			// imageView1.setImageResource(R.drawable.image_loading);
			// viewfilpper_training_top.addView(imageView1);
		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("DietFargmentActivity", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DietFargmentActivity", "onLoadingCancelled");
		}
	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.
	 * MotionEvent)
	 */
	@Override
	public boolean onDown(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.GestureDetector.OnGestureListener#onShowPress(android.view.
	 * MotionEvent)
	 */
	@Override
	public void onShowPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view
	 * .MotionEvent)
	 */
	@Override
	public boolean onSingleTapUp(MotionEvent e) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.GestureDetector.OnGestureListener#onScroll(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
		// TODO Auto-generated method stub
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * android.view.GestureDetector.OnGestureListener#onLongPress(android.view.
	 * MotionEvent)
	 */
	@Override
	public void onLongPress(MotionEvent e) {
		// TODO Auto-generated method stub

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.
	 * MotionEvent, android.view.MotionEvent, float, float)
	 */
	@Override
	public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
		// TODO Auto-generated method stub
		// Log.i(TAG, "e1=" + e1.getX() + " e2=" + e2.getX() + " e1-e2=" +
		// (e1.getX() - e2.getX()));
		//

		if (e1 != null && e2 != null) {

			if (e1.getX() - e2.getX() > 120) {
				viewfilpper_diet_top.setInAnimation(leftInAnimation);
				viewfilpper_diet_top.setOutAnimation(leftOutAnimation);
				viewfilpper_diet_top.showNext();// 向右滑动
				return true;
			} else if (e1.getX() - e2.getY() < -120) {
				viewfilpper_diet_top.setInAnimation(rightInAnimation);
				viewfilpper_diet_top.setOutAnimation(rightOutAnimation);
				viewfilpper_diet_top.showPrevious();// 向左滑动
				return true;
			}
		}

		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lyu.klt.graduationdesign.module.main.MainActivity.MyTouchListener#
	 * onTouchEvent(android.view.MotionEvent)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		// TODO Auto-generated method stub
		if (ViewUtil.inRangeOfView(viewfilpper_diet_top, event)) {
			// AbSharedUtil.putString(context,
			// Constant.RESIDEMENU_TOUCHEVENT_TYPE, "true");
			// MyApplication.resideMenu.dispatchTouchEvent(event);
			MyApplication.recommended_vp.onInterceptTouchEvent(event);
			MyApplication.main_vp.onInterceptTouchEvent(event);
			return this.detector.onTouchEvent(event); // touch事件交给手势处理。
		}

		AbSharedUtil.putString(context, Constant.RESIDEMENU_TOUCHEVENT_TYPE, "false");
		return false;
	}

	private AbStringHttpResponseListener dietDataStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss(context, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("list"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					dietDataListPo = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<DietDataListPo>>() {
					}.getType());
					mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
					rv_diet.setLayoutManager(mLayoutManager);
					mAdapter = new DietListRecyclerAdapter(context, 1, dietDataListPo);

					rv_diet.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onStart");
			// 显示进度框
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			// HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};
	
	private AbStringHttpResponseListener queryActivityStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss(context, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("list"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					activityPoList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<ActivityPo>>() {
					}.getType());

					doLoadCarouse();// 加载 轮播图

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onStart");
			// 显示进度框
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			// ((BaseActivity) context).HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};

}