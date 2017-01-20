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
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import lyu.klt.frame.photoselector.ui.PhotoItem.onItemClickListener;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalHaveHeadRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalListAdapter;
import lyu.klt.graduationdesign.module.adapter.DynamicPersonalRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: UserHomePage 
* @Description: TODO(用户主页) 
* @author 康良涛 
* @date 2017年1月19日 下午9:21:11 
*  
*/
public class UserHomePageActivity extends BaseActivity {
	private static final String TAG = UserHomePageActivity.class
			.getSimpleName();
	private Activity context;
	
	public SwipeRefreshLayout swipe_refresh_widget;
	public int lastVisibleItem;

	private ListView rv_user_personal_dynamic;
	private DynamicPersonalHaveHeadRecyclerAdapter mAdapter;
	private DynamicPersonalListAdapter dynamicPersonalListAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<DynamicPo> dynamicPoList;
	private List<String> mDatas;
	private UserPo userPo;
	
	private View listView_head;
	
	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;
	
	private ImageView iv_information_user_picture;
	private TextView tv_userName,tv_focus_num,tv_fans_num;
	
	String userId;
	
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
		setAbContentView(R.layout.activity_user_home_page_layout);
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
		userId=getIntent().getStringExtra("userId");
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
		

		
		
		swipe_refresh_widget=(SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		rv_user_personal_dynamic = (ListView) findViewById(R.id.rv_user_personal_dynamic);
		listView_head=LayoutInflater.from(context).inflate(R.layout.recycleview_list_item_head,	null);
		
		titlebar_view = listView_head.findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = listView_head.findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) listView_head.findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) listView_head.findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);
		titlebar_right_text = (TextView) titlebar_right.findViewById(R.id.title_bar_right_text);
		
		iv_information_user_picture=(ImageView) listView_head.findViewById(R.id.iv_information_user_picture);
		tv_userName=(TextView) listView_head.findViewById(R.id.tv_userName);
		tv_focus_num=(TextView) listView_head.findViewById(R.id.tv_focus_num);
		tv_fans_num=(TextView) listView_head.findViewById(R.id.tv_fans_num);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		

		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return));
		title_bar_text.setVisibility(View.GONE);
		
		
		
//		initRVData();
//		MyRecyclerAdapter recycleAdapter;
//		recycleAdapter = new MyRecyclerAdapter(context, mDatas);
//
//		// 设置Adapter
//		rv_user_personal_dynamic.setAdapter(recycleAdapter);
//		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
//		rv_user_personal_dynamic.setLayoutManager(mLayoutManager);
//		rv_user_personal_dynamic.setItemAnimator(new DefaultItemAnimator());
//		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
//		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
//		rv_user_personal_dynamic.addItemDecoration(decoration);
		
		rv_user_personal_dynamic.addHeaderView(listView_head);
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
				handler.sendEmptyMessageDelayed(0, 3000);
			}
		});
		
		title_bar_left_img.setOnClickListener(onClickListener);
		tv_focus_num.setOnClickListener(onClickListener);
		tv_fans_num.setOnClickListener(onClickListener);
		
	}
	
	
	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		
		UserDynamicAPI.queryUserPersonalDynamic(context, userId, queryUserPersonalDynamicStringHttpResponseListener);
		UserAPI.queryUserHomePageInfo(context, userId, queryUserHomePageInfoStringHttpResponseListener);
	}
	
	
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.tv_focus_num:
				intent.setClass(context, UserFocusListActivity.class);
				intent.putExtra("userId", userPo.getUserId());
				startActivity(intent);
				break;
			case R.id.tv_fans_num:
				intent.setClass(context, UserFansListActivity.class);
				intent.putExtra("userId", userPo.getUserId());
				startActivity(intent);
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
					dynamicPoList= gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<DynamicPo>>() {
							}.getType());
					
					dynamicPersonalListAdapter = new DynamicPersonalListAdapter(context, dynamicPoList);
					rv_user_personal_dynamic.setAdapter(dynamicPersonalListAdapter);
					dynamicPersonalListAdapter.notifyDataSetChanged();
					
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
	
	private AbStringHttpResponseListener queryUserHomePageInfoStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					
					Gson gson=new Gson();
					userPo= gson.fromJson(jsonObject.getString("record"),
							new TypeToken<UserPo>() {
							}.getType());
					
					String strArr2[] = userPo.getUserPhoto().split("/");
					String fileId2 = strArr2[strArr2.length - 1];
					ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + fileId2,
							iv_information_user_picture, imageLoadingListener);
					
					tv_userName.setText(userPo.getUserName());
					tv_fans_num.setText("粉丝 "+userPo.getFansNum()+"");
					tv_focus_num.setText("关注 "+userPo.getFocusNum()+"");
					
					
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
	//		HideProgressDialog();

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
	
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("UserHomePageActivity", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("UserHomePageActivity", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("UserHomePageActivity", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("UserHomePageActivity", "onLoadingCancelled");
		}
	};

}
