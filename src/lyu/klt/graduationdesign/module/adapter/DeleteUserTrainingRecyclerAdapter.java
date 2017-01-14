package lyu.klt.graduationdesign.module.adapter;

import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Message;
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
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbDialogUtil;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.VideoDownLoadDialog;
import lyu.klt.graduationdesign.module.fargment.FitnessFargmentActivity;
import lyu.klt.graduationdesign.module.fargment.TrainingFargmentActivity;
import lyu.klt.graduationdesign.moudle.activity.DeleteUserTrainingActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.TrainingDataPAI;
import lyu.klt.graduationdesign.moudle.client.Constant;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(用户已添加训练的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class DeleteUserTrainingRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>{
	private final static String TAG = "UserTrainingRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<TrainingDataListPo> trainingDataPoList;
	
	private Dialog dialog;

	public DeleteUserTrainingRecyclerAdapter(Context context, int type, List<TrainingDataListPo> trainingDataPoList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.trainingDataPoList = trainingDataPoList;
	}

	@Override
	public int getItemCount() {
		return trainingDataPoList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_fitness, vg, false);
		holder = new TitleHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		TitleHolder holder = (TitleHolder) vh;
		LayoutParams params = holder.ll_item.getLayoutParams();
		if (mType == 1) { // 表示是线性布局
			// params.height = 50;
			holder.ll_item.setLayoutParams(params);
		} else if (mType == 2) { // 表示是网格布局
			// params.height=500;
			holder.ll_item.setLayoutParams(params);
		} 
	
		
		String strArr[] = trainingDataPoList.get(position).getTrianingList().get(0).getTrainingImage().split("/");
		String fileId=strArr[strArr.length-1];
		ImageLoaderUtil.displayImage(UrlConstant.FILE_SERVICE_DOWNLOAD_TRAININGIMAGE_URL + fileId, holder.iv_trainingImage,
				imageLoadingListener);
		
		
		holder.tv_category.setText(trainingDataPoList.get(position).getTrianingList().get(0).getCategory());
		holder.tv_num.setText(trainingDataPoList.get(position).getTrianingList().get(0).getParticipation()+"人收藏");
		holder.tv_levelAndTime.setText(trainingDataPoList.get(position).getTrianingList().get(0).getTrainingLevel()+" • "+trainingDataPoList.get(position).getTrianingList().get(0).getTrainingTime()+"分钟");
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				showDialog(mContext,position);
			}
		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {
			
				return true;
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
		//public TextView tv_title;
		public TextView tv_category;
		public TextView tv_levelAndTime;
		public TextView tv_num;
		public ImageView iv_trainingImage;
	

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_category=(TextView) v.findViewById(R.id.tv_category);
			tv_levelAndTime=(TextView) v.findViewById(R.id.tv_levelAndTime);
			tv_num=(TextView) v.findViewById(R.id.tv_num);
			iv_trainingImage=(ImageView) v.findViewById(R.id.iv_trainingImage);
			
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


	
	ImageLoadingListener imageLoadingListener = new ImageLoadingListener() {

		@Override
		public void onLoadingStarted(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingStarted");
		}

		@Override
		public void onLoadingFailed(String arg0, View arg1, FailReason arg2) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingFailed");

		}

		@Override
		public void onLoadingComplete(String arg0, View arg1, Bitmap arg2) {
			// TODO Auto-generated method stub
			Log.e("StepRecyclerAdapter", "onLoadingComplete");
		}

		@Override
		public void onLoadingCancelled(String arg0, View arg1) {
			// TODO Auto-generated method stub
			Log.e("UserTrainingRecyclerAdapter", "onLoadingCancelled");
		}
	};
	
	
	public void showDialog(final Context context,final int position) {
		final View viewDia = LayoutInflater.from(context).inflate(R.layout.message_prompt_dialog, null);
		Button btn_ok=(Button) viewDia.findViewById(R.id.btn_ok);
		Button btn_cancel=(Button) viewDia.findViewById(R.id.btn_cancel);
		EditText log_text=(EditText) viewDia.findViewById(R.id.log_text);
		log_text.setEnabled(false);
		log_text.setText("确定删除训练？");

		btn_ok.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				TrainingDataPAI.deleteUserTraining(context, AbSharedUtil.getString(mContext, Constant.LAST_LOGINID), trainingDataPoList.get(position).getTrianingList().get(0).getCategory(), deleteTrainingStringHttpResponseListener);
				
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
		//videoDownLoadDialog.setCanceledOnTouchOutside(false);//dialog弹出后会点击屏幕，dialog不消失；点击物理返回键dialog消失
	}
	
	
	private AbStringHttpResponseListener deleteTrainingStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss((BaseActivity)mContext, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(mContext, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("record"))) {
						return;
					}
					
					DeleteUserTrainingActivity.handler.sendMessage(new Message());				//	dialog.dismiss();
					
					FitnessFargmentActivity.isRefresh=true;
					
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
			AbDialogUtil.showProgressDialog(mContext, 0, "正在操作...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			((BaseActivity) mContext).HideProgressDialog();
			dialog.dismiss();

		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(mContext, error.getMessage());
		//	dialog.dismiss();
		}

	};
	
}
