package lyu.klt.graduationdesign.module.fargment;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.app.Fragment;
import android.content.ContentValues;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.GestureDetector.OnGestureListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.FitnessListAdapter;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.DietInfomation;
import lyu.klt.graduationdesign.moudle.activity.EditPasswordActivity;
import lyu.klt.graduationdesign.moudle.activity.HealthDetectionActivity;
import lyu.klt.graduationdesign.moudle.activity.HeartRateDetectionActivity;
import lyu.klt.graduationdesign.moudle.activity.InfomationActivity;
import lyu.klt.graduationdesign.moudle.activity.LoginActivity;
import lyu.klt.graduationdesign.moudle.activity.MainActivity;
import lyu.klt.graduationdesign.moudle.activity.SettingActivity;
import lyu.klt.graduationdesign.moudle.activity.TestActivity;
import lyu.klt.graduationdesign.moudle.activity.UserFansListActivity;
import lyu.klt.graduationdesign.moudle.activity.UserFocusListActivity;
import lyu.klt.graduationdesign.moudle.activity.UserPersonalDynamicActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;

/**
 * 
 * @ClassName: PersonalFargmentActivity
 * @Description: TODO(个人模块Fargment)
 * @author 康良涛
 * @date 2016年12月16日 下午8:08:10
 *
 */
public class PersonalFargmentActivity extends Fragment {
	private static final String TAG = PersonalFargmentActivity.class.getSimpleName();
	private Activity context;

	// 主要用于消除预加载
	protected boolean isVisible;
	// 标志位，View已经初始化完成。
	private boolean isPrepared;
	// 标志位，数据已经加载过了。
	private boolean isLoaded;
	// 标志位，数据重新加载。
	private boolean isLoadedDate;

	public ScrollView sv_fitness;
	
	private FileInputStream in = null;
	private Bitmap myBitmap;
	
	private ImageView user_picture;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;

	private Button btn_personal_exit;

	private TextView tv_user_name,tv_user_password,tv_personal_dynamic_num,tv_personal_focus_num,tv_personal_fans_num;
	private View rl_personal_dynamic,rl_personal_fans,rl_personal_focus;

	public View user_information;
	public View ll_setting;

	public UserPo userPo;
	
	private String[] photoNameArray;
	private String photoName;	
	
	
	private View ll_diet_collection,ll_healthDetection;

	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// View view = inflater.inflate(R.layout.message_layout,
		// container, false);
		View view = inflater.inflate(R.layout.fargment_personal_layout, null);
		init(view);
		return view;
	}

	



	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		isLoadedDate = AbSharedUtil.getBoolean(context, Constant.ISLOADEDDATE, false);
		if (isLoadedDate) {
			if(AbSharedUtil.getBoolean(context, Constant.ISUPDATEUSERPHOTO, false)){
				initUserHead();
				AbSharedUtil.putBoolean(context, Constant.ISUPDATEUSERPHOTO, false);
			}
//			UserAPI.userInformationForMobile(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
//					userInformationStringHttpResponseListener);
			UserAPI.queryUserHomePageInfo(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
					queryUserHomePageInfoStringHttpResponseListener);
			AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, false);
		}
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
		user_information = (RelativeLayout) view.findViewById(R.id.user_information);

		btn_personal_exit = (Button) view.findViewById(R.id.btn_personal_exit);
		tv_user_name = (TextView) view.findViewById(R.id.tv_user_name);
		
		tv_user_password= (TextView) view.findViewById(R.id.tv_user_password);
		
		tv_personal_dynamic_num= (TextView) view.findViewById(R.id.tv_personal_dynamic_num);
		tv_personal_focus_num= (TextView) view.findViewById(R.id.tv_personal_focus_num);
		tv_personal_fans_num= (TextView) view.findViewById(R.id.tv_personal_fans_num);
		
		user_picture=(ImageView) view.findViewById(R.id.user_picture);
		
		ll_diet_collection=(LinearLayout)view.findViewById(R.id.ll_diet_collection);
		ll_healthDetection=(LinearLayout)view.findViewById(R.id.ll_healthDetection);
		
		rl_personal_dynamic=(RelativeLayout)view.findViewById(R.id.rl_personal_dynamic);
		rl_personal_fans=(RelativeLayout)view.findViewById(R.id.rl_personal_fans);
		rl_personal_focus=(RelativeLayout)view.findViewById(R.id.rl_personal_focus);
		
		ll_setting=(LinearLayout)view.findViewById(R.id.ll_setting);
	}

	public void initViewData() {
		isPrepared = true;

		// title_bar_left_img.setImageDrawable(context.getResources().getDrawable(
		// R.drawable.btn_return));
		title_bar_text.setText("个人");
		// listView_fitness.setFocusable(false);

	}

	public void initEvent() {

		user_information.setOnClickListener(onClickListener);
		btn_personal_exit.setOnClickListener(onClickListener);
		tv_user_password.setOnClickListener(onClickListener);
		ll_diet_collection.setOnClickListener(onClickListener);
		ll_healthDetection.setOnClickListener(onClickListener);
		rl_personal_dynamic.setOnClickListener(onClickListener);
		rl_personal_fans.setOnClickListener(onClickListener);
		rl_personal_focus.setOnClickListener(onClickListener);
		ll_setting.setOnClickListener(onClickListener);
	}

	public void startGame() {
//		UserAPI.userInformationForMobile(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
//				userInformationStringHttpResponseListener);
		UserAPI.queryUserHomePageInfo(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
				queryUserHomePageInfoStringHttpResponseListener);

	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.user_information:
				intent.setClass(context, InfomationActivity.class);
				intent.putExtra("userPo", (Serializable) userPo);
				startActivity(intent);
				break;
			case R.id.btn_personal_exit:
				intent.setClass(context, LoginActivity.class);
				startActivity(intent);
				AbSharedUtil.putBoolean(context, Constant.ISLOGIN, false);
				MyApplication.getInstance().exit();
				break;
			case R.id.tv_user_password:
				intent.putExtra("userPo", (Serializable)userPo);
				intent.setClass(context, EditPasswordActivity.class);
				startActivity(intent);
				break;
			case R.id.ll_diet_collection:
					
				
				break;
			case R.id.ll_healthDetection:
				intent.setClass(context, HealthDetectionActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_personal_dynamic:
				intent.setClass(context, UserPersonalDynamicActivity.class);
				startActivity(intent);
				break;
			case R.id.rl_personal_focus:
				intent.setClass(context, UserFocusListActivity.class);
				intent.putExtra("userId", AbSharedUtil.getString(context, Constant.LAST_LOGINID));
				startActivity(intent);
				break;
			case R.id.rl_personal_fans:
				intent.setClass(context, UserFansListActivity.class);
				intent.putExtra("userId", AbSharedUtil.getString(context, Constant.LAST_LOGINID));
				startActivity(intent);
				break;
				
			case R.id.ll_setting:
				intent.setClass(context, SettingActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}

	};

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

//	private AbStringHttpResponseListener userInformationStringHttpResponseListener = new AbStringHttpResponseListener() {
//
//		@Override
//		public void onSuccess(int statusCode, String content) {
//			// TODO Auto-generated method stub
//
//			if (!StringUtil.isEmpty(content)) {
//				try {
//					JSONObject returncode = new JSONObject(content);
//					String data = returncode.getString("data");
//					String type = returncode.getString("type");
//					if (!ApiHandler.isSccuss(context, type, data)) {
//						return;
//					}
//					// 解密数据
//					data = DataUtils.getResponseData(context, data);
//					JSONObject jsonObject = new JSONObject(data);
//
//					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
//						return;
//					}
//
//					// UserPo userPo=new UserPo();
//					Gson gson = GsonUtils.getGson();
//					userPo = gson.fromJson(jsonObject.getString("record"), new TypeToken<UserPo>() {
//					}.getType());
//					tv_user_name.setText(userPo.getUserName());
//					initUserHead();
//
//				} catch (Exception e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//		@Override
//		public void onStart() {
//			// TODO Auto-generated method stub
//			AbLogUtil.d(TAG, "onStart");
//			// 显示进度框
//			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
//		}
//
//		@Override
//		public void onFinish() {
//			// TODO Auto-generated method stub
//			AbLogUtil.d(TAG, "onFinish");
//			// 移除进度框
//			// HideProgressDialog();
//
//			// AbDialogUtil.removeDialog(context);
//		}
//
//		@Override
//		public void onFailure(int statusCode, String content, Throwable error) {
//			// TODO Auto-generated method stub
//			AbLogUtil.d(TAG, "onFailure");
//			AbToastUtil.showToast(context, error.getMessage());
//		}
//
//	};
	
	
	private void initUserHead(){
		try {
			photoNameArray=userPo.getUserPhoto().split("/");
			photoName=photoNameArray[photoNameArray.length-1];			
			in = new FileInputStream(FileUtils.SDPATH + "image/" + photoName);
			myBitmap = BitmapFactory.decodeStream(in);
			user_picture.setImageBitmap(myBitmap);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + photoName, user_picture,
					imageLoadingListener);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingFailed");
			try {
				InputStream in2 = context.getResources().openRawResource(R.drawable.user_defind_head);
				myBitmap = BitmapFactory.decodeStream(in2);
				user_picture.setImageBitmap(myBitmap);
				in2.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AbToastUtil.showToast(context, "头像下载失败！");
		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingCancelled");
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
				
					
					Gson gson = GsonUtils.getGson();
					userPo = gson.fromJson(jsonObject.getString("record"), new TypeToken<UserPo>() {
					}.getType());
					tv_user_name.setText(userPo.getUserName());
					initUserHead();
					
					tv_personal_dynamic_num.setText(userPo.getDynamicNum()+"");
					tv_personal_fans_num.setText(userPo.getFansNum()+"");
					tv_personal_focus_num.setText(userPo.getFocusNum()+"");
					
					AbSharedUtil.putString(context, Constant.LAST_LOGINUSERNAME, userPo.getUserName());
					
					
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
}
