package lyu.klt.graduationdesign.module.main;



import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.po.UserPo;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.FargmentTabUtil;
import lyu.klt.graduationdesign.util.ImageViewUtils;





public class MainActivityTest extends BaseActivity {
	private static final String TAG = MainActivityTest.class
			.getSimpleName();
	private Activity context;
	
	public FargmentTabUtil fargmentTabUtil;
	
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_main);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
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
		fargmentTabUtil=new FargmentTabUtil(context);
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
		titlebar_right = (LinearLayout) titlebar_view
				.findViewById(R.id.title_bar_right_layout);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		ImageViewUtils.setImageViewWidth_div(context, title_bar_left_img,15);
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
				R.drawable.title_bar_menu_on));
		
		
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img_layout.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	
	
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {		
			case R.id.title_bar_left_img_layout:
				intent.setClass(MainActivityTest.this, MainActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
		
	};
	
	private AbStringHttpResponseListener abStringHttpResponseListener = new AbStringHttpResponseListener() {
		// 获取数据成功会调用这里
		@Override
		public void onSuccess(int statusCode, String content) {
			Log.d(TAG, "onSuccess");
			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
//					if (!ApiHandler.isSccuss((Activity) context, type, data)) {
//						return;
//					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);
					Gson gson = GsonUtils.getGson();
					List<UserPo> userPos = gson.fromJson(
							jsonObject.getString("list"),
							new TypeToken<List<UserPo>>() {
							}.getType());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};

		// 开始执行前
		@Override
		public void onStart() {
			Log.d(TAG, "onStart");
			// 显示进度框
			 AbDialogUtil.showProgressDialog(context, 0, "正在提交...");
		}

		// 失败，调用
		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			Log.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

		// 完成后调用，失败，成功
		@Override
		public void onFinish() {
			Log.d(TAG, "onFinish");
			// 移除进度框
			HideProgressDialog();
		};

	};
	
	
	

}
