/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
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
import lyu.klt.graduationdesign.module.bean.DynamicCommentsPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserDynamicAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.service.UploadImageAsyncTask;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.DialogUtils;
import lyu.klt.graduationdesign.util.ImageUntils;
import lyu.klt.graduationdesign.util.UploadUtil;
import lyu.klt.graduationdesign.util.WindowUtil;

/**
 * @ClassName: ReleaseDynamicActivity
 * @Description: TODO(新动态发布activity)
 * @author 康良涛
 * @date 2017年1月13日 下午12:49:20
 * 
 */
public class ReplyCommentsActivity extends BaseActivity {

	private static final String TAG = ReplyCommentsActivity.class.getSimpleName();
	private Activity context;

	private ImageView iv_camera;
	private ImageView iv_at;
	private ImageView iv_photo;
	private TextView tv_exit;
	private TextView tv_reply_comments;
	private EditText edi_commentsText;

	private Bitmap myBitmap;

	private Dialog dialog;

	private boolean iv_photo_is_visibility = false;

	private static String nowTime;
	private DynamicCommentsPo dynamicCommentsPo;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_reply_comments_layout);
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
		dynamicCommentsPo = (DynamicCommentsPo) getIntent().getSerializableExtra("dynamicCommentsPo");
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();

		iv_camera = (ImageView) findViewById(R.id.iv_camera);
		iv_at = (ImageView) findViewById(R.id.iv_at);
		iv_photo = (ImageView) findViewById(R.id.iv_photo);
		tv_exit = (TextView) findViewById(R.id.tv_exit);
		tv_reply_comments = (TextView) findViewById(R.id.tv_reply_comments);
		edi_commentsText = (EditText) findViewById(R.id.edi_commentsText);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		iv_photo.setVisibility(View.GONE);

		LayoutParams layoutParams = iv_photo.getLayoutParams();
		layoutParams.width = WindowUtil.getScreenWidth(context) / 2;
		layoutParams.height = WindowUtil.getScreenHeight(context) / 3;
		iv_photo.setLayoutParams(layoutParams);
		
		iv_camera.setVisibility(View.GONE);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		iv_camera.setOnClickListener(onClickListener);
		tv_exit.setOnClickListener(onClickListener);
		tv_reply_comments.setOnClickListener(onClickListener);
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
			case R.id.iv_camera:
//				if (!iv_photo_is_visibility) {
//					CommonUtils.launchActivityForResult(context, PhotoSelectorActivity.class, 0, 1);
//				} else {
//					initPhotoDialog();
//				}
				break;
			case R.id.iv_at:

				break;
			case R.id.tv_exit:
				initExitDialog();
				break;
			case R.id.tv_reply_comments:
//				if(iv_photo_is_visibility){
//					nowTime = DateUtil.getStringTime(DateUtil.FORMAT_TYPE2);
//					UserDynamicAPI.addUserDynamic(context, userId, DateUtil.getDateEN1(),
//							edi_dynamicText.getText().toString(), userId + "_dynamic" + nowTime + ".jpg",
//							addUserDynamicStringHttpResponseListener);
//					AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, true);
//				}else{
//					UserDynamicAPI.addUserDynamic(context, userId, DateUtil.getDateEN1(),
//							edi_dynamicText.getText().toString(), "isEmpty",
//							addUserDynamicStringHttpResponseListener);
//					AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, true);
//				}
				
				UserDynamicAPI.addDynamicComments(context, dynamicCommentsPo.getDynamicId()+"", AbSharedUtil.getString(context, Constant.LAST_LOGINID), edi_commentsText.getText().toString(), dynamicCommentsPo.getCommentsUserId(),dynamicCommentsPo.getCommentsUserName(), AbSharedUtil.getString(context, Constant.LAST_LOGINUSERNAME), addUserCommentsStringHttpResponseListener);
				
				break;
			default:
				break;
			}
		}
	};

//	@Override
//	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//		super.onActivityResult(requestCode, resultCode, data);
//		if (requestCode == 0 && resultCode == RESULT_OK) {
//			if (data != null && data.getExtras() != null) {
//				@SuppressWarnings("unchecked")
//				List<PhotoModel> photos = (List<PhotoModel>) data.getExtras().getSerializable("photos");
//				if (photos == null || photos.isEmpty())
//					return;
//				StringBuffer sb = new StringBuffer();
//				for (PhotoModel photo : photos) {
//					myBitmap = ImageUntils.getSmallBitmap(photo.getOriginalPath());
//					iv_photo.setImageBitmap(myBitmap);
//					iv_photo.setVisibility(View.VISIBLE);
//					iv_photo_is_visibility = true;
//				}
//
//			}
//
//		}
//	}

//	private void initPhotoDialog() {
//		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
//		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
//		final TextView message_prompt = (TextView) viewDia.findViewById(R.id.message_prompt);
//		message_prompt.setText("提示");
//		log_text.setText("再次拍照会将上一张照片替换，确定要再次拍照吗？");
//		log_text.setEnabled(false);
//		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
//		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);
//		btn_cancel.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//				DialogUtils.hideDialog(dialog);
//			}
//		});
//		btn_ok.setOnClickListener(new OnClickListener() {
//
//			@Override
//			public void onClick(View v) {
//
//				CommonUtils.launchActivityForResult(context, PhotoSelectorActivity.class, 0, 1);
//				DialogUtils.hideDialog(dialog);
//			}
//		});
//		dialog = new Dialog(context, R.style.dialog1);
//		dialog.show();
//		dialog.setContentView(viewDia);
//		dialog.setCanceledOnTouchOutside(true);
//	}

	private void initExitDialog() {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		final TextView message_prompt = (TextView) viewDia.findViewById(R.id.message_prompt);
		message_prompt.setText("提示");
		log_text.setText("评论还未发布，确定取消发布？");
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

				finish();
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(context, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			initExitDialog();
			return true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private AbStringHttpResponseListener addUserCommentsStringHttpResponseListener = new AbStringHttpResponseListener() {

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
					AbToastUtil.showToast(context, "发布成功！");
					AbSharedUtil.putBoolean(context, Constant.ISLOADEDDATE, true);
					finish();
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
//			if(iv_photo_is_visibility){
//				if (!UploadUtil.addDynamicImage(context, myBitmap, userId + "_dynamic" + nowTime + ".jpg")) {
//					return;
//				}
//			}
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

}
