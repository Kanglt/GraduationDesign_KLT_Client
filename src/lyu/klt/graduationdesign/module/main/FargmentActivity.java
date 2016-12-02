package lyu.klt.graduationdesign.module.main;

import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ViewFlipper;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.po.UserPo;
import lyu.klt.graduationdesign.moudle.api.TestAPI;
import lyu.klt.graduationdesign.moudle.login.LoginActivity;
import lyu.klt.graduationdesign.util.DataUtils;

@SuppressLint("NewApi")
public class FargmentActivity extends Fragment {
	private static final String TAG = FargmentActivity.class
			.getSimpleName();
	private Activity context;
	
	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;
	
	public Button btn_test;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
//		View view = inflater.inflate(R.layout.message_layout,
//				container, false);
		View view = inflater.inflate(R.layout.fargment_layout, null);
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
	}

	public void initData() {

	}

	public void initView(View view) {
		btn_test=(Button) view.findViewById(R.id.btn_test);
	}

	public void initViewData() {
		isPrepared = true;
	}
	
	public void initEvent() {
		btn_test.setOnClickListener(onClickListener);
	}

	public void startGame() {		
	}

	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.btn_test:
				intent.setClass(context, LoginActivity.class);
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
					btn_test.setText(userPos.get(0).getUserName()+userPos.get(0).getUserPassword());
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
			//HideProgressDialog();
			AbDialogUtil.removeDialog(context);
		};

	};
	
}
