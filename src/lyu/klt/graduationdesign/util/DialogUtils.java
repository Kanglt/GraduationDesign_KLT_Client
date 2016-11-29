/*   
 * Copyright (c) 2015-2020 FJZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */ 
package lyu.klt.graduationdesign.util;

import android.app.Dialog;

/**   
 * 对话框Utils
 * @author   沈龙琴
 * @version   1.0 
 * @datetime 2016年7月15日 下午7:37:15   
 */
public class DialogUtils {
	public static void showDialog(Dialog dialog) {
		if (dialog == null)
			return;
		if (!dialog.isShowing())
			dialog.show();
	}

	public static void hideDialog(Dialog dialog) {
		if (dialog == null)
			return;
		if (dialog.isShowing())
			dialog.dismiss();
	}
}
