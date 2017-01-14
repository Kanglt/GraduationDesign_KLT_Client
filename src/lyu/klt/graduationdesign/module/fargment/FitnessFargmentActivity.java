package lyu.klt.graduationdesign.module.fargment;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.FitnessListAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.AddTrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.UserTrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.bean.UserTotalTrainingRecordPo;
import lyu.klt.graduationdesign.moudle.activity.MainActivity.MyTouchListener;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.activity.DeleteUserTrainingActivity;
import lyu.klt.graduationdesign.moudle.activity.AddTrainingActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * 
 * @ClassName: FitnessFargmentActivity
 * @Description: TODO(健身模块Fargment)
 * @author 康良涛
 * @date 2016年12月16日 下午8:08:10
 *
 */
public class FitnessFargmentActivity extends Fragment {
	private static final String TAG = FitnessFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	public ScrollView sv_fitness;
	public SwipeRefreshLayout swipe_refresh_widget;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;
	
	

	private RecyclerView rv_userTraining;
	private UserTrainingRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	private List<TrainingDataListPo> trainingDataListPo;
	
	
	private TextView tv_fitness_my_trianing_add;
	
	private TextView tv_fitness_total_time;
	private TextView tv_fitness_total_finish_num;
	private TextView tv_fitness_cumulative_day_num;
	private TextView tv_fitness_total_consumption_num;
	
	private View ll_sub_user_training;
	
	public static boolean isRefresh=false;
	
	private UserTotalTrainingRecordPo userTotalTrainingRecordPo;
	
	
//	private ListView list_userTraining;
//	private FitnessListAdapter fitnessListAdapter;
//	
	Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			swipe_refresh_widget.setRefreshing(false);
			
		}

	};
	

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(isRefresh){
			TrainingDataPAI.getUserTrainingData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),getUserTrainingStringHttpResponseListener );
			TrainingDataPAI.getUserTrainingTotalRecord(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), getUserTrainingTotalRecordStringHttpResponseListener);
			isRefresh=false;
		}
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_fitness_layout, null);
		this.init(view);
		return view;
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
		context = this.getActivity();
		//((MainActivity) this.getActivity()).registerMyTouchListener(this);
	}

	public void initData() {

	}

	public void initView(View view) {

		titlebar_view = view.findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = view.findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) view.findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) view.findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);

		rv_userTraining = (RecyclerView) view.findViewById(R.id.rv_userTraining);
		sv_fitness = (ScrollView) view.findViewById(R.id.sv_fitness);
		
	//	list_userTraining=(ListView) view.findViewById(R.id.list_userTraining);

		
		swipe_refresh_widget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
		
		tv_fitness_my_trianing_add=(TextView) view.findViewById(R.id.tv_fitness_my_trianing_add);
		
		ll_sub_user_training=(LinearLayout)view.findViewById(R.id.ll_sub_user_training);
		
		tv_fitness_total_time=(TextView) view.findViewById(R.id.tv_fitness_total_time);
		tv_fitness_total_finish_num=(TextView) view.findViewById(R.id.tv_fitness_total_finish_num);
		tv_fitness_cumulative_day_num=(TextView) view.findViewById(R.id.tv_fitness_cumulative_day_num);
		tv_fitness_total_consumption_num=(TextView) view.findViewById(R.id.tv_fitness_total_consumption_num);
		
	}

	public void initViewData() {
		isPrepared = true;

		// title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
		// R.drawable.btn_return));
		title_bar_text.setText("健身");

		
		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);

		// 设置Adapter
		rv_userTraining.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_userTraining.setLayoutManager(mLayoutManager);
		rv_userTraining.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		rv_userTraining.addItemDecoration(decoration);
		
		
//		trainingDataListPo=new ArrayList<TrainingDataListPo>();
//		fitnessListAdapter=new FitnessListAdapter(context, trainingDataListPo);
//		list_userTraining.setAdapter(fitnessListAdapter);
		
	}

	public void initEvent() {

		
		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
//				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
//				handler.sendEmptyMessageDelayed(0, 3000);
				TrainingDataPAI.getUserTrainingData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),getUserTrainingStringHttpResponseListener );
				TrainingDataPAI.getUserTrainingTotalRecord(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), getUserTrainingTotalRecordStringHttpResponseListener);
			}
		});
		
		tv_fitness_my_trianing_add.setOnClickListener(onClickListener);
		ll_sub_user_training.setOnClickListener(onClickListener);
		
		

		
//		rv_userTraining.setOnTouchListener(new OnTouchListener() {
//	        @Override
//	        public boolean onTouch(View v, MotionEvent event) {
//	            switch (event.getAction()) {
//	            case MotionEvent.ACTION_DOWN:
//	                break;
//	            case MotionEvent.ACTION_MOVE:
//	                if (v.getScrollY() <= 0) {
//	                    Log.i("scroll view", "top");
//	                    return sv_fitness.onTouchEvent(event);
//	                }
////	                } else if (sv_fitness.getChildAt(0).getMeasuredHeight() <= v.getHeight() + v.getScrollY()) {
//////	                    Log.i("scroll view", "bottom");
//////	                    Log.i("scroll view", "view.getMeasuredHeight() = " + sv_fitness.getMeasuredHeight()
//////	                        + ", v.getHeight() = " + v.getHeight()
//////	                        + ", v.getScrollY() = " + v.getScrollY()
//////	                        + ", view.getChildAt(0).getMeasuredHeight() = " + sv_fitness.getChildAt(0).getMeasuredHeight());
////	                	
////	                	return rv_userTraining.onTouchEvent(event);
////	                }
//	                break;
//	            case MotionEvent.ACTION_UP:
//	            	
//	            	break;
//	            default:
//	                break;
//	            }
//	            return false;
//	 } });
		
	}

	public void startGame() {
		
		TrainingDataPAI.getUserTrainingData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),getUserTrainingStringHttpResponseListener );
		TrainingDataPAI.getUserTrainingTotalRecord(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), getUserTrainingTotalRecordStringHttpResponseListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.tv_fitness_my_trianing_add:
				intent.setClass(context, AddTrainingActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_sub_user_training:
				intent.setClass(context, DeleteUserTrainingActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}

	};

	private AbStringHttpResponseListener getUserTrainingStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					trainingDataListPo = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<TrainingDataListPo> >() {
					}.getType());

					mAdapter = new UserTrainingRecyclerAdapter(context, 1, trainingDataListPo);

					rv_userTraining.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
					
//					fitnessListAdapter=new FitnessListAdapter(context, trainingDataListPo);
//					list_userTraining.setAdapter(fitnessListAdapter);
//					fitnessListAdapter.notifyDataSetChanged();
//					ViewUtil.setListViewHeightBasedOnChildren(list_userTraining);
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
			swipe_refresh_widget.setRefreshing(false);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};
	
	
	private AbStringHttpResponseListener getUserTrainingTotalRecordStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
						return;
					}
					
					userTotalTrainingRecordPo=GsonUtils.getObj(jsonObject.getString("record"), UserTotalTrainingRecordPo.class);
					
					tv_fitness_total_time.setText(userTotalTrainingRecordPo.getTotalTime()+"");
					tv_fitness_total_finish_num.setText(userTotalTrainingRecordPo.getTotalNum()+"");
					tv_fitness_cumulative_day_num.setText(userTotalTrainingRecordPo.getTotalDay()+"");
					tv_fitness_total_consumption_num.setText(userTotalTrainingRecordPo.getTotalCalories()+"");
					
					
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
			swipe_refresh_widget.setRefreshing(false);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};

	/*
	 * (non-Javadoc)
	 * 
	 * @see lyu.klt.graduationdesign.module.main.MainActivity.MyTouchListener#
	 * onTouchEvent(android.view.MotionEvent)
	 */
	// @Override
	// public boolean onTouchEvent(MotionEvent event) {
	// // TODO Auto-generated method stub
	// if(ViewUtil.inRangeOfView(sv_fitness,event)){
	// AbSharedUtil.putString(context, Constant.RESIDEMENU_TOUCHEVENT_TYPE,
	// "true");
	// MyApplication.resideMenu.dispatchTouchEvent(event);
	// return sv_fitness.dispatchTouchEvent(event);
	// }
	//
	// AbSharedUtil.putString(context, Constant.RESIDEMENU_TOUCHEVENT_TYPE,
	// "false");
	//
	// return false;
	// }
	
	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}


	

	
}
