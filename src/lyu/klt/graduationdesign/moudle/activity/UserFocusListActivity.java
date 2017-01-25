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
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
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
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalHaveHeadRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalListAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.UserFocusListRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.bean.UserPPo;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * @ClassName: UserFocusListActivity
 * @Description: TODO(用户关注列表界面)
 * @author 康良涛
 * @date 2017年1月20日 下午3:57:52
 * 
 */
public class UserFocusListActivity extends BaseActivity {

	private static final String TAG = UserFocusListActivity.class.getSimpleName();
	private Activity context;

	public SwipeRefreshLayout swipe_refresh_widget;
	private RecyclerView rv_user_focus;
	private UserFocusListRecyclerAdapter queryUserFocusListRecyclerAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<UserPPo> userPPoList;
	private List<String> mDatas;
	private MyRecyclerAdapter recycleAdapter;
	
	private String userId;

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
		setAbContentView(R.layout.activity_user_focus_list_layout);
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
		userId=getIntent().getStringExtra("userId");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		rv_user_focus = (RecyclerView) findViewById(R.id.rv_user_focus);

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
		title_bar_text.setText("关注");

		 initRVData();
		 recycleAdapter = new MyRecyclerAdapter(context, mDatas);
		
		 // 设置Adapter
		 rv_user_focus.setAdapter(recycleAdapter);
		 mLayoutManager = new MyLinearLayoutManger(context,
		 LinearLayout.VERTICAL, false);
		 rv_user_focus.setLayoutManager(mLayoutManager);
		 rv_user_focus.setItemAnimator(new DefaultItemAnimator());
		 // 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		 SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		 rv_user_focus.addItemDecoration(decoration);
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
				UserAPI.queryUserFocusInfo(context,userId, queryUserFocusInfoStringHttpResponseListener);
			}
		});

		title_bar_left_img.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		UserAPI.queryUserFocusInfo(context,userId, queryUserFocusInfoStringHttpResponseListener);
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

	private AbStringHttpResponseListener queryUserFocusInfoStringHttpResponseListener = new AbStringHttpResponseListener() {

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
						 rv_user_focus.setAdapter(recycleAdapter);
						 recycleAdapter.notifyDataSetChanged();
						swipe_refresh_widget.setRefreshing(false);
						return;
					}

					Gson gson = new Gson();
					userPPoList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<UserPPo>>() {
					}.getType());

					queryUserFocusListRecyclerAdapter = new UserFocusListRecyclerAdapter(context,2, userPPoList);
					rv_user_focus.setAdapter(queryUserFocusListRecyclerAdapter);
					queryUserFocusListRecyclerAdapter.notifyDataSetChanged();
					
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
