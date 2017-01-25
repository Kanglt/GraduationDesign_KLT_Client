/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
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
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DietReducedFatRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.TotalTrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.UserDietRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.DietDataPAI;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: UserDietActivity 
* @Description: TODO(用户饮食收藏界面) 
* @author 康良涛 
* @date 2017年1月25日 上午10:34:32 
*  
*/
public class UserDietActivity extends BaseActivity {

	private static final String TAG = UserDietActivity.class
			.getSimpleName();
	private Activity context;
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;
	
	public SwipeRefreshLayout swipe_refresh_widget;
	private RecyclerView rv_user_diet;
	private UserDietRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	private List<DietDataPo> dietDataList;
	private MyRecyclerAdapter recycleAdapter;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_user_diet_layout);
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
		
		titlebar_view = findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);
		
		rv_user_diet=(RecyclerView) findViewById(R.id.rv_user_diet);
		swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("饮食收藏");
		
		
		initRVData();
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);

		// 设置Adapter
		rv_user_diet.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_user_diet.setLayoutManager(mLayoutManager);
		rv_user_diet.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		rv_user_diet.addItemDecoration(decoration);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img.setOnClickListener(onClickListener);
		
		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				//handler.sendEmptyMessageDelayed(0, 3000);
				DietDataPAI.queryUserDiet(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), queryUserDietStringHttpResponseListener);
				
			}
		});
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		DietDataPAI.queryUserDiet(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), queryUserDietStringHttpResponseListener);
	}
	
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
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
	
	private AbStringHttpResponseListener queryUserDietStringHttpResponseListener = new AbStringHttpResponseListener() {

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
						rv_user_diet.setAdapter(recycleAdapter);
						recycleAdapter.notifyDataSetChanged();
						swipe_refresh_widget.setRefreshing(false);
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					dietDataList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<DietDataPo>>() {
					}.getType());
					mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
					rv_user_diet.setLayoutManager(mLayoutManager);
					mAdapter = new UserDietRecyclerAdapter(context, 1, dietDataList);

					rv_user_diet.setAdapter(mAdapter);
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
}
