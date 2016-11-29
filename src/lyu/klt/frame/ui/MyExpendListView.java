package lyu.klt.frame.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ExpandableListView;

public class MyExpendListView extends ExpandableListView { 

    public MyExpendListView(Context context, AttributeSet attrs) { 
        super(context, attrs); 
    } 

    public MyExpendListView(Context context) { 
        super(context); 
    } 

    public MyExpendListView(Context context, AttributeSet attrs, int defStyle) { 
        super(context, attrs, defStyle); 
    } 

    @Override 
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) { 

        int expandSpec = MeasureSpec.makeMeasureSpec( 
                Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST); 
        super.onMeasure(widthMeasureSpec, expandSpec); 
    } 
} 