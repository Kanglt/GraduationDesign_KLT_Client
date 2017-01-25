/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

/**
 * @ClassName: WebActivity
 * @Description: TODO(轮播图活动界面)
 * @author 康良涛
 * @date 2017年1月25日 下午12:05:31
 * 
 */
public class WebActivity extends BaseActivity {

	private static final String TAG = ForgotPasswordActivity.class.getSimpleName();
	private Activity context;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private ImageView right_img;

	private String activityURL = "";

	private WebView webView;
	private WebSettings myWebSettings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_web_layout);
		init();
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub

		initUtil();
		initData();
		initView();
		initViewData();
		initEvent();
		startGame();
	}

	@Override
	public void initUtil() {
		// TODO Auto-generated method stub
		super.initUtil();
		context = this;
		MyApplication.getInstance().addActivity(this);
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		activityURL = getIntent().getStringExtra("activityURL");

	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		titlebar_view = findViewById(R.id.title_bar_layout);
		title_bar_left_img_layout = findViewById(R.id.title_bar_left_img_layout);
		title_bar_left_img = (ImageView) findViewById(R.id.title_bar_left_img);
		title_bar_text = (TextView) findViewById(R.id.title_bar_text);
		titlebar_right = (LinearLayout) titlebar_view.findViewById(R.id.title_bar_right_layout);

		webView = (WebView) findViewById(R.id.webView);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return2));
		title_bar_text.setText("活动");

		try {

			myWebSettings = webView.getSettings();
			myWebSettings.setJavaScriptEnabled(true);
			webView.loadUrl(activityURL);
			// myWebView.loadUrl("http://www.baidu.com");
			webView.setWebViewClient(new WebViewClient() {
				@Override
				public boolean shouldOverrideUrlLoading(WebView view, String url) {

					view.loadUrl(url);
					return true;
				}
			});
		} catch (Exception e) {
			AbToastUtil.showToast(context, "can not load html file");
		}
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		title_bar_left_img.setOnClickListener(onClickListener);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}

	private OnClickListener onClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.title_bar_left_img:
				finish();
				break;

			default:
				break;
			}
		}
	};

}
