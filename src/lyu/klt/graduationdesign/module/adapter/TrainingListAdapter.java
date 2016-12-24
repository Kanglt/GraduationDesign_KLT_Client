/**     
*/
package lyu.klt.graduationdesign.module.adapter;

import java.util.HashMap;
import java.util.List;
import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.OnItemTouchListener;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.module.bean.TrainingDataListPo;
import lyu.klt.graduationdesign.module.bean.TrainingDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;

/**
 * @ClassName: TrainingListAdapter
 * @Description: TODO(推荐模块下的子模块（训练）饮食模块下的listView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午1:54:38
 * 
 */
public class TrainingListAdapter extends BaseAdapter {

	private Context mContext;
	// private List<TrainingDataPo> trainingDataList;

	private RecyclerView rv_training_fargment;
	private TrainingRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<TrainingDataListPo> trainingDataListPo;
	private String itemType = "listFooter";

	public TrainingListAdapter(Context context, List<TrainingDataListPo> trainingDataListPo) {
		this.mContext = context;
		this.trainingDataListPo = trainingDataListPo;

	}

	static class ViewHolder {
		// ImageView status_img;
		// TextView status_message_detail_text;
		TextView tv_list_title;
		RecyclerView rv_staggered;
	}

	static class ViewHolderFooter {
		// ImageView status_img;
		// TextView status_message_detail_text;
		TextView tv_list_footer;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return trainingDataListPo.size();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return trainingDataListPo.get(position);
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		TrainingDataListPo trainingDataPo = trainingDataListPo.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.listview_list_item_recommended, parent, false);
			rv_training_fargment = (RecyclerView) convertView.findViewById(R.id.rv_staggered);
			holder.tv_list_title = (TextView) convertView.findViewById(R.id.tv_list_title);
			holder.tv_list_title.setText(trainingDataPo.getTrianingList().get(0).getTitleName());
			mLayoutManager = new MyLinearLayoutManger(mContext, LinearLayout.HORIZONTAL, false);
			rv_training_fargment.setLayoutManager(mLayoutManager);

			mAdapter = new TrainingRecyclerAdapter(mContext, 2, trainingDataPo.getTrianingList());
			rv_training_fargment.setAdapter(mAdapter);
			rv_training_fargment.setItemAnimator(new DefaultItemAnimator());
			// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
			SpacesItemDecoration decoration = new SpacesItemDecoration(5);
			rv_training_fargment.addItemDecoration(decoration);
			
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		
		
		MyApplication.rv_training_fargment = rv_training_fargment;
		return convertView;

	}

}
