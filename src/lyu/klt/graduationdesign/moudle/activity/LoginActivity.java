package lyu.klt.graduationdesign.moudle.activity;

import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.sqlite.KangSQLiteOpenHelper;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;

public class LoginActivity extends BaseActivity {
	private static final String TAG = LoginActivity.class.getSimpleName();
	private Activity context;

	public KangSQLiteOpenHelper kangSQLiteOpenHelper;
	public SQLiteDatabase sqLiteDatabase;
	public String[] userIdStr = new String[] {};
	public String[] userPasswordStr = new String[] {};
	public ArrayAdapter<String> autoAdapter;

	public AutoCompleteTextView autotv_userId;
	public Button btn_login;
	public CheckBox btn_rememberPassword;
	public EditText edi_password;
	public TextView tv_changePassword, tv_registered;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_login);
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
		kangSQLiteOpenHelper = new KangSQLiteOpenHelper(context, "testDB", null, 1);

	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		getLogindata();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		autotv_userId = (AutoCompleteTextView) findViewById(R.id.autotv_userId);
		edi_password = (EditText) findViewById(R.id.edi_password);
		btn_login = (Button) findViewById(R.id.btn_login);
		tv_changePassword = (TextView) findViewById(R.id.tv_changePassword);
		tv_registered = (TextView) findViewById(R.id.tv_registered);
		btn_rememberPassword=(CheckBox) findViewById(R.id.btn_rememberPassword);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		autoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userIdStr);
		autotv_userId.setAdapter(autoAdapter);
		autotv_userId.setThreshold(1);// 第几个字符开始检索
		
		if(AbSharedUtil.getBoolean(context, Constant.ISREMEBERPASSWORD, false)){
			btn_rememberPassword.setChecked(true);
			autotv_userId.setText(AbSharedUtil.getString(context, Constant.LAST_LOGINID));
			edi_password.setText(AbSharedUtil.getString(context, Constant.LAST_LOGINID_PASSWORD));
		}
		
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		btn_login.setOnClickListener(onClickListener);
		tv_changePassword.setOnClickListener(onClickListener);
		tv_registered.setOnClickListener(onClickListener);

	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	public void getLogindata() {
		sqLiteDatabase = kangSQLiteOpenHelper.getReadableDatabase();
		Cursor cursor = sqLiteDatabase.rawQuery("select * from 'loginData'", null);
		int i = cursor.getCount();
		userIdStr = new String[i];
		userPasswordStr = new String[i];
		cursor.moveToNext();
		for (; cursor != null && i > 0; i--) {
			int userNameColumnIndex = cursor.getColumnIndex("userId");
			int userPasswordColumnIndex = cursor.getColumnIndex("userPassword");
			userIdStr[i - 1] = cursor.getString(userNameColumnIndex);
			userPasswordStr[i - 1] = cursor.getString(userPasswordColumnIndex);
			cursor.moveToNext();
		}
		sqLiteDatabase.close();
		cursor.close();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.btn_login:
				String userId = autotv_userId.getText().toString();
				String userPassword = edi_password.getText().toString();
				
				
				if (!userId.isEmpty() && !userPassword.isEmpty()) {
					
					UserAPI.userLoginForMobile(context, userId, userLoginStringHttpResponseListener);
					
				} else {
					AbToastUtil.showToast(context, "用户名或密码不能为空！");
				}

				break;
			case R.id.tv_registered:
				intent.setClass(context, RegisteredActivity.class);
				startActivity(intent);
				break;
			case R.id.tv_changePassword:
				
				AbToastUtil.showToast(context, "功能尚未开放！");
				break;
			default:
				break;
			}
		}

	};

	private AbStringHttpResponseListener userLoginStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					UserPo userPo;
					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();
					userPo = gson.fromJson(jsonObject.getString("record"), new TypeToken<UserPo>() {
					}.getType());
					if(userPo.getUserPassword().equals(edi_password.getText().toString())){
						sqLiteDatabase = kangSQLiteOpenHelper.getReadableDatabase();
						ContentValues cv = new ContentValues();
						cv.put("userId", userPo.getUserId());
						cv.put("userPassword", userPo.getUserPassword());
						sqLiteDatabase.insert("loginData", null, cv);
						// sqLiteDatabase.execSQL("insert into loginData(userName,
						// userPassword) values(?,?)",new Object[]{userName,
						// userPassword});
						sqLiteDatabase.close();
						
						
						getLogindata();
						
						autoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userIdStr);
						autoAdapter.notifyDataSetChanged();
						autotv_userId.setAdapter(autoAdapter);
						
						if(btn_rememberPassword.isChecked()){
							AbSharedUtil.putBoolean(context, Constant.ISREMEBERPASSWORD, true);
						}else{
							AbSharedUtil.putBoolean(context, Constant.ISREMEBERPASSWORD, false);
						}
						AbSharedUtil.putString(context, Constant.LAST_LOGINID, userPo.getUserId());
						AbSharedUtil.putString(context, Constant.LAST_LOGINID_PASSWORD, userPo.getUserPassword());
						Intent intent=new Intent();
						intent.setClass(context, MainActivity.class);
						startActivity(intent);
					}else{
						AbToastUtil.showToast(context, "密码错误！");
					}

					// StringBuilder sb = new StringBuilder();
					// for (int i = 0; i < jsonArray.length(); i++) {
					// //遍历所有取出id集合
					// if(i==jsonArray.length()-1){ //如果是最后一个不加上,号
					// sb.append(jsonArray.getJSONObject(i).getString("fileId"));
					// }else{
					// sb.append(jsonArray.getJSONObject(i).getString("fileId")+",");
					// }
					// }
					// String localStr = AbSharedUtil.getString(context,
					// Constant.CAROUSEL_RESTAURANT_FILEID);
					// if(localStr==null || !localStr.equals(sb.toString())){
					// //如果本地图片为空 并且 上面的和本地的不一样
					// AbSharedUtil.putString(context,
					// Constant.CAROUSEL_RESTAURANT_FILEID,sb.toString());
					// doLoadCarouse(); //如果不一样 重新加载
					//
					// }

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
			AbDialogUtil.showProgressDialog(context, 0, "正在登入...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			HideProgressDialog();

		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};

}
