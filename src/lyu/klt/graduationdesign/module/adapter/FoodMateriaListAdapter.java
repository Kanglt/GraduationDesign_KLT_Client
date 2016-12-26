/**     
*/
package lyu.klt.graduationdesign.module.adapter;

import java.util.HashMap;
import java.util.List;
import com.lyu.graduationdesign_klt.R;

import android.content.Context;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.graduationdesign.module.bean.FoodMateriaPo;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/** 
* @ClassName: DietListAdapter 
* @Description: TODO(食品材料列表的适配器) 
* @author 康良涛 
* @date 2016年12月16日 下午1:54:38 
*  
*/
public class FoodMateriaListAdapter extends BaseAdapter{

	private Context mContext;
	private List<FoodMateriaPo> foodMateriaPoList;



	public FoodMateriaListAdapter(Context context,List<FoodMateriaPo> foodMateriaPoList
			) {
		this.mContext = context;
		this.foodMateriaPoList=foodMateriaPoList;
		
	}

	

	static class ViewHolder {

		TextView tv_foodMaterialName;
		TextView tv_foodMateriaNum;

	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getCount()
	 */
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return foodMateriaPoList.size();
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItem(int)
	 */
	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return foodMateriaPoList.get(position);
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getItemId(int)
	 */
	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}



	/* (non-Javadoc)
	 * @see android.widget.Adapter#getView(int, android.view.View, android.view.ViewGroup)
	 */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		FoodMateriaPo foodMateriaPo=foodMateriaPoList.get(position);
		if (convertView == null) {
			holder = new ViewHolder();
			
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_foodmateria, parent,false);
			
			holder.tv_foodMaterialName=(TextView) convertView.findViewById(R.id.tv_foodMaterialName);
			holder.tv_foodMateriaNum=(TextView) convertView.findViewById(R.id.tv_foodMateriaNum);
			
			
			holder.tv_foodMaterialName.setText(foodMateriaPo.getFoodMaterialName());
			holder.tv_foodMateriaNum.setText(foodMateriaPo.getFoodMateriaNum());
			
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		return convertView;
		
	}
}
