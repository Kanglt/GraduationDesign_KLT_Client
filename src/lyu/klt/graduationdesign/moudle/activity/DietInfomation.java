/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DietListRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.FoodMateriaListAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.StepRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.DietDataListPo;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.DietStepPo;
import lyu.klt.graduationdesign.module.bean.FoodMateriaPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.DietDataPAI;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.ViewUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * @ClassName: DietInfomation
 * @Description: TODO(显示食品所有信息)
 * @author 康良涛
 * @date 2016年12月25日 上午10:47:09
 * 
 */
public class DietInfomation extends BaseActivity {

	private static final String TAG = DietInfomation.class.getSimpleName();
	private Activity context;

	private ImageView iv_dinneImage;
	private TextView tv_dietName, tv_dinneTime, tv_introduce, tv_calories_num, tv_carbohydrate_num, tv_protein_num,
			tv_fat_num;
	private ListView list_foodMateria;
	private RecyclerView rv_step;

	private List<FoodMateriaPo> foodMateriaPoList;
	private FoodMateriaListAdapter foodMateriaListAdapter;

	private StepRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;
	private List<DietStepPo> dietStepPoListPo;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;

	private DietDataPo dietDataPo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity__diet_information_layout);
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
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		dietDataPo = (DietDataPo) getIntent().getSerializableExtra("dietDataPo");
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

		iv_dinneImage = (ImageView) findViewById(R.id.iv_dinneImage);

		tv_dietName = (TextView) findViewById(R.id.tv_dietName);
		tv_dinneTime = (TextView) findViewById(R.id.tv_dinneTime);
		tv_introduce = (TextView) findViewById(R.id.tv_introduce);
		tv_calories_num = (TextView) findViewById(R.id.tv_calories_num);
		tv_carbohydrate_num = (TextView) findViewById(R.id.tv_carbohydrate_num);
		tv_protein_num = (TextView) findViewById(R.id.tv_protein_num);
		tv_fat_num = (TextView) findViewById(R.id.tv_fat_num);

		list_foodMateria = (ListView) findViewById(R.id.list_foodMateria);
		rv_step = (RecyclerView) findViewById(R.id.rv_step);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		titlebar_view.setAlpha(5);
		title_bar_left_img.setAlpha(100);
		// titlebar_view.setBackgroundColor(android.graphics.Color.parseColor("#7c7c7c"));
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return3));
		title_bar_text.setVisibility(View.GONE);
		titlebar_right_text.setVisibility(View.VISIBLE);
		titlebar_right_text.setText("");
		titlebar_right_text.setBackgroundResource(R.drawable.btn_add);

		tv_dinneTime.setText(dietDataPo.getProductionTime() + "分钟");
		tv_dietName.setText(dietDataPo.getDietName());
		tv_introduce.setText(dietDataPo.getIntroduce());
		tv_calories_num.setText(dietDataPo.getCalories());
		tv_carbohydrate_num.setText(dietDataPo.getCarbohydrate());
		tv_protein_num.setText(dietDataPo.getProtein());
		tv_fat_num.setText(dietDataPo.getFat());

		ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_DIETIMAGE_URL + "diet_1.jpg", iv_dinneImage,
				imageLoadingListener);

		foodMateriaPoList = new ArrayList<FoodMateriaPo>();
		foodMateriaListAdapter = new FoodMateriaListAdapter(context, foodMateriaPoList);
		list_foodMateria.setAdapter(foodMateriaListAdapter);
		ViewUtil.setListViewHeightBasedOnChildren(list_foodMateria);

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

		title_bar_left_img.setOnClickListener(onClickListener);
		titlebar_right_text.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

		DietDataPAI.getDietData_foodMateria(context, dietDataPo.getDietName(),
				dietData_foodMateriaStringHttpResponseListener);
		DietDataPAI.getDietDetaile_step(context, dietDataPo.getDietName(), dietDetaile_stepStringHttpResponseListener);
	}

	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}

	private AbStringHttpResponseListener dietData_foodMateriaStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					foodMateriaPoList = gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<FoodMateriaPo>>() {
							}.getType());

					foodMateriaListAdapter = new FoodMateriaListAdapter(context, foodMateriaPoList);
					list_foodMateria.setAdapter(foodMateriaListAdapter);
					foodMateriaListAdapter.notifyDataSetChanged();
					ViewUtil.setListViewHeightBasedOnChildren(list_foodMateria);

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

	private AbStringHttpResponseListener dietDetaile_stepStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					dietStepPoListPo = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<DietStepPo>>() {
					}.getType());

					mAdapter = new StepRecyclerAdapter(context, 1, dietStepPoListPo);

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

	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DietInfomation", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("DietInfomation", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("DietInfomation", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("DietInfomation", "onLoadingCancelled");
		}
	};

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.title_bar_right_text:
				
				break;

			default:
				break;
			}
		}
	};
}