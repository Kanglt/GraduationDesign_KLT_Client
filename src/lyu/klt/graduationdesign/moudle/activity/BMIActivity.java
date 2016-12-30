/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.Legend.LegendForm;
import com.github.mikephil.charting.components.Legend.LegendPosition;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis.XAxisPosition;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ViewPortHandler;
import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.DateUtil;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.DeleteUserTrainingRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.UserBodyDataPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserBodyDataAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;

/**
 * @ClassName: BMIActivity
 * @Description: TODO(BMI计算)
 * @author 康良涛
 * @date 2016年12月28日 下午12:50:24
 * 
 */
public class BMIActivity extends BaseActivity {

	private static final String TAG = BMIActivity.class.getSimpleName();
	private Activity context;

	private LineChart mlinechart;
	private XAxis mXAxis;

	private List<UserBodyDataPo> userBodyDataPoList;

	private float yMax, xCount;

	private TextView tv_BMI_date;
	private TextView tv_userWeight_with_lastTime;
	private TextView tv_userWeight;
	private TextView tv_BIM;
	private EditText edi_BMI_weight, edi_BMI_height;
	private Button btn_BMI_confirm_calculate;
	DecimalFormat decimalFormat;
	
	private double height=0.0,weight=0.0,data=0.0;;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_bmi_layout);
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
		decimalFormat=new DecimalFormat(".0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
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

		mlinechart = (LineChart) findViewById(R.id.linechart);
		tv_BMI_date = (TextView) findViewById(R.id.tv_BMI_date);
		tv_userWeight_with_lastTime = (TextView) findViewById(R.id.tv_userWeight_with_lastTime);
		tv_userWeight = (TextView) findViewById(R.id.tv_userWeight);
		tv_BIM = (TextView) findViewById(R.id.tv_BIM);
		edi_BMI_weight = (EditText) findViewById(R.id.edi_BMI_weight);
		edi_BMI_height = (EditText) findViewById(R.id.edi_BMI_height);
		btn_BMI_confirm_calculate=(Button) findViewById(R.id.btn_BMI_confirm_calculate);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		// 将x轴放到底部 默认在顶部
		mXAxis = mlinechart.getXAxis();
		mXAxis.setPosition(XAxisPosition.BOTTOM);

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		btn_BMI_confirm_calculate.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();

		UserBodyDataAPI.getUserBodyData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), "BMI",
				getUserBodyDataStringHttpResponseListener);
	}
	
	
	private OnClickListener onClickListener=new OnClickListener() {
		
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.btn_BMI_confirm_calculate:
				if(edi_BMI_weight.getText().toString().isEmpty()){
					AbToastUtil.showToast(context, "体重不能为空！");
					return;
				}
				if(edi_BMI_height.getText().toString().isEmpty()){
					AbToastUtil.showToast(context, "身高不能为空！");
					return;
				}
				weight=Double.parseDouble(edi_BMI_weight.getText().toString());
				height=Double.parseDouble(edi_BMI_height.getText().toString());
				data=weight/(height*height);
				String date=DateUtil.getDateEN();
				
				UserBodyDataAPI.addUserBodyData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), "BMI", decimalFormat.format(data), decimalFormat.format(height), decimalFormat.format(weight), date, addUserBodyDataStringHttpResponseListener);
				break;

			default:
				break;
			}
		}
	};

	// 设置显示样式
	private void setChartStyle(LineChart mlinechart, LineData mLineData) {
		// 是否在折线上添加边框
		mlinechart.setDrawBorders(false);
		// 数据描述
		mlinechart.setDescription("时间");
		// 如果没有数据的时候，会显示这个，类似listview的emtpyview
		mlinechart.setNoDataTextDescription("暂无数据！");
		// 是否绘制背景颜色。
		// 如果mLineChart.setDrawGridBackground(false)，
		// 那么mLineChart.setGridBackgroundColor()将失效;
		mlinechart.setDrawGridBackground(true);
		// 折线图的背景
		mlinechart.setGridBackgroundColor(android.graphics.Color.parseColor("#99cc99"));
		// 设置触摸
		mlinechart.setTouchEnabled(true);
		// 设置拖拽
		mlinechart.setDragEnabled(true);
		// 设置缩放
		mlinechart.setScaleEnabled(true);
		// 禁用后可以分别缩放x轴与y轴
		mlinechart.setPinchZoom(false);

		// x y 轴的背景
		//
		mlinechart.setBackgroundColor(android.graphics.Color.parseColor("#99cc99"));
		// 设置x y轴的数据
		mlinechart.setData(mLineData);
		// 设置比例图标，就是那一组y的value的
		Legend mLegend = mlinechart.getLegend();
		mLegend.setPosition(LegendPosition.BELOW_CHART_CENTER);
		// 样式
		mLegend.setForm(LegendForm.CIRCLE);
		// 字体
		mLegend.setFormSize(8.0f);
		// 下方字体颜色
		mLegend.setTextColor(Color.BLACK);
		// 设置x轴的动画
		mlinechart.animateX(0);
		// 是否从y轴的最底部开始显示
		mlinechart.getAxisLeft().setStartAtZero(false);

		// 右侧Y轴是否显示数字
		mlinechart.getAxisRight().setEnabled(false);

		// 隐藏纵横网格线
		mlinechart.getXAxis().setDrawGridLines(false);
		mlinechart.getAxisLeft().setDrawGridLines(false);
		mlinechart.getAxisRight().setDrawGridLines(false);

		// mLegend.setWordWrapEnabled(false);

	}

	private LineData LineData(int count) {
		ArrayList<String> x = new ArrayList<String>();
		// x轴的数据
		for (int i = 0; i < count; i++) {
			x.add("");
		}
		// y轴的数据
		ArrayList<Entry> y = new ArrayList<Entry>();
		yMax = 0;
		for (int i = 0; i < count; i++) {
			float f = Float.parseFloat(userBodyDataPoList.get(i).getData());
			if (yMax < f) {
				yMax = f;
			}
			Entry entry = new Entry(f, i);
			y.add(entry);
		}

		// y轴最大值
		mlinechart.getAxisLeft().setAxisMaxValue((float) (yMax + yMax * 0.5));
		// 设置X轴最大值 单位是DP
		mlinechart.setDragOffsetX(count * 30);

		mlinechart.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {

			@Override
			public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
				// TODO Auto-generated method stub
				tv_BMI_date.setText(userBodyDataPoList.get(e.getXIndex()).getDate());
				// tv_userWeight_with_lastTime.setText(userBodyDataPoList.get(e.getXIndex()).getDate());
				tv_userWeight.setText(userBodyDataPoList.get(e.getXIndex()).getWeight());
				tv_BIM.setText(userBodyDataPoList.get(e.getXIndex()).getData());

				float x = 0;
				if (e.getXIndex() > 0) {
					x = Float.parseFloat(userBodyDataPoList.get(e.getXIndex()).getWeight())
							- Float.parseFloat(userBodyDataPoList.get(e.getXIndex() - 1).getWeight());
				}

				if (x > 0) {
					tv_userWeight_with_lastTime.setText("+" + x);
				} else {
					tv_userWeight_with_lastTime.setText(x + "");
				}

			}

			@Override
			public void onNothingSelected() {
				// TODO Auto-generated method stub

			}
		});

		// 添加警戒线
		// YAxis yAxis = mlinechart.getAxisLeft();
		// LimitLine ll = new LimitLine(10, "警戒线");
		// ll.setLineWidth(0.5f);
		// ll.setLineColor(Color.GRAY);
		// ll.setTextColor(Color.GRAY);
		// ll.setTextSize(12);
		// ll.setEnabled(true);
		// yAxis.addLimitLine(ll);

		// y轴的数据集
		LineDataSet set = new LineDataSet(y, "BMI数据");
		// 用y轴的集合来设置参数
		// 线宽
		set.setLineWidth(1.0f);
		// 显示圆形大小
		set.setCircleSize(2.0f);
		// 折线的颜色 #f5f5f5
		set.setColor(Color.WHITE);
		// 圆球颜色
		set.setCircleColor(Color.WHITE);
		// 设置mLineDataSet.setDrawHighlightIndicators(false)后，
		// Highlight的十字交叉的纵横线将不会显示，
		// 同时，mLineDataSet.setHighLightColor()失效。

		set.setDrawHighlightIndicators(true);
		// 点击后，十字交叉线的颜色
		set.setHighLightColor(Color.GRAY);
		// 设置显示数据点字体大小
		set.setValueTextSize(10.0f);
		// mLineDataSet.setDrawCircleHole(true);

		// 改变折线样式，用曲线。
		set.setDrawCubic(true);
		// 默认是直线
		// 曲线的平滑度，值越大越平滑。
		set.setCubicIntensity(0.2f);

		// 填充曲线下方的区域，红色，半透明。
		set.setDrawFilled(false);
		// 数值越小 透明度越大
		// set.setFillAlpha(150);
		// set.setFillColor(Color.RED);

		// 填充折线上数据点、圆球里面包裹的中心空白处的颜色。
		set.setCircleColorHole(Color.WHITE);
		// 设置折线上显示数据的格式。如果不设置，将默认显示float数据格式。
		set.setValueFormatter(new ValueFormatter() {

			@Override
			public String getFormattedValue(float value, Entry entry, int dataSetIndex,
					ViewPortHandler viewPortHandler) {
				int n = (int) value;
				String str = n + "";
				return str;
			}
		});
		List<ILineDataSet> mLineDataSets = new ArrayList<ILineDataSet>();
		mLineDataSets.add(set);

		LineData mLineData = new LineData(x, mLineDataSets);
		return mLineData;

	}

	private AbStringHttpResponseListener getUserBodyDataStringHttpResponseListener = new AbStringHttpResponseListener() {
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

					Gson gson = GsonUtils.getGson();

					userBodyDataPoList = gson.fromJson(jsonObject.getString("list"),
							new TypeToken<List<UserBodyDataPo>>() {
							}.getType());

					LineData mLineData = LineData(userBodyDataPoList.size());
					setChartStyle(mlinechart, mLineData);

					tv_BMI_date.setText(userBodyDataPoList.get(userBodyDataPoList.size() - 1).getDate());
					// tv_userWeight_with_lastTime.setText(userBodyDataPoList.get(e.getXIndex()).getDate());
					tv_userWeight.setText(userBodyDataPoList.get(userBodyDataPoList.size() - 1).getWeight());
					tv_BIM.setText(userBodyDataPoList.get(userBodyDataPoList.size() - 1).getData());

					float x = 0;
					x = Float.parseFloat(userBodyDataPoList.get(userBodyDataPoList.size() - 1).getWeight())
							- Float.parseFloat(userBodyDataPoList.get(userBodyDataPoList.size() - 2).getWeight());

					if (x > 0) {
						tv_userWeight_with_lastTime.setText("+" + x);
					} else {
						tv_userWeight_with_lastTime.setText(x + "");
					}

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
	
	private AbStringHttpResponseListener addUserBodyDataStringHttpResponseListener = new AbStringHttpResponseListener() {
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
					
					UserBodyDataAPI.getUserBodyData(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID), "BMI",
							getUserBodyDataStringHttpResponseListener);
			
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
		//	AbDialogUtil.showProgressDialog(context, 0, "正在计算...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
		//	HideProgressDialog();

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
