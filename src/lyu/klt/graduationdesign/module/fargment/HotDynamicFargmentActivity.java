package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.ViewFlipper;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.adapter.DynamicFriendsRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicHotRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.MyStaggeredGridLayoutManager;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * 
 * @ClassName: HotDynamicFargmentActivity
 * @Description: TODO(动态模块下的子模块（热门动态）的Fargment)
 * @author 康良涛
 * @date 2016年12月15日 上午8:43:22
 *
 */
public class HotDynamicFargmentActivity extends Fragment {
	private static final String TAG = HotDynamicFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	public SwipeRefreshLayout swipe_refresh_widget;
	public int lastVisibleItem;
	
	
	private RecyclerView rv_dynamic_hot;
	private DynamicHotRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<DynamicPo> DynamicPoList;
	private List<String> mDatas;

	
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
		View view = inflater.inflate(R.layout.fargment_dynamic_hot_layout, null);
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

	}

	public void initData() {

	}

	public void initView(View view) {
		
		swipe_refresh_widget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
		rv_dynamic_hot = (RecyclerView) view.findViewById(R.id.rv_dynamic_hot);
		
		

	}

	public void initViewData() {
		isPrepared = true;

		

		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);
		rv_dynamic_hot.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_dynamic_hot.setLayoutManager(mLayoutManager);
		rv_dynamic_hot.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		rv_dynamic_hot.addItemDecoration(decoration);
	}

	public void initEvent() {
		

		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				UserDynamicAPI.queryHotDynamic(context, queryHotDynamicStringHttpResponseListener);
			}
		});

//		rv_dynamic_hot.setOnScrollListener(new RecyclerView.OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
//					swipe_refresh_widget.setRefreshing(true);
//					// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
//					handler.sendEmptyMessageDelayed(0, 3000);
//				}
//			}
//
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				super.onScrolled(recyclerView, dx, dy);
//				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
//			}
//		});
		
	}

	public void startGame() {
		UserDynamicAPI.queryHotDynamic(context, queryHotDynamicStringHttpResponseListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {

			default:
				break;
			}
		}

	};

	
	private AbStringHttpResponseListener queryHotDynamicStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					
					Gson gson=new Gson();
					DynamicPoList= gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<DynamicPo>>() {
							}.getType());
					
					mAdapter = new DynamicHotRecyclerAdapter(context, 2, DynamicPoList);
					rv_dynamic_hot.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
					
					swipe_refresh_widget.setRefreshing(false);
					
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
		//	AbDialogUtil.showProgressDialog(context, 0, "正在操作...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			//HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};

	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}

	



}
