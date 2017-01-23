/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.SystemVersionPo;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.SystemAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.DialogUtils;
import lyu.klt.graduationdesign.util.SystemInfoUtils;

/**
 * @ClassName: SettingActivity
 * @Description: TODO(设置界面)
 * @author 康良涛
 * @date 2017年1月23日 下午9:00:48
 * 
 */
public class SettingActivity extends BaseActivity {

	private static final String TAG = SettingActivity.class.getSimpleName();
	private Activity context;

	private RelativeLayout setting_clear_cache;
	private RelativeLayout setting_version_update;
	private RelativeLayout setting_about;
	
	private CheckBox setting_message_remind;
	private SystemVersionPo systemVersionPo;

	private Dialog dialog;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_settings_layout);
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

		setting_clear_cache = (RelativeLayout) findViewById(R.id.setting_clear_cache);
		setting_version_update = (RelativeLayout) findViewById(R.id.setting_version_update);
		setting_about = (RelativeLayout) findViewById(R.id.setting_about);
		setting_message_remind=(CheckBox) findViewById(R.id.setting_message_remind);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("设置");
		
		if(AbSharedUtil.getBoolean(context, Constant.DETECTIONNEWVERSION, true)){
			setting_message_remind.setChecked(true);
		}else{
			setting_message_remind.setChecked(false);
		}
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		
		setting_about.setOnClickListener(onClickListener);
		setting_clear_cache.setOnClickListener(onClickListener);
		setting_version_update.setOnClickListener(onClickListener);
		title_bar_left_img.setOnClickListener(onClickListener);
		setting_message_remind.setOnClickListener(onClickListener);
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
			Intent intent = new Intent();
			switch (v.getId()) {
			case R.id.setting_about:
				intent.setClass(context, AboutActivity.class);
				startActivity(intent);
				break;
			case R.id.setting_clear_cache:
				initClearCacheDialog();
				break;
			case R.id.setting_version_update:
				SystemAPI.querySystemVersionInfomation(context, queryUserHomePageInfoStringHttpResponseListener);
				break;
			case R.id.title_bar_left_img:
				finish();
				break;
			case R.id.setting_message_remind:
				if(AbSharedUtil.getBoolean(context, Constant.DETECTIONNEWVERSION, true)){
					AbSharedUtil.putBoolean(context, Constant.DETECTIONNEWVERSION, false);
				}else{
					AbSharedUtil.putBoolean(context, Constant.DETECTIONNEWVERSION, true);
				}
				break;
			default:
				break;
			}
		}
	};

	private void clearCache() {
		FileUtils.deleteDir(FileUtils.SDPATH + "image/");
		FileUtils.deleteDir(FileUtils.SDPATH + "imageLoader/");
		FileUtils.deleteDir(FileUtils.SDPATH + "apk/");
	}

	private void initClearCacheDialog() {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		final TextView message_prompt = (TextView) viewDia.findViewById(R.id.message_prompt);
		message_prompt.setText(R.string.str_prompt);
		log_text.setText(R.string.str_clear_cache_prompt);
		log_text.setEnabled(false);
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

				clearCache();
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(context, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
	}
	
	private AbStringHttpResponseListener queryUserHomePageInfoStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					
					Gson gson=new Gson();
					systemVersionPo= gson.fromJson(jsonObject.getString("record"),
							new TypeToken<SystemVersionPo>() {
							}.getType());
					
					if(!SystemInfoUtils.getVersion(context).equals(systemVersionPo.getSystemVersionId())){
						initUpdateVersionDialog(systemVersionPo.getSystemApkURL());
					}else{
						initNoUpdateVersionDialog();
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
		//	AbDialogUtil.showProgressDialog(context, 0, "正在操作...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
	//		HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};
	
	private void initUpdateVersionDialog(String systemApkURL) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		final TextView message_prompt = (TextView) viewDia.findViewById(R.id.message_prompt);
		message_prompt.setText(R.string.str_newVersion_prompt);
		log_text.setText(R.string.str_isUpdateNewVersion);
		log_text.setEnabled(false);
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

				String apkNameArr[] = systemVersionPo.getSystemApkURL().split("/");
				String apkName = apkNameArr[apkNameArr.length - 1];
				DownLoadDialog.showNewVersionDownLoadDialog(context, apkName);
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(context, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		//dialog.setCanceledOnTouchOutside(true);
		dialog.setCancelable(false);
	}
	

	private void initNoUpdateVersionDialog() {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		final TextView message_prompt = (TextView) viewDia.findViewById(R.id.message_prompt);
		message_prompt.setText(R.string.str_prompt);
		log_text.setText(R.string.str_ItIsNewVersion);
		log_text.setEnabled(false);
		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);
		btn_cancel.setVisibility(View.GONE);
		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideDialog(dialog);
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(context, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
		
	}
}
