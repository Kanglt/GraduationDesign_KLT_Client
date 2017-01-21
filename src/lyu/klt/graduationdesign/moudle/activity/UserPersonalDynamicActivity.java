/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DynamicFriendsRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DynamicPPo;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.UploadUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: UserPersonalDynamicActivity 
* @Description: TODO(用户个人动态显示界面) 
* @author 康良涛 
* @date 2017年1月14日 下午7:50:05 
*  
*/
public class UserPersonalDynamicActivity extends BaseActivity {
	
	private static final String TAG = UserPersonalDynamicActivity.class
			.getSimpleName();
	private Activity context;
	
	
	public SwipeRefreshLayout swipe_refresh_widget;
	public int lastVisibleItem;

	private RecyclerView rv_user_personal_dynamic;
	private DynamicPersonalRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<DynamicPPo> dynamicPPoList;
	private List<String> mDatas;
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;



	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_user_personal_layout);
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
		context=this;
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
		
		swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);

		rv_user_personal_dynamic = (RecyclerView) findViewById(R.id.rv_user_personal_dynamic);
		
		titlebar_view = findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);
		titlebar_right_text = (TextView) titlebar_right.findViewById(R.id.title_bar_right_text);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("我的动态");
		
//		// 这句话是为了，第一次进入页面的时候显示加载进度条
//		swipe_refresh_widget.setProgressViewOffset(false, 0,
//				(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24, getResources().getDisplayMetrics()));
//		swipe_refresh_widget.setEnabled(false);
		
		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);

		// 设置Adapter
		rv_user_personal_dynamic.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_user_personal_dynamic.setLayoutManager(mLayoutManager);
		rv_user_personal_dynamic.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		rv_user_personal_dynamic.addItemDecoration(decoration);

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				UserDynamicAPI.queryUserPersonalDynamic(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), queryUserPersonalDynamicStringHttpResponseListener);
				
			}
		});


		
		title_bar_left_img.setOnClickListener(onClickListener);

	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		
		UserDynamicAPI.queryUserPersonalDynamic(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), queryUserPersonalDynamicStringHttpResponseListener);
		
	}
	
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;

			default:
				break;
			}
		}
	};
	
	private AbStringHttpResponseListener queryUserPersonalDynamicStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					dynamicPPoList= gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<DynamicPPo>>() {
							}.getType());
					
					mAdapter = new DynamicPersonalRecyclerAdapter(context, 2, dynamicPPoList);
					rv_user_personal_dynamic.setAdapter(mAdapter);
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
			AbDialogUtil.showProgressDialog(context, 0, "正在操作...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			HideProgressDialog();

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
