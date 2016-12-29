/**     
*/
package lyu.klt.graduationdesign.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.DateUtil;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.farment.FitnessFargmentActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * @ClassName: MyCountDownTimer
 * @Description: TODO(倒计时)
 * @author 康良涛
 * @date 2016年12月26日 上午11:00:08
 * 
 */
public class MyCountDownTimer extends AdvancedCountdownTimer {

	private static SimpleDateFormat format;
	private static String date;
	private static int totalSize, totalSizeCountDownTimer;
	private Dialog dialog;
	private Activity context;
	private TrainingDataPo trainingDataPo;

	/**
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public MyCountDownTimer(Activity context, long millisInFuture, long countDownInterval,
			TrainingDataPo trainingDataPo) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		format = new SimpleDateFormat("mm:ss");
		totalSize = (int) millisInFuture;
		totalSizeCountDownTimer = (int) millisInFuture;
		this.context = context;
		this.trainingDataPo = trainingDataPo;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.os.CountDownTimer#onFinish()
	 */
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		date = format.format(new Date(totalSize));
		VideoDisplayActivity.tv_progress.setText(date);
		VideoDisplayActivity.iv_start.setImageBitmap(VideoDisplayActivity.video_startBitmap);
		FitnessFargmentActivity.isRefresh = true;
		showDialog(context);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see lyu.klt.graduationdesign.util.AdvancedCountdownTimer#onTick(long,
	 * int)
	 */
	@Override
	public void onTick(long millisUntilFinished, int percent) {
		// TODO Auto-generated method stub
		totalSizeCountDownTimer -= 1000;
		date = format.format(new Date(totalSizeCountDownTimer));
		VideoDisplayActivity.tv_progress.setText(date);

	}

	private AbStringHttpResponseListener addUserTrainingRecordStringHttpResponseListener = new AbStringHttpResponseListener() {

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

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub

			// 显示进度框
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub

			// 移除进度框
			// HideProgressDialog();
			TrainingDataPAI.addTraining(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
					trainingDataPo.getCategory(), addTrainingStringHttpResponseListener);
			MyApplication.getInstance().removeActivityByName("VideoDisplayActivity");
			dialog.dismiss();
			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub

			AbToastUtil.showToast(context, error.getMessage());
		}

	};

	public void showDialog(final Context context) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);
		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
		EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		log_text.setEnabled(false);
		log_text.setText("训练完成，上传训练记录？");

		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrainingDataPAI.addUserTrainingRecord(context, AbSharedUtil.getString(context, Constant.LAST_LOGINID),
						DateUtil.getDateEN1(), trainingDataPo.getTrainingCalories(), trainingDataPo.getTrainingTime(),
						trainingDataPo.getCategory(), addUserTrainingRecordStringHttpResponseListener);

			}
		});
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();

			}
		});

		dialog = new Dialog(context, R.style.MyDialogStyle);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCancelable(false);// dialog弹出后会点击屏幕或物理返回键，dialog不消失
		// videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}

	private AbStringHttpResponseListener addTrainingStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss((BaseActivity) context, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
						return;
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			// AbLogUtil.d(TAG, "onStart");
			// 显示进度框
			// AbDialogUtil.showProgressDialog(mContext, 0, "正在操作...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			// AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
		//	((BaseActivity) context).HideProgressDialog();
		//	dialog.dismiss();

		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			// AbToastUtil.showToast(context, error.getMessage());
			// dialog.dismiss();
		}

	};

}
