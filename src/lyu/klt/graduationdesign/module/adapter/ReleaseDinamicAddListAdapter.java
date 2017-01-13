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
 * @Description: TODO(动态模块下按钮Add的dialog中的List的adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午1:54:38
 * 
 */
public class ReleaseDinamicAddListAdapter extends BaseAdapter {

	private Context mContext;
	private String[] mlist = { "发布动态", "添加好友" };

	public ReleaseDinamicAddListAdapter(Context context) {
		this.mContext = context;

	}

	static class ViewHolder {

		TextView tv_release_dynamic_list_item;

	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mlist.length;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mlist[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			holder = new ViewHolder();
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_release_dynamic_add, parent, false);
			holder.tv_release_dynamic_list_item=(TextView) convertView.findViewById(R.id.tv_release_dynamic_list_item);
			
			holder.tv_release_dynamic_list_item.setText(mlist[position]);
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;

	}

}
