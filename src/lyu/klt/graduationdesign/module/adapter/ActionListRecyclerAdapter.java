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
import android.os.Handler;
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
import android.widget.Toast;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbSharedUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.module.adapter.TrainingRecyclerAdapter.TitleHolder;
import lyu.klt.graduationdesign.module.bean.ActionPo;
import lyu.klt.graduationdesign.module.bean.DietDataPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.bean.UserPPo;
import lyu.klt.graduationdesign.module.bean.UserPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.DietInfomation;
import lyu.klt.graduationdesign.moudle.activity.TrainingMotionDiagramActivity;
import lyu.klt.graduationdesign.moudle.activity.TrainingMotionDiagramListActivity;
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
* @ClassName: QueryActionListRecyclerAdapter 
* @Description: TODO(动作列表) 
* @author 康良涛 
* @date 2017年1月24日 下午9:54:23 
*
 */
public class ActionListRecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
	private final static String TAG = "QueryActionListRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<ActionPo> actionPoList;

	private String[] photoNameArray;
	private String photoName;
	private FileInputStream in = null;
	private Bitmap myBitmap;
	private InputStream isFocusIs = null;
	private Bitmap FocusBitmap;
	private Bitmap noFocusBitmap;

	private Dialog dialog;

	public ActionListRecyclerAdapter(Context context, int type, List<ActionPo> actionPoList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.actionPoList = actionPoList;

	}

	@Override
	public int getItemCount() {
		return actionPoList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.listview_list_item_training_motion_diagram_list, vg, false);
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

		holder.tv_actionName.setText(actionPoList.get(position).getActionName());
		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				intent.setClass(mContext, TrainingMotionDiagramActivity.class);
				intent.putExtra("actionPo", actionPoList.get(position));
				mContext.startActivity(intent);

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

		public TextView tv_actionName;

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);

			tv_actionName = (TextView) v.findViewById(R.id.tv_actionName);

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

}
