/**     
*/
package lyu.klt.graduationdesign.view;



import com.lyu.graduationdesign_klt.R;

import android.app.Dialog;
import android.content.Context;
import android.support.v4.os.CancellationSignal.OnCancelListener;
import android.view.LayoutInflater;
import android.view.View;

/** 
* @ClassName: AlertDialogCommon 
* @Description: TODO(这里用一句话描述这个类的作用) 
* @author 康良涛 
* @date 2017年1月13日 下午3:02:21 
*  
*/
public class AlertDialogCommon extends Dialog {

    private View rootView;
    private Context context;
    private int rid;

    public AlertDialogCommon(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    public AlertDialogCommon(Context context, int rid) {
        super(context, R.style.dialog2);
        this.context = context;
        this.rid = rid;
        init();
    }

    public void init(){
        rootView = LayoutInflater.from(context).inflate(rid,null);
        setContentView(rootView);
    }

    public void showDialog(){
        this.show(); 
    }

    public View getView(){
        return rootView;
    }
}
