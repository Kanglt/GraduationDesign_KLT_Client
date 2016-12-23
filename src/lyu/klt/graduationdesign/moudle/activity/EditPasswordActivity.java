package lyu.klt.graduationdesign.moudle.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;

public class EditPasswordActivity extends BaseActivity {
	private static final String TAG = EditPasswordActivity.class.getSimpleName();
	private Activity context;

	private UserPo userPo;

	private EditText edi_editAccount, edi_nowPassword, edi_newPassword, edi_confirmPassword;
	private Button btn_editPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_edit_password);
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
		userPo = (UserPo) getIntent().getSerializableExtra("userPo");
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
		edi_editAccount = (EditText) findViewById(R.id.edi_editAccount);
		edi_nowPassword = (EditText) findViewById(R.id.edi_nowPassword);
		edi_newPassword = (EditText) findViewById(R.id.edi_newPassword);
		edi_confirmPassword = (EditText) findViewById(R.id.edi_confirmPassword);

		btn_editPassword = (Button) findViewById(R.id.btn_editPassword);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		edi_editAccount.setEnabled(false);
		if (userPo != null) {
			edi_editAccount.setText(userPo.getUserId());
		}
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		btn_editPassword.setOnClickListener(onClicklistener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	private OnClickListener onClicklistener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_editPassword:

				if (dateFormat()) {
					UserAPI.updateUserInformationForMobile(context, userPo.getUserId(), userPo.getUserName(),
							edi_newPassword.getText().toString(), userPo.getUserBirthday(), userPo.getUserPhoneNumble(),
							userPo.getUserSex(), userPo.getUserEmail(), userPo.getUserId() + "_head.jpg",
							updateUserPasswordStringHttpResponseListener);
				}
				break;

			default:
				break;
			}

		}

	};

	private boolean dateFormat() {
		if (TextUtils.isEmpty(edi_nowPassword.getText().toString())) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_nowPassword.setFocusable(true);
			edi_nowPassword.requestFocus();
			return false;
		}
		if (TextUtils.isEmpty(edi_newPassword.getText().toString())) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_newPassword.setFocusable(true);
			edi_newPassword.requestFocus();
			return false;
		}
		if (TextUtils.isEmpty(edi_confirmPassword.getText().toString())) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_confirmPassword.setFocusable(true);
			edi_confirmPassword.requestFocus();
			return false;
		}
		if (!edi_newPassword.getText().toString().equals(edi_confirmPassword.getText().toString())) {
			AbToastUtil.showToast(context, R.string.str_password_is_different);
			edi_newPassword.setFocusable(true);
			edi_newPassword.requestFocus();
			return false;
		}
		if (!edi_nowPassword.getText().toString().equals(userPo.getUserPassword())) {
			AbToastUtil.showToast(context, R.string.str_password_is_error);
			edi_nowPassword.setFocusable(true);
			edi_nowPassword.requestFocus();
			return false;
		}
		return true;

	}

	private AbStringHttpResponseListener updateUserPasswordStringHttpResponseListener = new AbStringHttpResponseListener() {
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

					AbToastUtil.showToast(context, "修改成功！");
					Intent intent = new Intent(context, LoginActivity.class);
					startActivity(intent);
					finish();
					MyApplication.getInstance().exit();
					// UserPo userPo=new UserPo();
					// Gson gson = GsonUtils.getGson();
					// userPo = gson.fromJson(jsonObject.getString("record"),
					// new TypeToken<UserPo>() {
					// }.getType());

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
}
