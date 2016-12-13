package lyu.klt.graduationdesign.moudle.activiy;


import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.FormatValidation;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

public class RegisteredActivity extends BaseActivity {
	private static final String TAG = RegisteredActivity.class
			.getSimpleName();
	private Activity context;
	
	public EditText edi_registeredAccount,edi_registeredPassword,edi_confirmPassword;
	public Button btn_registered;
	public String str_account,str_password,str_confirmPassword;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_registered);
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
		edi_registeredAccount=(EditText) findViewById(R.id.edi_registeredAccount);
		edi_registeredPassword=(EditText) findViewById(R.id.edi_registeredPassword);
		edi_confirmPassword=(EditText) findViewById(R.id.edi_confirmPassword);
		btn_registered=(Button) findViewById(R.id.btn_registered);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		btn_registered.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}
	
	private OnClickListener onClickListener =new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_registered:
				registered();
				break;

			default:
				break;
			}
			
		}
	};
	
	private void registered() {
		str_account=edi_registeredAccount.getText().toString();
		str_password=edi_registeredPassword.getText().toString();
		str_confirmPassword=edi_confirmPassword.getText().toString();

		if (TextUtils.isEmpty(str_account)) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_registeredAccount.setFocusable(true);
			edi_registeredAccount.requestFocus();
			return;
		}

		if (TextUtils.isEmpty(str_password)
				|| !(str_password.length() > 5 && str_password.length() < 17)) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_registeredPassword.setFocusable(true);
			edi_registeredPassword.requestFocus();
			return;
		}

		if (TextUtils.isEmpty(str_confirmPassword)
				|| !(str_confirmPassword.length() > 5 && str_confirmPassword.length() < 17)) {
			AbToastUtil.showToast(context, R.string.str_data_is_empty);
			edi_confirmPassword.setFocusable(true);
			edi_confirmPassword.requestFocus();
			return;
		}

		if (!str_password.equals(str_confirmPassword)) {
			AbToastUtil.showToast(context, R.string.str_password_is_different);
			return;
		}
		

		if (!FormatValidation.isMobileNO(str_account)) {
			AbToastUtil.showToast(context, R.string.str_account_type_erroe);
			edi_registeredAccount.setFocusable(true);
			edi_registeredAccount.requestFocus();
			return;
		}



	}
	
}
