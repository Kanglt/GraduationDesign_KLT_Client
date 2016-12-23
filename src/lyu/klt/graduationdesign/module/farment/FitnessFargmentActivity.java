package lyu.klt.graduationdesign.module.farment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.graduationdesign.module.adapter.DietListAdapter;
import lyu.klt.graduationdesign.module.adapter.FitnessListAdapter;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.ViewUtil;

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

	public ListView listView_fitness;
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
		// ((MainActivity) this.getActivity()).registerMyTouchListener(this);
	}

	public void initData() {

	}

	public void initView(View view) {

		titlebar_view = view.findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = view.findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) view.findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) view.findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);

		sv_fitness = (ScrollView) view.findViewById(R.id.sv_fitness);
		listView_fitness = (ListView) view.findViewById(R.id.listView_fitness);
		
		swipe_refresh_widget = (SwipeRefreshLayout) view.findViewById(R.id.swipe_refresh_widget);
	}

	public void initViewData() {
		isPrepared = true;

		// title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
		// R.drawable.btn_return));
		title_bar_text.setText("健身");

		// listView_fitness.setFocusable(false);
		listView_fitness.setAdapter(new FitnessListAdapter(context, listdata()));
		listView_fitness.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

			}
		});
		ViewUtil.setListViewHeightBasedOnChildren(listView_fitness);
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

	public List<HashMap<String, Object>> listdata() {
		List<HashMap<String, Object>> list = new ArrayList<HashMap<String, Object>>();
		HashMap<String, Object> hm = new HashMap<String, Object>();
		hm.put("list_text", "请选择");
		list.add(hm);
		hm = new HashMap<String, Object>();
		hm.put("list_text", "康良涛");
		list.add(hm);

		hm = new HashMap<String, Object>();
		hm.put("list_text", "康良仁");
		list.add(hm);

		hm = new HashMap<String, Object>();
		hm.put("list_text", "康俊华");
		list.add(hm);

		hm = new HashMap<String, Object>();
		hm.put("list_text", "康露燕");
		list.add(hm);

		return list;
	}

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
}
