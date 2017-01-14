package lyu.klt.graduationdesign.module.fargment;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView;
import lyu.klt.frame.ab.view.pullview.AbPullToRefreshView.OnHeaderRefreshListener;
import lyu.klt.graduationdesign.module.adapter.DynamicFriendsRecyclerAdapter;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * 
 * @ClassName: FriendsDynamicFargmentActivity
 * @Description: TODO(动态模块下的子模块（好友动态）的Fargment)
 * @author 康良涛
 * @date 2016年12月15日 上午8:43:22
 *
 */
public class FriendsDynamicFargmentActivity extends Fragment {
	private static final String TAG = FriendsDynamicFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;

	public SwipeRefreshLayout swipe_refresh_widget;
	public int lastVisibleItem;

	private RecyclerView rv_dynamic_friends;
	private DynamicFriendsRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private String[] yearArray = { "测试数据1", "测试数据2", "测试数据3", "测试数据4", "测试数据5", "测试数据6", "测试数据7", "测试数据8", "测试数据9",
			"测试数据10", "测试数据11", "测试数据12" };

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
		View view = inflater.inflate(R.layout.fargment_dynamic_friends_layout, null);
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
		// mPulltorefreshview = (AbPullToRefreshView)
		// view.findViewById(R.id.mPulltorefreshview);
		swipe_refresh_widget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);

		rv_dynamic_friends = (RecyclerView) view.findViewById(R.id.rv_dynamic_friends);

	}

	public void initViewData() {
		isPrepared = true;

//		// 这句话是为了，第一次进入页面的时候显示加载进度条
//		swipe_refresh_widget.setProgressViewOffset(false, 0,
//				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
//		swipe_refresh_widget.setEnabled(false);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_dynamic_friends.setLayoutManager(mLayoutManager);

		mAdapter = new DynamicFriendsRecyclerAdapter(context, 2, yearArray);
		rv_dynamic_friends.setAdapter(mAdapter);
		rv_dynamic_friends.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(5);
		rv_dynamic_friends.addItemDecoration(decoration);

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

		rv_dynamic_friends.setOnScrollListener(new RecyclerView.OnScrollListener() {

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
