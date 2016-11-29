/*   
 * Copyright (c) 2015-2020 FJZhongXingDianzi. All Rights Reserved.   
 *   
 * This software is the confidential and proprietary information of   
 * Founder. You shall not disclose such Confidential Information   
 * and shall use it only in accordance with the terms of the agreements   
 * you entered into with Founder.   
 *   
 */ 
package lyu.klt.graduationdesign.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;

/**   
 * This class is used for ...   
 * @author   沈龙琴
 * @version   1.0 
 * @datetime 2016年5月10日 下午4:00:04   
 */
public class DrawableEditText extends EditText {  
  
    private DrawableLeftListener mLeftListener ;  
    private DrawableRightListener mRightListener ;  
  
    final int DRAWABLE_LEFT = 0;  
    final int DRAWABLE_TOP = 1;  
    final int DRAWABLE_RIGHT = 2;  
    final int DRAWABLE_BOTTOM = 3;  
  
    public DrawableEditText(Context context, AttributeSet attrs) {  
        super(context, attrs);  
    }  
  
    public DrawableEditText(Context context, AttributeSet attrs, int defStyleAttr) {  
        super(context, attrs, defStyleAttr);  
    }  
  
  
    public DrawableEditText(Context context) {  
        super(context);  
    }  
  
    public void setDrawableLeftListener(DrawableLeftListener listener) {  
        this.mLeftListener = listener;  
    }  
  
    public void setDrawableRightListener(DrawableRightListener listener) {  
        this.mRightListener = listener;  
    }  
  
    public interface DrawableLeftListener {  
        public void onDrawableLeftClick(View view) ;  
    }  
  
    public interface DrawableRightListener {  
        public void onDrawableRightClick(View view) ;  
    }  
  
    @Override  
    public boolean onTouchEvent(MotionEvent event) {  
        switch (event.getAction()) {  
            case MotionEvent.ACTION_UP:  
                if (mRightListener != null) {  
                    Drawable drawableRight = getCompoundDrawables()[DRAWABLE_RIGHT] ;  
                    if (drawableRight != null && event.getRawX() >= (getRight() - drawableRight.getBounds().width())) {  
                        mRightListener.onDrawableRightClick(this) ;  
                        return true ;  
                    }  
                }  
  
                if (mLeftListener != null) {  
                    Drawable drawableLeft = getCompoundDrawables()[DRAWABLE_LEFT] ;  
                    if (drawableLeft != null && event.getRawX() <= (getLeft() + drawableLeft.getBounds().width())) {  
                        mLeftListener.onDrawableLeftClick(this) ;  
                        return true ;  
                    }  
                }  
                break;  
        }  
  
        return super.onTouchEvent(event);  
    }  
}  
