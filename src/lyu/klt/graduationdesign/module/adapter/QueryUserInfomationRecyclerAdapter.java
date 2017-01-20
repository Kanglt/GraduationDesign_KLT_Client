package lyu.klt.graduationdesign.module.adapter;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter.TitleHolder;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.DietInfomation;
import lyu.klt.graduationdesign.moudle.activity.UserHomePageActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.UserAPI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.DialogUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;

/**
 * 
* @ClassName: QueryUserInfomationRecyclerAdapter 
* @Description: TODO(搜索用户) 
* @author 康良涛 
* @date 2017年1月20日 下午10:56:41 
*
 */
public class QueryUserInfomationRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
	private final static String TAG = "QueryUserInfomationRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private UserPo userPo;

	private String[] photoNameArray;
	private String photoName;
	private FileInputStream in = null;
	private Bitmap myBitmap;
	private InputStream isFocusIs = null;
	private Bitmap FocusBitmap;
	private Bitmap noFocusBitmap;
	private Dialog dialog;

	public QueryUserInfomationRecyclerAdapter(Context context, int type, UserPo userPo) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.userPo = userPo;

		try {
			isFocusIs = context.getResources().openRawResource(R.drawable.right);
			FocusBitmap = BitmapFactory.decodeStream(isFocusIs);
			isFocusIs = context.getResources().openRawResource(R.drawable.btn_add);
			noFocusBitmap = BitmapFactory.decodeStream(isFocusIs);
			isFocusIs.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public int getItemCount() {
		return 1;
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.recyclerview_list_item_user_info, vg, false);
		holder = new TitleHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		final TitleHolder holder = (TitleHolder) vh;
		LayoutParams params = holder.ll_item.getLayoutParams();
		if (mType == 1) { // 表示是线性布局
			// params.height = 50;
			holder.ll_item.setLayoutParams(params);
		} else if (mType == 2) { // 表示是网格布局
			// params.height=500;
			holder.ll_item.setLayoutParams(params);
		}

		initUserHead(holder.user_picture);
		holder.tv_user_age.setText(userPo.getUserAge());
		holder.tv_user_name.setText(userPo.getUserName() + "(" + userPo.getUserId() + ")");
		holder.tv_user_sex.setText(userPo.getUserSex());

		holder.tv_focus.setText("已关注");
		holder.iv_focus.setImageBitmap(FocusBitmap);
		holder.tv_noFocus.setText("未关注");
		holder.iv_noFocus.setImageBitmap(noFocusBitmap);

		if (userPo.getIsFocus() == 0) {
			holder.ll_noFocus.setVisibility(View.VISIBLE);
			holder.ll_Focus.setVisibility(View.GONE);
		} else if (userPo.getIsFocus() == 1) {
			holder.ll_noFocus.setVisibility(View.GONE);
			holder.ll_Focus.setVisibility(View.VISIBLE);
		}

		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(mContext, UserHomePageActivity.class);
				intent.putExtra("userId", userPo.getUserId());
				mContext.startActivity(intent);

			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				return true;
			}
		});

		holder.ll_Focus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				initFocusDialog(holder.ll_Focus, holder.ll_noFocus,
						AbSharedUtil.getString(mContext, Constant.LAST_LOGINID), userPo.getUserId());
			}
		});

		holder.ll_noFocus.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				initNoFocusDialog(holder.ll_Focus, holder.ll_noFocus,
						AbSharedUtil.getString(mContext, Constant.LAST_LOGINID), userPo.getUserId());
			}
		});

	}

	@Override
	public int getItemViewType(int position) {
		// 这里返回每项的类型，开发者可自定义头部类型与一般类型，
		// 然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class TitleHolder extends RecyclerView.ViewHolder {
		public View ll_item;
		public View ll_Focus;
		public View ll_noFocus;
		public ImageView user_picture;
		public TextView tv_user_name;
		public TextView tv_user_sex;
		public TextView tv_user_age;
		public TextView tv_user_address;
		public ImageView iv_focus;
		public TextView tv_focus;
		public ImageView iv_noFocus;
		public TextView tv_noFocus;

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			ll_Focus = (LinearLayout) v.findViewById(R.id.ll_Focus);
			user_picture = (ImageView) v.findViewById(R.id.user_picture);
			tv_user_name = (TextView) v.findViewById(R.id.tv_user_name);
			tv_user_sex = (TextView) v.findViewById(R.id.tv_user_sex);
			tv_user_age = (TextView) v.findViewById(R.id.tv_user_age);
			tv_user_address = (TextView) v.findViewById(R.id.tv_user_address);
			tv_focus = (TextView) v.findViewById(R.id.tv_focus);
			iv_focus = (ImageView) v.findViewById(R.id.iv_focus);

			ll_noFocus = (LinearLayout) v.findViewById(R.id.ll_noFocus);
			tv_noFocus = (TextView) v.findViewById(R.id.tv_noFocus);
			iv_noFocus = (ImageView) v.findViewById(R.id.iv_noFocus);

		}

	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	private OnItemLongClickListener mOnItemLongClickListener;

	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.mOnItemLongClickListener = listener;
	}

	private void initUserHead(ImageView user_photo) {
		try {
			photoNameArray = userPo.getUserPhoto().split("/");
			photoName = photoNameArray[photoNameArray.length - 1];
			in = new FileInputStream(FileUtils.SDPATH + "image/" + photoName);
			myBitmap = BitmapFactory.decodeStream(in);
			user_photo.setImageBitmap(myBitmap);
			in.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();

			ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_USERPHOTO_URL + photoName, user_photo,
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
			Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("QueryUserInfomationRecyclerAdapter", "onLoadingCancelled");
		}
	};

	private void initFocusDialog(final View ll_Focus, final View ll_noFocus, final String userId,
			final String focusId) {
		final View viewDia = LayoutInflater.from(mContext).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		log_text.setEnabled(false);
		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);

		log_text.setText("取消关注？");

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideDialog(dialog);
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_Focus.setVisibility(View.GONE);
				ll_noFocus.setVisibility(View.VISIBLE);
				UserAPI.deleteUserFocus(mContext, userId, focusId, deleteUserFocusStringHttpResponseListener);
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(mContext, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
	}

	private void initNoFocusDialog(final View ll_Focus, final View ll_noFocus, final String userId,
			final String focusId) {
		final View viewDia = LayoutInflater.from(mContext).inflate(R.layout.message_prompt_dialog, null);
		final EditText log_text = (EditText) viewDia.findViewById(R.id.log_text);
		log_text.setEnabled(false);
		Button btn_cancel = (Button) viewDia.findViewById(R.id.btn_cancel);
		Button btn_ok = (Button) viewDia.findViewById(R.id.btn_ok);

		log_text.setText("确认关注？");

		btn_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				DialogUtils.hideDialog(dialog);
			}
		});
		btn_ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				ll_noFocus.setVisibility(View.GONE);
				ll_Focus.setVisibility(View.VISIBLE);
				UserAPI.addUserFocusForMobile(mContext, userId, focusId, addUserFocusStringHttpResponseListener);
				DialogUtils.hideDialog(dialog);
			}
		});
		dialog = new Dialog(mContext, R.style.dialog1);
		dialog.show();
		dialog.setContentView(viewDia);
		dialog.setCanceledOnTouchOutside(true);
	}

	private AbStringHttpResponseListener deleteUserFocusStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss((Activity) mContext, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(mContext, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();
					AbSharedUtil.putBoolean(mContext, Constant.ISLOADEDDATE, true);
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
			AbToastUtil.showToast(mContext, error.getMessage());
		}

	};

	private AbStringHttpResponseListener addUserFocusStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss((Activity) mContext, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(mContext, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();
					AbSharedUtil.putBoolean(mContext, Constant.ISLOADEDDATE, true);

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
			AbToastUtil.showToast(mContext, error.getMessage());
		}

	};

}
