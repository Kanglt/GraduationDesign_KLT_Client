package lyu.klt.graduationdesign.moudle.activity;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.photoselector.model.PhotoModel;
import lyu.klt.frame.photoselector.ui.PhotoSelectorActivity;
import lyu.klt.frame.photoselector.util.CommonUtils;
import lyu.klt.frame.util.DateUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.DialogUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.util.UploadUtil;

public class InfomationActivity extends BaseActivity {
	private static final String TAG = InfomationActivity.class.getSimpleName();
	private Activity context;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;

	private FileInputStream in = null;

	private UserPo userPo;

	private View ll_information_user_id, ll_information_user_name, ll_information_user_sex,
			ll_information_user_birthday, ll_information_user_phone_numble, ll_information_user_email;

	private TextView tv_information_user_id, tv_information_user_name, tv_information_user_sex,
			tv_information_user_birthday, tv_information_user_phone_numble, tv_information_user_email,tv_information_user_age;

	private ImageView iv_information_user_picture;

	private Calendar calendar;// 用来装日期的
	private Dialog DateTimedialog;
	private int years = 0;
	private int month = 0;
	private int day = 0;

	private Bitmap myBitmap;

	private Dialog dialog;
	private String[] photoNameArray;
	private String photoName;
	
	private String userAge;
	
	private static String nowTime=DateUtil.getStringTime(DateUtil.FORMAT_TYPE2);

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_infomation);
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
		calendar = Calendar.getInstance();// 获取当前时间
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		userPo = (UserPo) getIntent().getSerializableExtra("userPo");
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

		ll_information_user_id = (LinearLayout) findViewById(R.id.ll_information_user_id);
		ll_information_user_name = (LinearLayout) findViewById(R.id.ll_information_user_name);
		ll_information_user_sex = (LinearLayout) findViewById(R.id.ll_information_user_sex);
		ll_information_user_birthday = (LinearLayout) findViewById(R.id.ll_information_user_birthday);
		ll_information_user_phone_numble = (LinearLayout) findViewById(R.id.ll_information_user_phone_numble);
		ll_information_user_email = (LinearLayout) findViewById(R.id.ll_information_user_email);

		tv_information_user_id = (TextView) findViewById(R.id.tv_information_user_id);
		tv_information_user_name = (TextView) findViewById(R.id.tv_information_user_name);
		tv_information_user_sex = (TextView) findViewById(R.id.tv_information_user_sex);
		tv_information_user_birthday = (TextView) findViewById(R.id.tv_information_user_birthday);
		tv_information_user_phone_numble = (TextView) findViewById(R.id.tv_information_user_phone_numble);
		tv_information_user_email = (TextView) findViewById(R.id.tv_information_user_email);
		tv_information_user_age= (TextView) findViewById(R.id.tv_information_user_age);

		iv_information_user_picture = (ImageView) findViewById(R.id.iv_information_user_picture);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		

		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("修改个人资料");
		titlebar_right_text.setVisibility(View.VISIBLE);

		if (userPo != null) {
			initUserHead();
			tv_information_user_id.setText(userPo.getUserId());
			tv_information_user_name.setText(userPo.getUserName());
			tv_information_user_sex.setText(userPo.getUserSex());
			tv_information_user_birthday.setText(userPo.getUserBirthday().toString());
			tv_information_user_phone_numble.setText(userPo.getUserPhoneNumble());
			tv_information_user_email.setText(userPo.getUserEmail());
			tv_information_user_age.setText(userPo.getUserAge());
		}

	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img.setOnClickListener(onClickListener);
		titlebar_right_text.setOnClickListener(onClickListener);

		ll_information_user_id.setOnClickListener(onClickListener);
		ll_information_user_name.setOnClickListener(onClickListener);
		ll_information_user_sex.setOnClickListener(onClickListener);
		ll_information_user_birthday.setOnClickListener(onClickListener);
		ll_information_user_phone_numble.setOnClickListener(onClickListener);
		ll_information_user_email.setOnClickListener(onClickListener);

		iv_information_user_picture.setOnClickListener(onClickListener);

	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.ll_information_user_name:
				initUpdateUserInfoDialog(tv_information_user_name);
				break;
			case R.id.ll_information_user_sex:
				initUpdateUserInfoDialog(tv_information_user_sex);
				break;
			case R.id.ll_information_user_birthday:
				initDateTimeDialog(tv_information_user_birthday);
				break;
			case R.id.ll_information_user_phone_numble:
				initUpdateUserInfoDialog(tv_information_user_phone_numble);
				break;
			case R.id.ll_information_user_email:
				initUpdateUserInfoDialog(tv_information_user_email);
				break;
			case R.id.title_bar_right_text:
				UserAPI.updateUserInformationForMobile(context, tv_information_user_id.getText().toString(),
						tv_information_user_name.getText().toString(),
						AbSharedUtil.getString(context, Constant.LAST_LOGINID_PASSWORD),
						tv_information_user_birthday.getText().toString(),tv_information_user_age.getText().toString(),
						tv_information_user_phone_numble.getText().toString(),
						tv_information_user_sex.getText().toString(), tv_information_user_email.getText().toString(),
						userPo.getUserId() + "_head"+nowTime+".jpg", updateUserInformationStringHttpResponseListener);
				break;
			case R.id.iv_information_user_picture:
				CommonUtils.launchActivityForResult(context, PhotoSelectorActivity.class, 0, 1);
				break;
			default:
				break;
			}
		}

	};

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == 0 && resultCode == RESULT_OK) {
			if (data != null && data.getExtras() != null) {
				@SuppressWarnings("unchecked")
				List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
				if (photos == null || photos.isEmpty())
					return;
				StringBuffer sb = new StringBuffer();
				for (PhotoModel photo : photos) {
					// sb.append(photo.getOriginalPath() + "\r\n");
					// InputStream in;
					// in=context.getResources().openRawResource(R.drawable.recommended_unpress);
					FileInputStream in = null;
					try {
						in = new FileInputStream(photo.getOriginalPath());
						myBitmap = BitmapFactory.decodeStream(in);
						iv_information_user_picture.setImageBitmap(myBitmap);
						in.close();
					} catch (FileNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				}

			}

		}
	}

	/**
	 * 
	 * @Title: initDateTimeDialog @author 康良涛 @Description:
	 *         TODO(选择时间) @param @param text @return void @throws
	 */
	private void initDateTimeDialog(final TextView text) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.activity_date_and_time_dialog, null);
		final DatePicker date_text = (DatePicker) viewDia.findViewById(R.id.date_text);
		date_text.setCalendarViewShown(false);
		if (years != 0) {
			date_text.updateDate(years, month - 1, day);
		}
		final TimePicker time_text = (TimePicker) viewDia.findViewById(R.id.time_text);
		time_text.setVisibility(View.GONE);
		time_text.setIs24HourView(true);
		time_text.setCurrentHour(calendar.get(Calendar.HOUR_OF_DAY));
		time_text.setCurrentMinute(calendar.get(Calendar.MINUTE));
		Button ok_btn = (Button) viewDia.findViewById(R.id.ok_btn);
		ok_btn.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				String MonthOfYear = "";
				String DayOfMonth = "";
				years = date_text.getYear();
				month = date_text.getMonth() + 1;
				day = date_text.getDayOfMonth();
				if (month <= 9) {
					MonthOfYear = "0" + month;
				} else {
					MonthOfYear = "" + month;
				}
				if (day <= 9) {
					DayOfMonth = "0" + day;
				} else {
					DayOfMonth = "" + day;
				}
				// String HourOfDay = "";
				// String Minute = "";
				// if (time_text.getCurrentHour() <= 9) {
				// HourOfDay = "0" + time_text.getCurrentHour();
				// } else {
				// HourOfDay = "" + time_text.getCurrentHour();
				// }
				// if (time_text.getCurrentMinute() <= 9) {
				// Minute = "0" + time_text.getCurrentMinute();
				// } else {
				// Minute = "" + time_text.getCurrentMinute();
				// }
				text.setText(years + "-" + MonthOfYear + "-" + DayOfMonth);
				userAge=DateUtil.remainDateToStringYear(years + "-" + MonthOfYear + "-" + DayOfMonth, DateUtil.getDateEN1());
				tv_information_user_age.setText(userAge);
				calendar.set(years, date_text.getMonth(), day, time_text.getCurrentHour(), time_text.getCurrentMinute(),
						0);
				hideDialog(DateTimedialog);
			}
		});

		DateTimedialog = new Dialog(context, R.style.dialog1);
		DateTimedialog.show();
		DateTimedialog.setContentView(viewDia);
		DateTimedialog.setCanceledOnTouchOutside(true);
	}

	private AbStringHttpResponseListener updateUserInformationStringHttpResponseListener = new AbStringHttpResponseListener() {

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

					FileUtils.saveBitmap(myBitmap, "image/" + tv_information_user_id.getText().toString() + "_head"+nowTime);
					AbToastUtil.showToast(context, "修改成功！");
					AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, true);
					finish();
					// userPo.setUserBirthday(tv_information_user_birthday.getText().toString());
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

			if (!UploadUtil.updateUserPhoto(context, myBitmap, userPo.getUserId() + "_head"+nowTime+".jpg")) {
				return;
			}

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

	/**
	 * @Title: initUpdateUserInfoDialog @Description:
	 *         关于修改资料的dialog @param: @param commodityOrderPo @return:
	 *         void @throws
	 */
	private void initUpdateUserInfoDialog(final TextView tv) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		log_text.setText(tv.getText().toString());
		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideDialog(dialog);
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				tv.setText(log_text.getText().toString());
				// AbSharedUtil.putString(context, Constant.REFRESH, "1");
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(context, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
	}

	private void initUserHead() {
		try {
			photoNameArray=userPo.getUserPhoto().split("/");
			photoName=photoNameArray[photoNameArray.length-1];			
			in = new FileInputStream(FileUtils.SDPATH + "image/" + photoName);
			myBitmap = BitmapFactory.decodeStream(in);
			iv_information_user_picture.setImageBitmap(myBitmap);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + photoName, iv_information_user_picture,
					imageLoadingListener);
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingFailed");
			try {
				InputStream in2 = context.getResources().openRawResource(R.drawable.user_defind_head);
				myBitmap = BitmapFactory.decodeStream(in2);
				iv_information_user_picture.setImageBitmap(myBitmap);
				in2.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			AbToastUtil.showToast(context, "头像下载失败！");
		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("PersonalFargmentActivity", "onLoadingCancelled");
		}
	};

}
