/**     
*/
package lyu.klt.graduationdesign.module.adapter;

import java.util.HashMap;
import java.util.List;
import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.bean.DynamicCommentsPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.ReplyCommentsActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;
/**
 * 
* @ClassName: DynamicHomgPageListAdapter 
* @Description: TODO(动态正文界面的listView的Adapter) 
* @author 康良涛 
* @date 2017年1月22日 下午9:17:17 
*
 */
public class DynamicHomgPageListAdapter extends BaseAdapter {
	private final static String TAG = "DynamicHomgPageListAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<DynamicCommentsPo> dynamicCommentsPoList;

	public DynamicHomgPageListAdapter(Context context, List<DynamicCommentsPo> dynamicCommentsPoList) {
		this.mContext = context;
		this.dynamicCommentsPoList = dynamicCommentsPoList;

	}

	static class ViewHolder {
		public View ll_item;
		// public TextView tv_title;
		public TextView tv_commentsUserName;
		public TextView tv_replyName;
		public TextView tv_commentsText;
		public TextView tv_reply_text;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return dynamicCommentsPoList.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return dynamicCommentsPoList.get(position);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getView(int, android.view.View,
	 * android.view.ViewGroup)
	 */
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		final DynamicCommentsPo dynamicCommentsPo = dynamicCommentsPoList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_dynamic_comments, parent, false);
			holder.ll_item = (LinearLayout) convertView.findViewById(R.id.ll_item);
			holder.tv_commentsUserName = (TextView) convertView.findViewById(R.id.tv_commentsUserName);
			holder.tv_replyName = (TextView) convertView.findViewById(R.id.tv_replyName);
			holder.tv_commentsText = (TextView) convertView.findViewById(R.id.tv_commentsText);
			holder.tv_reply_text = (TextView) convertView.findViewById(R.id.tv_reply_text);
			
			// 列表项的点击事件需要自己实现
			holder.ll_item.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
				//	AbToastUtil.showToast(mContext, position+"");
//					// 获取编辑框焦点
//					edi_reply.setFocusable(true);
//					//打开软键盘
//					InputMethodManager imm = (InputMethodManager)mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
//					imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
					Intent intent=new Intent(mContext,ReplyCommentsActivity.class);
					intent.putExtra("dynamicCommentsPo", dynamicCommentsPo);
					mContext.startActivity(intent);
				}
			});
			holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
				@Override
				public boolean onLongClick(View v) {
				
					return true;
				}
			});
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		
		if(dynamicCommentsPo.getReplyName().equals("@FIRSTCOMMENTS@")){
			holder.tv_reply_text.setVisibility(View.GONE);
			holder.tv_replyName.setVisibility(View.GONE);
		}
		holder.tv_commentsUserName.setText(dynamicCommentsPo.getCommentsUserName());
		holder.tv_replyName.setText(dynamicCommentsPo.getReplyName());
		holder.tv_commentsText.setText(dynamicCommentsPo.getCommentsText());
		
		return convertView;

	}

	
}
