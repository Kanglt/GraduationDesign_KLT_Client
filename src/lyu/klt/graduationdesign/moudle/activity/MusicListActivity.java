/**     
*/
package lyu.klt.graduationdesign.moudle.activity;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.http.AbStringHttpResponseListener;
import lyu.klt.frame.ab.util.AbLogUtil;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.google.gson.Gson;
import lyu.klt.frame.google.gson.reflect.TypeToken;
import lyu.klt.frame.util.GsonUtils;
import lyu.klt.frame.util.StringUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.adapter.MusicRecyclerAdapter;
import lyu.klt.graduationdesign.module.adapter.MyRecyclerAdapter;
import lyu.klt.graduationdesign.module.bean.MusicDataPo;
import lyu.klt.graduationdesign.moudle.api.ApiHandler;
import lyu.klt.graduationdesign.moudle.api.MusicDataAPI;
import lyu.klt.graduationdesign.moudle.client.MyApplication;
import lyu.klt.graduationdesign.moudle.service.MusicPlayerService;
import lyu.klt.graduationdesign.util.DataUtils;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;
import lyu.klt.graduationdesign.view.SpacesItemDecoration;

/**
 * @ClassName: MusicListActivity
 * @Description: TODO(音乐列表)
 * @author 康良涛
 * @date 2016年12月26日 下午1:57:43
 * 
 */
public class MusicListActivity extends BaseActivity {

	private static final String TAG = MusicListActivity.class.getSimpleName();
	private static Activity context;

	/**
	 * titlebar相关组件
	 */
	private View titlebar_view;// titlebar
	private View title_bar_left_img_layout;
	private ImageView title_bar_left_img;
	private TextView title_bar_text;
	private LinearLayout titlebar_right;
	private TextView titlebar_right_text;

	private RecyclerView rv_music;
	private MusicRecyclerAdapter mAdapter;
	private MyLinearLayoutManger mLayoutManager;
	private List<String> mDatas;

	private String musicType;

	private List<MusicDataPo> musicDataPoList;

	private static int currentposition = -1;// 当前播放列表里哪首音乐
	private boolean isplay = false;// 音乐是否在播放
	private static MusicPlayerService musicPlayerService = null;
	private static MediaPlayer mediaPlayer = null;
	private boolean isservicerunning = false;// 退出应用再进入时（点击app图标或者在通知栏点击service）使用，判断服务是否在启动

	private ImageView iv_musicType_bag, iv_musicType;

	private InputStream in;
	private Bitmap musicType_bagBitmap, musicType_Bitmap;

	private View rl_test;

	private static boolean serviceIsStart = false;

	public static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			if (msg.obj.equals("service_start")) {
				serviceIsStart = true;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_music_list_layout);
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
		musicType = getIntent().getStringExtra("musicType");

		if (musicType.equals("Energy")) {
			in = context.getResources().openRawResource(R.drawable.boxing_2_bag);
			musicType_bagBitmap = BitmapFactory.decodeStream(in);
			in = context.getResources().openRawResource(R.drawable.boxing_2);
			musicType_Bitmap= BitmapFactory.decodeStream(in);
		}
		if (musicType.equals("aerobic")) {
			in = context.getResources().openRawResource(R.drawable.runing_bag);
			musicType_bagBitmap = BitmapFactory.decodeStream(in);
			in = context.getResources().openRawResource(R.drawable.runing);
			musicType_Bitmap= BitmapFactory.decodeStream(in);
		}
		if (musicType.equals("Relax")) {
			in = context.getResources().openRawResource(R.drawable.runing_2_bag);
			musicType_bagBitmap = BitmapFactory.decodeStream(in);
			in = context.getResources().openRawResource(R.drawable.runing_2);
			musicType_Bitmap= BitmapFactory.decodeStream(in);
		}
		if (musicType.equals("Yoga")) {
			in = context.getResources().openRawResource(R.drawable.yoga_bag);
			musicType_bagBitmap = BitmapFactory.decodeStream(in);
			in = context.getResources().openRawResource(R.drawable.yoga);
			musicType_Bitmap= BitmapFactory.decodeStream(in);
		}
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		titlebar_right_text = (TextView) titlebar_right.findViewById(R.id.title_bar_right_text);

		rv_music = (RecyclerView) findViewById(R.id.rv_music);

		iv_musicType_bag = (ImageView) findViewById(R.id.iv_musicType_bag);
		iv_musicType = (ImageView) findViewById(R.id.iv_musicType);

	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();

		titlebar_view.setAlpha(5);
		title_bar_left_img.setAlpha(100);
		title_bar_left_img.setImageDrawable(context.getResources().getDrawable(R.drawable.btn_return3));
		title_bar_text.setText(musicType);
		// title_bar_text.setTextColor(android.graphics.Color.parseColor("#FFFFFFFF"));
		// titlebar_view.setBackgroundColor(android.graphics.Color.parseColor("#7c7c7c"));

		initRVData();
		MyRecyclerAdapter recycleAdapter;
		recycleAdapter = new MyRecyclerAdapter(context, mDatas);

		// 设置Adapter
		rv_music.setAdapter(recycleAdapter);
		mLayoutManager = new MyLinearLayoutManger(context, LinearLayout.VERTICAL, false);
		rv_music.setLayoutManager(mLayoutManager);
		rv_music.setItemAnimator(new DefaultItemAnimator());
		// 每项周围的空隙是5，那么项与项之间的间隔就是5+5=10。
		SpacesItemDecoration decoration = new SpacesItemDecoration(1);
		rv_music.addItemDecoration(decoration);

		iv_musicType_bag.setImageBitmap(musicType_bagBitmap);
		iv_musicType.setImageBitmap(musicType_Bitmap);

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

		MusicDataAPI.getMusicData_with_musicType(context, musicType,
				getMusicData_with_musicTypeStringHttpResponseListener);
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

	private void initRVData() {
		mDatas = new ArrayList<String>();
		for (int i = 0; i < 1; i++) {
			mDatas.add("item" + i);
		}
	}

	private AbStringHttpResponseListener getMusicData_with_musicTypeStringHttpResponseListener = new AbStringHttpResponseListener() {

		@Override
		public void onSuccess(int statusCode, String content) {
			// TODO Auto-generated method stub

			if (!StringUtil.isEmpty(content)) {
				try {
					JSONObject returncode = new JSONObject(content);
					String data = returncode.getString("data");
					String type = returncode.getString("type");
					if (!ApiHandler.isSccuss(context, type, data)) {
						return;
					}
					// 解密数据
					data = DataUtils.getResponseData(context, data);
					JSONObject jsonObject = new JSONObject(data);

					if (StringUtil.isEmpty(jsonObject.getString("list"))) {
						return;
					}

					// UserPo userPo=new UserPo();
					Gson gson = GsonUtils.getGson();

					musicDataPoList = gson.fromJson(jsonObject.getString("list"), new TypeToken<List<MusicDataPo>>() {
					}.getType());

					mAdapter = new MusicRecyclerAdapter(context, 1, musicDataPoList);

					rv_music.setAdapter(mAdapter);
					mAdapter.notifyDataSetChanged();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

		@Override
		public void onStart() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onStart");
			// 显示进度框
			// AbDialogUtil.showProgressDialog(context, 0, "正在更新...");
		}

		@Override
		public void onFinish() {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFinish");
			// 移除进度框
			// HideProgressDialog();

			// AbDialogUtil.removeDialog(context);
		}

		@Override
		public void onFailure(int statusCode, String content, Throwable error) {
			// TODO Auto-generated method stub
			AbLogUtil.d(TAG, "onFailure");
			AbToastUtil.showToast(context, error.getMessage());
		}

	};

	public static ServiceConnection conn = new ServiceConnection() {

		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			// TODO Auto-generated method stub
			musicPlayerService = ((MusicPlayerService.musicBinder) service).getPlayInfo();
			mediaPlayer = musicPlayerService.getMediaPlayer();
		//	AbToastUtil.showToast(context, "服务获取成功！");
			currentposition = musicPlayerService.getCurposition();
		}

		@Override
		public void onServiceDisconnected(ComponentName name) {
			// TODO Auto-generated method stub
			musicPlayerService = null;
		//	AbToastUtil.showToast(context, "服务无法获取！");
		}

	};

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (serviceIsStart) {
			serviceIsStart = false;
			stopService(new Intent(context,MusicPlayerService.class));
			unbindService(conn);
		}

	}
}
