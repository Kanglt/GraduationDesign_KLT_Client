/**     
*/
package lyu.klt.graduationdesign.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import android.os.CountDownTimer;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;

/** 
* @ClassName: MyCountDownTimer 
* @Description: TODO(倒计时) 
* @author 康良涛 
* @date 2016年12月26日 上午11:00:08 
*  
*/
public class MyCountDownTimer extends AdvancedCountdownTimer {
	
	private static SimpleDateFormat format ;
	private static String date ;
	private static int totalSize,totalSizeCountDownTimer;

	/**
	 * @param millisInFuture
	 * @param countDownInterval
	 */
	public MyCountDownTimer(long millisInFuture, long countDownInterval) {
		super(millisInFuture, countDownInterval);
		// TODO Auto-generated constructor stub
		format = new SimpleDateFormat("mm:ss");
		totalSize=(int) millisInFuture;
		totalSizeCountDownTimer=(int) millisInFuture;
	}



	/* (non-Javadoc)
	 * @see android.os.CountDownTimer#onFinish()
	 */
	@Override
	public void onFinish() {
		// TODO Auto-generated method stub
		date = format.format(new Date(totalSize));
		VideoDisplayActivity.tv_progress.setText(date);
	}

	/* (non-Javadoc)
	 * @see lyu.klt.graduationdesign.util.AdvancedCountdownTimer#onTick(long, int)
	 */
	@Override
	public void onTick(long millisUntilFinished, int percent) {
		// TODO Auto-generated method stub
		totalSizeCountDownTimer-=1000;
		date = format.format(new Date(totalSizeCountDownTimer));
		VideoDisplayActivity.tv_progress.setText(date);
		
	}

}
