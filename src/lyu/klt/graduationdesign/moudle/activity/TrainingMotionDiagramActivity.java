/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * 
* @ClassName: TrainingMotionDiagramActivity 
* @Description: TODO(训练图解界面) 
* @author 康良涛 
* @date 2017年1月23日 下午9:30:25 
*
 */
public class TrainingMotionDiagramActivity extends BaseActivity {

	
	private static final String TAG = TrainingMotionDiagramActivity.class
			.getSimpleName();
	private Activity context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_training_motion_diagram_layout);
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
