package lyu.klt.frame.util;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup.MarginLayoutParams;
import android.widget.Gallery;
import android.widget.TextView;

public class ViewUtil {
	public static void SetEditText(TextView textview, Object str) {

		if (textview != null && str != null) {
			if (!StringUtil.isEmpty(str.toString()))
				textview.setText(str.toString());
		}

	}
	/**
	 * Align the first gallery item to the left.
	 * 
	 * @param parentView
	 *            The view containing the gallery widget (The gallery width must
	 *            be set to match_parent)
	 * @param gallery
	 *            The gallery we have to change
	 * @param mItemWidth
	 *            The gallery item width
	 * @param mItemSpacing
	 *            The gallery item spacing
	 */
	public static void alignGalleryToLeft(Activity context, View parentView,
			Gallery gallery, int mItemWidth, int mItemSpacing,
			int margeLeftAndRight) {
		int galleryWidth;
		if (parentView != null) {
			MarginLayoutParams lp = (MarginLayoutParams) parentView
					.getLayoutParams();
			galleryWidth = context.getWindowManager().getDefaultDisplay()
					.getWidth()
					- lp.leftMargin - lp.rightMargin;
		} else {
			galleryWidth = context.getWindowManager().getDefaultDisplay()
					.getWidth()
					- 2 * margeLeftAndRight;
		}
		int itemWidth = mItemWidth;
		int spacing = mItemSpacing;

		// The offset is how much we will pull the gallery to the left in order
		// to simulate
		// left alignment of the first item
		int offset;
		if (galleryWidth <= itemWidth) {
			offset = galleryWidth / 2 - itemWidth / 2 - spacing;
		} else {
			offset = galleryWidth - itemWidth - 2 * spacing;
		}

		// Now update the layout parameters of the gallery in order to set the
		// left margin
		MarginLayoutParams mlp = (MarginLayoutParams) gallery.getLayoutParams();
		mlp.setMargins(-offset, mlp.topMargin, mlp.rightMargin,
				mlp.bottomMargin);
	}

		
}
