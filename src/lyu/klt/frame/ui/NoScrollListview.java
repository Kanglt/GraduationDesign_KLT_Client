/*   
 * Copyright (c) 2015-2020 FJZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */ 
package lyu.klt.frame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ListView;

/**   
 * This class is used for ...   
 * @author   沈龙琴
 * @version   1.0 
 * @datetime 2016年3月5日 上午9:54:57   
 */
public class NoScrollListview extends ListView{  
	  
    public NoScrollListview(Context context, AttributeSet attrs) {  
            super(context, attrs);  
    }  
      
    /** 
     * 设置不滚动 
     */  
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)  
    {  
            int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,  
                            MeasureSpec.AT_MOST);  
            super.onMeasure(widthMeasureSpec, expandSpec);  

    }  

}  
