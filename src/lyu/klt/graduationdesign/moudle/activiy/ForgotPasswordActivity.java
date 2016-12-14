package lyu.klt.graduationdesign.moudle.activiy;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * 
* @ClassName: ForgotPasswordActivity 
* @Description: TODO(忘记密码Activity) 
* @author 康良涛 
* @date 2016年12月14日 上午10:13:13 
*
 */

public class ForgotPasswordActivity extends BaseActivity {
	
	private static final String TAG = ForgotPasswordActivity.class
			.getSimpleName();
	private Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_forgot_password);
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
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}
	
	
}
