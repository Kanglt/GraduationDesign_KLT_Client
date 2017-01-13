package lyu.klt.frame.photoselector.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AbsListView.LayoutParams;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.photoselector.model.PhotoModel;
import lyu.klt.frame.photoselector.ui.PhotoItem.onItemClickListener;
import lyu.klt.frame.photoselector.ui.PhotoItem.onPhotoItemCheckedListener;
import lyu.klt.frame.photoselector.util.CommonUtils;

import com.lyu.graduationdesign_klt.R;

public class PhotoSelectorAdapter extends MBaseAdapter<PhotoModel> {

	private int itemWidth;
	private int horizentalNum = 3;
	private onPhotoItemCheckedListener listener;
	private LayoutParams itemLayoutParams;
	private onItemClickListener mCallback;
	private OnClickListener cameraListener;

	private PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models) {
		super(context, models);
	}

	public PhotoSelectorAdapter(Context context, ArrayList<PhotoModel> models, int screenWidth,
			onPhotoItemCheckedListener listener, onItemClickListener mCallback, OnClickListener cameraListener) {
		this(context, models);
		setItemWidth(screenWidth);
		this.listener = listener;
		this.mCallback = mCallback;
		this.cameraListener = cameraListener;
	}

	/** 设置每一个Item的宽高 */
	public void setItemWidth(int screenWidth) {
		int horizentalSpace = context.getResources().getDimensionPixelSize(R.dimen.sticky_item_horizontalSpacing);
		this.itemWidth = (screenWidth - (horizentalSpace * (horizentalNum - 1))) / horizentalNum;
		this.itemLayoutParams = new LayoutParams(itemWidth, itemWidth);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		PhotoItem item = null;
		TextView tvCamera = null;
		if (position == 0 && CommonUtils.isNull(models.get(position).getOriginalPath())) { // 当时第一个时，显示按钮
			if (convertView == null || !(convertView instanceof TextView)) {
				tvCamera = (TextView) LayoutInflater.from(context).inflate(R.layout.view_camera, null);
				tvCamera.setHeight(itemWidth);
				tvCamera.setWidth(itemWidth);
				convertView = tvCamera;
			}
			convertView.setOnClickListener(cameraListener);
		} else { // 显示图片
			if (convertView == null || !(convertView instanceof PhotoItem)) {
				item = new PhotoItem(context, listener);
				item.setLayoutParams(itemLayoutParams);
				convertView = item;
			} else {
				item = (PhotoItem) convertView;
			}

			item.setImageDrawable(models.get(position));
			item.setSelected(models.get(position).isChecked());
			item.setOnClickListener(mCallback, position);
		}
		return convertView;
	}
}
