/*
 * Copyright (C) 2012 www.amsoft.cn
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package lyu.klt.frame.ab.view.calendar;

import java.util.Calendar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;
import lyu.klt.frame.ab.view.calendar.CalendarView.AbOnItemClickListener;

// TODO: Auto-generated Javadoc
/**
 * © 2012 amsoft.cn 名称：CalendarCell.java 描述：日历控件单元格绘制类
 * 
 * @author 还如一梦中
 * @version v1.0
 * @date：2013-7-9 下午3:54:16
 */
public class CalendarCell extends View {

	// 字体大小
	/** The text size. */
	private int textSize = 20;

	// 基本元素
	/** The m on item click listener. */
	private AbOnItemClickListener mOnItemClickListener;

	/** The pt. */
	private Paint pt = new Paint();

	/** The rect. */
	private RectF rect = new RectF();

	// 显示的文字
	/** The text date value. */
	private String textDateValue = "";

	// 当前日期
	/** The i date year. */
	private int iDateYear = 0;

	/** The i date month. */
	private int iDateMonth = 0;

	/** The i date day. */
	private int iDateDay = 0;

	/** The i date week. */
	private int iDateWeek = 0;
	// 布尔变量
	/** The is selected. */
	private boolean isSelected = false;

	/** The is active month. */
	private boolean isActiveMonth = false;

	/** The is today. */
	private boolean isToday = false;

	/** The b touched down. */
	private boolean bTouchedDown = false;

	/** The is holiday. */
	private boolean isHoliday = false;

	/** The has record. */
	private boolean hasRecord = false;

	/** 有正常数据 。。 */
	private boolean hasNormal = false;
	
	private boolean hasSelect = false;
	
//	private Bitmap bitmap;
	
	private boolean isGreen = false;

	// 当前cell的序号
	/** The position. */
	private int position = 0;

	/** The anim alpha duration. */
	public static int ANIM_ALPHA_DURATION = 100;

	/* 被选中的cell颜色 */
	/** The select cell color. */
	private int selectCellColor = Color.rgb(239, 239, 239);

	/* 最大背景颜色 */
	/** The bg color. */
    private int bgColor = Color.rgb(163, 163, 163);

    /* 数字颜色 */
	/** The number color. */
	private int numberColor = Color.rgb(86, 86, 86);

	/* cell背景颜色 */
	/** The cell color. */
	private int cellColor = Color.WHITE;

	/* 非本月的数字颜色 */
	/** The not active month color. */
	private int notActiveMonthColor = Color.rgb(255, 255, 255);

	/* 今天cell颜色 */
	/** The today color. */
//	private int todayColor = Color.rgb(1, 141, 255);
	private int todayColor = Color.rgb(17, 183, 243);
	/* 今天数字颜色 */
	private int todayTextColor = Color.WHITE;

	/* 周末数字颜色 */
	private int weekendTextColor = Color.rgb(134, 134, 134);

	/* 异常的标注颜色 */
//	private int errorColor = Color.rgb(212, 117, 99);
	private int errorColor = Color.rgb(230, 0, 0);
	/* 迟到的标注颜色 */
	private int lateColor = Color.rgb(246, 181, 79);
	/* 迟到的标注颜色 */
	private int tardyColor = Color.rgb(95, 135, 249);
	
	private int greenColor =Color.rgb(71, 161, 00);

	private String unloadText="";  //写在上面下面的文字;
	
    public void setBgColor(int bgColor) {
		this.bgColor = bgColor;
	}
	public CalendarCell(Context context) {
		super(context);
	}

	public int getGreenColor() {
		return greenColor;
	}

	public void setGreenColor(int greenColor) {
		this.greenColor = greenColor;
	}
	// 构造函数
	/**
	 * Instantiates a new calendar cell.
	 * 
	 * @param context
	 *            the context
	 * @param position
	 *            the position
	 * @param iWidth
	 *            the i width 
	 * @param iHeight
	 *            the i height
	 */
	public CalendarCell(Context context, int position, int iWidth, int iHeight,
			int textSize,int bgColor) {
		super(context);
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
		this.position = position;
		this.textSize = textSize;
		this.bgColor=bgColor;

	}

	/**
	 * 描述：获取这个Cell的日期.
	 * 
	 * @return the this cell date
	 */
	public Calendar getThisCellDate() {
		Calendar calDate = Calendar.getInstance();
		calDate.clear();
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}
 
	/**
	 * 描述：设置这个Cell的日期.
	 * 
	 * @param iYear
	 *            the i year
	 * @param iMonth
	 *            the i month
	 * @param iDay
	 *            the i day
	 * @param isToday
	 *            the is today
	 * @param isSelected
	 *            the is selected
	 * @param isHoliday
	 *            the is holiday
	 * @param isActiveMonth
	 *            the is active month
	 * @param hasRecord
	 *            the has record
	 */
	public void setThisCellDate(int iYear, int iMonth, int iDay,
			Boolean isToday, Boolean isSelected, Boolean isHoliday,
			int isActiveMonth, boolean hasRecord, boolean hasNormal) {
		iDateYear = iYear;
		iDateMonth = iMonth;
		iDateDay = iDay;

		this.textDateValue = Integer.toString(iDateDay);
		this.isActiveMonth = (iDateMonth == isActiveMonth);
		this.isToday = isToday;
		this.isHoliday = isHoliday;
		this.hasRecord = hasRecord;
		this.hasNormal = hasNormal;
		this.isSelected = isSelected;
	}

	/**
	 * 描述：重载绘制方法.
	 * 
	 * @param canvas
	 *            the canvas
	 * @see android.view.View#onDraw(android.graphics.Canvas)
	 */
	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);

		canvas.drawColor(bgColor);
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(0.5f, 0.5f);

		final boolean bFocused = IsViewFocused();

		drawDayView(canvas, bFocused);
		drawDayNumber(canvas);
	}

	/**
	 * Checks if is view focused.
	 * 
	 * @return true, if successful
	 */
	public boolean IsViewFocused() {
		return (this.isFocused() || bTouchedDown);
	}

	/**
	 * 描述：绘制日历方格.
	 * 
	 * @param canvas
	 *            the canvas
	 * @param bFocused
	 *            the b focused
	 */
	private void drawDayView(Canvas canvas, boolean bFocused) {

		pt.setColor(getCellColor());
		pt.setAntiAlias(true);
//		canvas.drawRect(rect, pt);
        canvas.drawCircle(this.getWidth()/2,this.getWidth()/2,this.getWidth()/5*2, pt);
		if (hasRecord) {
			createReminder(canvas, errorColor);
		}

		if (hasNormal) {
//			createReminder(canvas, Color.GREEN);
			createTardyReminder(canvas, tardyColor);
		}
		if (hasSelect) {
			createBackground(canvas);
		}

	}

    /**
     * 描述：选中的要请假的日期
     * @param hasSelect
     */
	public void setHasSelect(boolean hasSelect ,String text) {
		this.unloadText=text;
		this.hasSelect = hasSelect;
		isSelected = false;
	}
    /**
     * 描述：设置选择的字体为绿色
     * @param hasSelect
     */
	public void setIsGreen(boolean isGreen) {
		this.isGreen = isGreen;
	}
	/**
	 * 描述：绘制日历中的数字.
	 * 
	 * @param canvas
	 *            the canvas
	 */
	public void drawDayNumber(Canvas canvas) {
		// draw day number
		pt.setTypeface(null);
		pt.setAntiAlias(true);
		pt.setShader(null);
		pt.setFakeBoldText(true);
		pt.setTextSize(textSize);
		if (isToday) {
			pt.setColor(todayTextColor);
		}
		else {
//			if(isGreen){
//				pt.setColor(greenColor);
//			}else{
				if (isHoliday) {
				pt.setColor(weekendTextColor);
				}
				else{
				pt.setColor(todayColor);	
				}
			}
//			
//		}
		pt.setUnderlineText(false);

		if (!isActiveMonth) {
			pt.setColor(notActiveMonthColor);
			
		}

		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)
				- ((int) pt.measureText(textDateValue) >> 1);
		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);
		canvas.drawText(textDateValue, iPosX, iPosY, pt);
	}

	/**
	 * 描述：得到字体高度.
	 * 
	 * @return the text height
	 */
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	/**
	 * 描述：根据条件返回不同颜色值.
	 * 
	 * @return the cell color
	 */
	public int getCellColor() {
		if(isActiveMonth){
			if (isToday) {
				return todayColor;
			}
		}
		

		if (isSelected) {
			return selectCellColor;
		}

		// 如需周末有特殊背景色
		if (isHoliday) {
			return cellColor;
		}

		// 默认是白色的单元格
		return cellColor;
	}

	/**
	 * 描述：设置是否被选中.
	 * 
	 * @param selected
	 *            the new selected
	 */
	@Override
	public void setSelected(boolean selected) {
		if (this.isSelected != selected) {
			this.isSelected = selected;
			this.invalidate();
		}
	}

	/**
	 * 描述：设置是否有数据.
	 * 
	 * @param hasRecord
	 *            the new checks for record
	 */
	public void setHasRecord(boolean hasRecord) {
		if (this.hasRecord != hasRecord) {
			this.hasRecord = hasRecord;
			this.invalidate();
		}
	}

	/**
	 * 描述：项目需求设置是否有正常数据.
	 * 
	 * @param hasNormal
	 *            the new checks for record
	 */
	public void setHasNormal(boolean hasNormal) {
		if (this.hasNormal != hasNormal) {
			this.hasNormal = hasNormal;
			this.invalidate();
		}
	}

	/**
	 * 描述：设置点击事件.
	 * 
	 * @param onItemClickListener
	 *            the new on item click listener
	 */
	public void setOnItemClickListener(AbOnItemClickListener onItemClickListener) {
		this.mOnItemClickListener = onItemClickListener;
	}

	/**
	 * 描述：执行点击事件.
	 */
	public void doItemClick() {
		if (mOnItemClickListener != null) {
			mOnItemClickListener.onClick(position);
		}
	}

	/**
	 * 描述：TODO.
	 * 
	 * @version v1.0
	 * @param event
	 *            the event
	 * @return true, if successful
	 * @see android.view.View#onTouchEvent(android.view.MotionEvent)
	 * @author: amsoft.cn
	 * @date：2013-7-19 下午4:31:18
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(CalendarCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	/**
	 * 描述：TODO.
	 * 
	 * @version v1.0
	 * @param keyCode
	 *            the key code
	 * @param event
	 *            the event
	 * @return true, if successful
	 * @see android.view.View#onKeyDown(int, android.view.KeyEvent)
	 * @author: amsoft.cn
	 * @date：2013-7-19 下午4:31:18
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	/**
	 * 描述：动画不透明度渐变.
	 * 
	 * @param view
	 *            the view
	 */
	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}

	/**
	 * 描述：有记录时的样子.
	 * 
	 * @param canvas
	 *            the canvas
	 * @param Color
	 *            the color
	 */
	public void createReminder(Canvas canvas, int Color) {
		pt.setUnderlineText(true);
		pt.setStyle(Paint.Style.FILL_AND_STROKE);
		pt.setColor(Color);
//		Path path = new Path();
//		path.moveTo(rect.right - rect.width() / 4, rect.top);
//		path.lineTo(rect.right, rect.top);
//		path.lineTo(rect.right, rect.top + rect.width() / 4);
//		path.lineTo(rect.right - rect.width() / 4, rect.top);
//		path.close();	
//		canvas.drawPath(path, pt);
		pt.setAntiAlias(true);
        canvas.drawCircle(this.getWidth()/2,this.getWidth()/2+this.getWidth()/9*2,this.getWidth()/17, pt);
//        canvas.drawCircle(this.getWidth()/2+this.getWidth()/12,this.getWidth()/2+this.getWidth()/9*2,this.getWidth()/17, pt);
		
//		pt.setUnderlineText(true);
	}
	
	public void createTardyReminder(Canvas canvas, int Color) {
		pt.setUnderlineText(true);
		pt.setStyle(Paint.Style.FILL_AND_STROKE);
		pt.setColor(Color);
		pt.setAntiAlias(true);
        canvas.drawCircle(this.getWidth()/2+this.getWidth()/12,this.getWidth()/2+this.getWidth()/9*2,this.getWidth()/17, pt);
	}
	/**
	 * 描述：绘制请假时要显示的背景图片
	 */
	public void createBackground(Canvas canvas) {
		WindowManager wm = (WindowManager) getContext()
                .getSystemService(Context.WINDOW_SERVICE);
		 int width = wm.getDefaultDisplay().getWidth();
		 width /=7;
		numberColor=Color.WHITE;
		pt.setAntiAlias(true);
		//pt.setColor(Color.BLACK);
		//pt.setStrokeWidth((float) 3.0);
		//pt.setStyle(Style.STROKE);
		//canvas.drawBitmap(bitmap, null, rect, pt);
		/*设置paint 的style为 FILL：实心*/
        pt.setStyle(Paint.Style.FILL);
        /*设置paint的颜色*/
        pt.setColor(Color.rgb(148, 212, 1));
        /*画一个实心圆*/
        pt.setTextSize(width/5);
        canvas.drawCircle(width/2,width/2,width/4, pt);
        canvas.drawText(unloadText, (float) ((float)width/3.5), (float)width/11*10, pt);
		pt.setUnderlineText(true);
	}

	/**
	 * 描述：绘制图片
	 */
	public void createBitMap(Canvas canvas, Bitmap bitmap) {
		pt.setAntiAlias(true);
		pt.setColor(Color.BLACK);
		pt.setStrokeWidth((float) 3.0);
		pt.setStyle(Style.STROKE);
//		canvas.drawBitmap(bitmap, null, dst, pt);
		pt.setUnderlineText(true);
	}

	/**
	 * 描述：是否为活动的月.
	 * 
	 * @return true, if is active month
	 */
	public boolean isActiveMonth() {
		return isActiveMonth;
	}

	public int getTodayColor() {
		return todayColor;
	}

	/**   
	 * @Title: setTodayColor   
	 * @Description: 设置当前日期背景颜色  
	 * @param: @param todayColor      
	 * @return: void      
	 * @throws   
	 */
	public void setTodayColor(int todayColor) {
		this.todayColor = todayColor;
	}
}
