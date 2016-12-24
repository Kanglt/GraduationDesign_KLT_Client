/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
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
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ViewFlipper;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.TrainingListAdapter;
import lyu.klt.graduationdesign.module.adapter.TrainingListRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * @ClassName: TestActivity
 * @Description: TODO(功能测试类)
 * @author 康良涛
 * @date 2016年12月24日 下午4:23:10
 * 
 */
public class TestActivity extends BaseActivity implements OnGestureListener {

	private static final String TAG = TestActivity.class.getSimpleName();
	private Activity context;

	private RecyclerView rv_test;
	private TrainingListRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	List<TrainingDataListPo> trainingDataListPo;

	
	public SwipeRefreshLayout swipe_refresh_widget;
	public ViewFlipper viewfilpper_training_top;
	
	private GestureDetector detector; // 手势检测
	// 动画效果
	private Animation leftInAnimation;
	private Animation leftOutAnimation;
	private Animation rightInAnimation;
	private Animation rightOutAnimation;
	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			swipe_refresh_widget.setRefreshing(false);

		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.listview_header);
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
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		rv_test = (RecyclerView) findViewById(R.id.rv_test);
		
		swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		viewfilpper_training_top = (ViewFlipper) findViewById(R.id.viewfilpper_training_top);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		doLoadCarouse();// 加载 轮播图
		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(TestActivity.this, mDatas);
		LinearLayoutManager layoutManager = new LinearLayoutManager(this);
		// 设置布局管理器
		rv_test.setLayoutManager(layoutManager);
		// 设置为垂直布局，这也是默认的
		layoutManager.setOrientation(OrientationHelper.VERTICAL);
		// 设置Adapter
		rv_test.setAdapter(recycleAdapter);

		rv_test.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		rv_test.addItemDecoration(decoration);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		detector = new GestureDetector(this);
		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		});
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		TrainingDataPAI.getTrainingData(context, trainingDataStringHttpResponseListener);
	}
	
	 private void initRVData() {
         mDatas = new ArrayList<String>();
         for ( int i=0; i < 1; i++) {
              mDatas.add( "item"+i);
        }
  }

	 
	 private AbStringHttpResponseListener trainingDataStringHttpResponseListener = new AbStringHttpResponseListener() {

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

						trainingDataListPo = gson.fromJson(jsonObject.getString("list"),
								new TypeToken<List<TrainingDataListPo>>() {
								}.getType());
						mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
						rv_test.setLayoutManager(mLayoutManager);
						mAdapter = new TrainingListRecyclerAdapter(context, 2,trainingDataListPo);
						
						rv_test.setAdapter(mAdapter);
						mAdapter.notifyDataSetChanged();
					//	ViewUtil.setListViewHeightBasedOnChildren(listView_training);
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
		
		
		private void doLoadCarouse() {
			String localStr = AbSharedUtil.getString(context, Constant.VIEWFILPPER_TRAINING_FILEID);
			if (localStr == null) {
				return;
			}
			viewfilpper_training_top.removeAllViews();
			viewfilpper_training_top.setAutoStart(false);
			viewfilpper_training_top.stopFlipping();
			String strArr[] = localStr.split(",");
			for (String fileId : strArr) {
				ImageView imageView1 = new ImageView(context);
				imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
				ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_VIEWFILPPERIMAGE_URL + fileId, imageView1,
						imageLoadingListener);
				viewfilpper_training_top.addView(imageView1);
			}

			viewfilpper_training_top.setAutoStart(true);
			viewfilpper_training_top.startFlipping();

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
				Log.e("TrainingFargmentActivity", "onLoadingFailed");
				// viewfilpper_training_top.removeAllViews();
				// ImageView imageView1 = new ImageView(context);
				// imageView1.setScaleType(ImageView.ScaleType.FIT_XY);
				// imageView1.setImageResource(R.drawable.image_loading);
				// viewfilpper_training_top.addView(imageView1);
			}

			@Override
			public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
				// TODO Auto-generated method stub
				Log.e("TrainingFargmentActivity", "onLoadingComplete");
			}

			@Override
			public void onLoadingCancelled(String arg0, View arg1) {
				// TODO Auto-generated method stub
				Log.e("TrainingFargmentActivity", "onLoadingCancelled");
			}
		};

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onDown(android.view.MotionEvent)
		 */
		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onShowPress(android.view.MotionEvent)
		 */
		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onSingleTapUp(android.view.MotionEvent)
		 */
		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onScroll(android.view.MotionEvent, android.view.MotionEvent, float, float)
		 */
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			return false;
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onLongPress(android.view.MotionEvent)
		 */
		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			
		}

		/* (non-Javadoc)
		 * @see android.view.GestureDetector.OnGestureListener#onFling(android.view.MotionEvent, android.view.MotionEvent, float, float)
		 */
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
			// TODO Auto-generated method stub
			if (e1 != null && e2 != null) {

				if (e1.getX() - e2.getX() > 120) {
					viewfilpper_training_top.setInAnimation(leftInAnimation);
					viewfilpper_training_top.setOutAnimation(leftOutAnimation);
					viewfilpper_training_top.showNext();// 向右滑动
					return true;
				} else if (e1.getX() - e2.getY() < -120) {
					viewfilpper_training_top.setInAnimation(rightInAnimation);
					viewfilpper_training_top.setOutAnimation(rightOutAnimation);
					viewfilpper_training_top.showPrevious();// 向左滑动
					return true;
				}
			}
			return false;
		}
		
		@Override
		public boolean onTouchEvent(MotionEvent event) {
			// TODO Auto-generated method stub

			// listView_training.onInterceptTouchEvent(event);
			if (ViewUtil.inRangeOfView(viewfilpper_training_top, event)) {
				// AbSharedUtil.putString(context,
				// Constant.RESIDEMENU_TOUCHEVENT_TYPE, "true");
				// MyApplication.resideMenu.dispatchTouchEvent(event);
				MyApplication.recommended_vp.onInterceptTouchEvent(event);
				MyApplication.main_vp.onInterceptTouchEvent(event);
				return this.detector.onTouchEvent(event); // touch事件交给手势处理。
			}
			if (ViewUtil.inRangeOfView(MyApplication.rv_training_fargment, event)) {
				// AbSharedUtil.putString(context,
				// Constant.RESIDEMENU_TOUCHEVENT_TYPE, "true");
				// MyApplication.resideMenu.dispatchTouchEvent(event);
				// listView_training.onInterceptTouchEvent(event);
				return MyApplication.rv_training_fargment.dispatchTouchEvent(event);
			}

			AbSharedUtil.putString(context, Constant.RESIDEMENU_TOUCHEVENT_TYPE, "false");
			return false;
		}
}
