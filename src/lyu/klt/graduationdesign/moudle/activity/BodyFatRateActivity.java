/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/** 
* @ClassName: BMIActivity 
* @Description: TODO(BMI计算) 
* @author 康良涛 
* @date 2016年12月28日 下午12:50:24 
*  
*/
public class BodyFatRateActivity extends BaseActivity {

	private static final String TAG = BodyFatRateActivity.class
			.getSimpleName();
	private Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_body_fat_rate_layout);
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
