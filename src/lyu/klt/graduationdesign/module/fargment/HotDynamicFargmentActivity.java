package lyu.klt.graduationdesign.module.fargment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicFriendsRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicHotRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.TrainingListAdapter;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
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
	private String[] yearArray = { "测试数据1", "测试数据2", "测试数据3", "测试数据4", "测试数据5", "测试数据6", "测试数据7", "测试数据8", "测试数据9", "测试数据10", "测试数据11", "测试数据12" };

	
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

		

		mLayoutManager = new MyLinearLayoutManger(context,LinearLayout.VERTICAL,false);
		rv_dynamic_hot.setLayoutManager(mLayoutManager);

		mAdapter = new DynamicHotRecyclerAdapter(context, 2, yearArray);
		rv_dynamic_hot.setAdapter(mAdapter);
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
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		});

		rv_dynamic_hot.setOnScrollListener(new RecyclerView.OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
				if (newState == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItem + 1 == mAdapter.getItemCount()) {
					swipe_refresh_widget.setRefreshing(true);
					// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
					handler.sendEmptyMessageDelayed(0, 3000);
				}
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				super.onScrolled(recyclerView, dx, dy);
				lastVisibleItem = mLayoutManager.findLastVisibleItemPosition();
			}
		});
		
	}

	public void startGame() {
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



	



}
