/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import lyu.klt.graduationdesign.module.adapter.DynamicHomgPageListAdapter;
import lyu.klt.graduationdesign.module.bean.DynamicCommentsPo;
import lyu.klt.graduationdesign.module.bean.DynamicPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;

/**
 * @ClassName: DynamicHomePageActivity
 * @Description: TODO(动态正文界面)
 * @author 康良涛
 * @date 2017年1月22日 下午8:20:03
 * 
 */
public class DynamicHomePageActivity extends BaseActivity {

	private static final String TAG = DynamicHomePageActivity.class.getSimpleName();
	private Activity context;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;

	public SwipeRefreshLayout swipe_refresh_widget;
	private ListView lv_dynamic_home_page;
	private DynamicHomgPageListAdapter dynamicPersonalListAdapter;
	private List<DynamicCommentsPo> dynamicCommentsPoList;
	private DynamicPo dynamicPo;

	public ImageView iv_dynamic_user_picture;
	public TextView tv_dynamic_user_name;
	public TextView tv_dynamic_time;
	public TextView tv_dynamic_content;
	public ImageView iv_dynamic_picture;
	public TextView tv_dynamic_forwarding_num;
	public TextView tv_dynamic_comments_num;
	public TextView tv_dynamic_thumb_up_num;

	public TextView tv_dynamic_comments_num2;
	public TextView tv_dynamic_thumb_up_num2;

	public RelativeLayout rl_dynamic_forwarding;
	public RelativeLayout rl_dynamic_comments;
	public RelativeLayout rl_dynamic_thumb_up;
	public TextView tv_dynamic_thumb_up_num_text;
	
	private DynamicCommentsPo dynamicCommentsPo;

	// 标志位，数据重新加载。
	private boolean isLoadedDate;

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isLoadedDate = AbSharedUtil.getBoolean(context, Constant.ISLOADEDDATE, false);
		if (isLoadedDate) {
			UserDynamicAPI.queryDynamicComments(context, dynamicPo.getId() + "",
					queryUserPersonalDynamicStringHttpResponseListener);
			AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, false);
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_dynamic_home_page_layout);
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
		dynamicPo = (DynamicPo) getIntent().getSerializableExtra("dynamicPo");
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
		titlebar_right_text = (TextView) titlebar_right.findViewById(R.id.title_bar_right_text);

		swipe_refresh_widget = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_widget);
		lv_dynamic_home_page = (ListView) findViewById(R.id.lv_dynamic_home_page);

		tv_dynamic_user_name = (TextView) findViewById(R.id.tv_dynamic_user_name);
		tv_dynamic_time = (TextView) findViewById(R.id.tv_dynamic_time);
		tv_dynamic_content = (TextView) findViewById(R.id.tv_dynamic_content);
		tv_dynamic_comments_num = (TextView) findViewById(R.id.tv_dynamic_comments_num);
		tv_dynamic_thumb_up_num = (TextView) findViewById(R.id.tv_dynamic_thumb_up_num);
		iv_dynamic_user_picture = (ImageView) findViewById(R.id.iv_dynamic_user_picture);
		iv_dynamic_picture = (ImageView) findViewById(R.id.iv_dynamic_picture);

		tv_dynamic_comments_num2 = (TextView) findViewById(R.id.tv_dynamic_comments_num2);
		tv_dynamic_thumb_up_num2 = (TextView) findViewById(R.id.tv_dynamic_thumb_up_num2);

		rl_dynamic_comments = (RelativeLayout) findViewById(R.id.rl_dynamic_comments);
		rl_dynamic_thumb_up = (RelativeLayout) findViewById(R.id.rl_dynamic_thumb_up);
		tv_dynamic_thumb_up_num_text = (TextView) findViewById(R.id.tv_dynamic_thumb_up_num_text);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("动态正文");

		tv_dynamic_user_name.setText(dynamicPo.getUserName());
		tv_dynamic_time.setText(dynamicPo.getDynamicDate());
		tv_dynamic_content.setText(dynamicPo.getDynamicText());
		 tv_dynamic_comments_num.setText("评论"+dynamicPo.getDynamicCommentsNum());
		 tv_dynamic_thumb_up_num.setText("赞"+dynamicPo.getDynamicThumbUpNum());
		tv_dynamic_comments_num2.setText("" + dynamicPo.getDynamicCommentsNum());
		tv_dynamic_thumb_up_num2.setText("" + dynamicPo.getDynamicThumbUpNum());

		if(dynamicPo.getIsThumbUp()==1){
			tv_dynamic_thumb_up_num2.setTextColor(android.graphics.Color.parseColor("#FF0000"));
			tv_dynamic_thumb_up_num_text.setTextColor(android.graphics.Color.parseColor("#FF0000"));
		}
		
		if (!dynamicPo.getDynamicImage().equals("isEmpty")) {
			String strArr1[] = dynamicPo.getDynamicImage().split("/");
			String fileId = strArr1[strArr1.length - 1];
			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_DYNAMICIMAGE_URL + fileId,
					iv_dynamic_picture, imageLoadingListener);
		} else {
			iv_dynamic_picture.setVisibility(View.GONE);
		}

		String strArr2[] = dynamicPo.getUserPhoto().split("/");
		String fileId2 = strArr2[strArr2.length - 1];
		ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + fileId2, iv_dynamic_user_picture,
				imageLoadingListener);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img.setOnClickListener(onClickListener);
		rl_dynamic_thumb_up.setOnClickListener(onClickListener);
		rl_dynamic_comments.setOnClickListener(onClickListener);

		swipe_refresh_widget.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {
				// TODO Auto-generated method stub
				swipe_refresh_widget.setRefreshing(true);
				// 此处在现实项目中，请换成网络请求数据代码，sendRequest .....
				UserDynamicAPI.queryDynamicComments(context, dynamicPo.getId() + "",
						queryUserPersonalDynamicStringHttpResponseListener);
			}
		});
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

		UserDynamicAPI.queryDynamicComments(context, dynamicPo.getId() + "",
				queryUserPersonalDynamicStringHttpResponseListener);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.rl_dynamic_comments:
				dynamicCommentsPo=dynamicCommentsPoList.get(0);
				dynamicCommentsPo.setCommentsUserName("@FIRSTCOMMENTS@");
				dynamicCommentsPo.setCommentsUserId(dynamicCommentsPo.getReleaseUserId());
				Intent intent=new Intent(context,ReplyCommentsActivity.class);
				intent.putExtra("dynamicCommentsPo", dynamicCommentsPo);
				startActivity(intent);
				break;
			case R.id.rl_dynamic_thumb_up:
				if (dynamicPo.getIsThumbUp() == 0) {
					int thumb_up_num = Integer.parseInt(tv_dynamic_thumb_up_num2.getText().toString());
					tv_dynamic_thumb_up_num.setText("赞"+(thumb_up_num + 1));
					tv_dynamic_thumb_up_num2.setText((thumb_up_num + 1) + "");
					tv_dynamic_thumb_up_num2.setTextColor(android.graphics.Color.parseColor("#FF0000"));
					tv_dynamic_thumb_up_num_text.setTextColor(android.graphics.Color.parseColor("#FF0000"));
					dynamicPo.setIsThumUp(1);
					UserDynamicAPI.updateUserDynamicThumbUpNum(context,
							AbSharedUtil.getString(context, Constant.LAST_LOGINID),
							dynamicPo.getId() + "", "0",
							updateUserDynamicThumbUpNumStringHttpResponseListener);
				} else if (dynamicPo.getIsThumbUp() == 1) {
					int thumb_up_num = Integer.parseInt(tv_dynamic_thumb_up_num2.getText().toString());
					tv_dynamic_thumb_up_num.setText("赞"+(thumb_up_num - 1));
					tv_dynamic_thumb_up_num2.setText((thumb_up_num - 1) + "");
					tv_dynamic_thumb_up_num2.setTextColor(android.graphics.Color.parseColor("#000000"));
					tv_dynamic_thumb_up_num_text.setTextColor(android.graphics.Color.parseColor("#000000"));
					dynamicPo.setIsThumUp(0);
					UserDynamicAPI.updateUserDynamicThumbUpNum(context,
							AbSharedUtil.getString(context, Constant.LAST_LOGINID),
							dynamicPo.getId() + "", "1",
							updateUserDynamicThumbUpNumStringHttpResponseListener);
				}
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

					Gson gson = new Gson();
					dynamicCommentsPoList = gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<DynamicCommentsPo>>() {
							}.getType());

					dynamicPersonalListAdapter = new DynamicHomgPageListAdapter(context, dynamicCommentsPoList);
					lv_dynamic_home_page.setAdapter(dynamicPersonalListAdapter);
					dynamicPersonalListAdapter.notifyDataSetChanged();
					ViewUtil.setListViewHeightBasedOnChildren(lv_dynamic_home_page);
					swipe_refresh_widget.setRefreshing(false);

					tv_dynamic_comments_num.setText("评论" + dynamicCommentsPoList.get(0).getDynamicCommentsNum());
					tv_dynamic_thumb_up_num.setText("赞" + dynamicCommentsPoList.get(0).getDynamicThumbUpNum());
					
					tv_dynamic_comments_num2.setText("" + dynamicCommentsPoList.get(0).getDynamicCommentsNum());
					tv_dynamic_thumb_up_num2.setText("" + dynamicCommentsPoList.get(0).getDynamicThumbUpNum());
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

	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DynamicPersonalRecyclerAdapter", "onLoadingCancelled");
		}
	};
	
	private AbStringHttpResponseListener updateUserDynamicThumbUpNumStringHttpResponseListener = new AbStringHttpResponseListener() {

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
			// AbDialogUtil.showProgressDialog(context, 0, "正在操作...");

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
			// AbToastUtil.showToast(context, error.getMessage());
		}

	};
}
