/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.ActionStepRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.DietStepRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.FoodMateriaListAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.ActionPo;
import lyu.klt.graduationdesign.module.bean.ActionStepPo;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.DietStepPo;
import lyu.klt.graduationdesign.module.bean.FoodMateriaPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

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
	
	private RecyclerView rv_step;
	private ActionStepRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	private List<ActionStepPo> actionStepPoList;
	private ActionPo actionPo;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;
	
	private TextView tv_introduce;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_training_motion_diagram_step_layout);
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
		actionPo=(ActionPo) getIntent().getSerializableExtra("actionPo");
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
		
		rv_step = (RecyclerView) findViewById(R.id.rv_step);
		tv_introduce=(TextView) findViewById(R.id.tv_introduce);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		titlebar_view.setAlpha(5);
		title_bar_left_img.setAlpha(100);
		// titlebar_view.setBackgroundColor(android.graphics.Color.parseColor("#7c7c7c"));
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return3));
		title_bar_text.setText("图解:"+actionPo.getActionName());
		
		tv_introduce.setText(actionPo.getActionText());
		
		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);

		// 设置Adapter
		rv_step.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_step.setLayoutManager(mLayoutManager);
		rv_step.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		rv_step.addItemDecoration(decoration);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		titlebar_right_text.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
		TrainingDataPAI.queryActionStep(context, actionPo.getId()+"", querActionstepStringHttpResponseListener);
		
	}
	

	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}
	
	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			default:
				break;
			}
		}
	};
	
	private AbStringHttpResponseListener querActionstepStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					actionStepPoList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<ActionStepPo>>() {
					}.getType());

					mAdapter = new ActionStepRecyclerAdapter(context, 1, actionStepPoList);

					rv_step.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
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
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
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
			AbToastUtil.showToast(context, error.getMessage());
		}

	};
}
