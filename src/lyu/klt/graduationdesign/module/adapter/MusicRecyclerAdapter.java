package lyu.klt.graduationdesign.module.adapter;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.lyu.graduationdesign_klt.R;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.frame.util.FileUtils;
import lyu.klt.graduationdesign.module.bean.DietStepPo;
import lyu.klt.graduationdesign.module.bean.MusicDataPo;
import lyu.klt.graduationdesign.module.clickListener.OnItemClickListener;
import lyu.klt.graduationdesign.module.clickListener.OnItemLongClickListener;
import lyu.klt.graduationdesign.module.dialog.DownLoadDialog;
import lyu.klt.graduationdesign.moudle.activity.MusicListActivity;
import lyu.klt.graduationdesign.moudle.activity.VideoDisplayActivity;
import lyu.klt.graduationdesign.moudle.client.UrlConstant;
import lyu.klt.graduationdesign.moudle.service.MusicPlayerService;
import lyu.klt.graduationdesign.util.ImageLoaderUtil;
import lyu.klt.graduationdesign.view.MyLinearLayoutManger;

/**
 * 
 * @ClassName: TrainingRecyclerAdapter
 * @Description: TODO(音乐列表的RecyclerView的Adapter)
 * @author 康良涛
 * @date 2016年12月16日 下午10:01:51
 *
 */
public class MusicRecyclerAdapter extends RecyclerView.Adapter<ViewHolder>
		implements OnItemClickListener, OnItemLongClickListener {
	private final static String TAG = "MusicRecyclerAdapter";
	private Context mContext;
	private LayoutInflater mInflater;
	private int mType;
	private List<MusicDataPo> musicDataPoList;

	private MyLinearLayoutManger mLayoutManager;

	public InputStream in;
	public static Bitmap down_musicBitmap, rightBitmap;

	private static int currentposition = -1;// 当前播放列表里哪首音乐
	private boolean isplay = false;// 音乐是否在播放
	private static MusicPlayerService musicPlayerService = null;
	private static MediaPlayer mediaPlayer = null;
	private Handler handler = null;// 处理界面更新，seekbar ,textview
	private boolean isservicerunning = false;// 退出应用再进入时（点击app图标或者在通知栏点击service）使用，判断服务是否在启动
	private Intent intent = null;

	public static List<TitleHolder> viewHolderList;

	public MusicRecyclerAdapter(Context context, int type, List<MusicDataPo> musicDataPoList) {
		mContext = context;
		mInflater = LayoutInflater.from(context);
		mType = type;
		this.musicDataPoList = musicDataPoList;

		in = mContext.getResources().openRawResource(R.drawable.down_music);
		down_musicBitmap = BitmapFactory.decodeStream(in);
		in = mContext.getResources().openRawResource(R.drawable.right);
		rightBitmap = BitmapFactory.decodeStream(in);
		try {
			in.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		viewHolderList = new ArrayList<TitleHolder>();

	}

	@Override
	public int getItemCount() {
		return musicDataPoList.size();
	}

	@Override
	public ViewHolder onCreateViewHolder(ViewGroup vg, int viewType) {
		View v = null;
		ViewHolder holder = null;
		v = mInflater.inflate(R.layout.recyclerview_music_item, vg, false);
		holder = new TitleHolder(v);
		return holder;
	}

	@Override
	public void onBindViewHolder(ViewHolder vh, final int position) {
		final TitleHolder holder = (TitleHolder) vh;
		LayoutParams params = holder.ll_item.getLayoutParams();
		if (mType == 1) { // 表示是线性布局
			// params.height = 50;
			holder.ll_item.setLayoutParams(params);
		} else if (mType == 2) { // 表示是网格布局
			// params.height=500;
			holder.ll_item.setLayoutParams(params);
		}

		viewHolderList.add(holder);

		final String[] fileName = musicDataPoList.get(position).getMusicURL().split("/");

		holder.tv_musicName.setText(musicDataPoList.get(position).getMusicName());
		holder.tv_author.setText(musicDataPoList.get(position).getMusicAuthor());

		if (FileUtils.isFileExist("musics/" + fileName[fileName.length - 1])) {
			holder.iv_isDownMusic.setImageBitmap(rightBitmap);
		}

		// 列表项的点击事件需要自己实现
		holder.ll_item.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent();
				if (!FileUtils.isFileExist("musics/" + fileName[fileName.length - 1])) {
					DownLoadDialog.showMusicDownLoadDialog(mContext, fileName[fileName.length - 1],
							holder.iv_isDownMusic);
				} else {
					if (!holder.beforeisPlalMusic) {
						currentposition = position;
						player(currentposition);
						for (int i = 0; i < viewHolderList.size(); i++) {
							viewHolderList.get(i).beforeisPlalMusic=false;
							viewHolderList.get(i).iv_horn.setVisibility(View.GONE);;
						}
						holder.beforeisPlalMusic = true;
						holder.iv_horn.setVisibility(View.VISIBLE);
						holder.isPlalMusic = true;
					} else {
						if (!holder.isPlalMusic) {
							holder.isPlalMusic = true;
							currentposition = position;
							player("2");
							holder.iv_horn.setVisibility(View.VISIBLE);
						} else {
							holder.isPlalMusic = false;
							currentposition = position;
							player("1");
							holder.iv_horn.setVisibility(View.GONE);
						}
					}

					Message msg = new Message();
					msg.obj = "service_start";
					MusicListActivity.handler.sendMessage(msg);
				}
			}

		});
		holder.ll_item.setOnLongClickListener(new OnLongClickListener() {
			@Override
			public boolean onLongClick(View v) {

				return true;
			}
		});

	}

	@Override
	public int getItemViewType(int position) {
		// 这里返回每项的类型，开发者可自定义头部类型与一般类型，
		// 然后在onCreateViewHolder方法中根据类型加载不同的布局，从而实现带头部的网格布局
		return 0;
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	public class TitleHolder extends RecyclerView.ViewHolder {
		public View ll_item;
		public TextView tv_musicName;
		public TextView tv_author;
		public ImageView iv_horn;
		public ImageView iv_isDownMusic;
		public boolean isPlalMusic = false;
		public boolean beforeisPlalMusic = false;

		public TitleHolder(View v) {
			super(v);
			ll_item = (LinearLayout) v.findViewById(R.id.ll_item);
			tv_musicName = (TextView) v.findViewById(R.id.tv_musicName);
			tv_author = (TextView) v.findViewById(R.id.tv_author);
			iv_horn = (ImageView) v.findViewById(R.id.iv_horn);
			iv_isDownMusic = (ImageView) v.findViewById(R.id.iv_isDownMusic);

			intent = new Intent(mContext, MusicPlayerService.class);
			intent.setAction("player");

		}
	}

	private OnItemClickListener mOnItemClickListener;

	public void setOnItemClickListener(OnItemClickListener listener) {
		this.mOnItemClickListener = listener;
	}

	private OnItemLongClickListener mOnItemLongClickListener;

	public void setOnItemLongClickListener(OnItemLongClickListener listener) {
		this.mOnItemLongClickListener = listener;
	}

	@Override
	public void onItemClick(View view, int position) {
		// AbToastUtil.showToast(mContext, "尝试开启服务！");

	}

	@Override
	public void onItemLongClick(View view, int position) {

	}

	public void play_pause(View view) {
		// Log.i("MusicPlayerService", "MusicActivity...play_pause........." +
		// isplay);
		// 当前是pause的图标,（使用图标来判断是否播放，就不需要再新定义变量为状态了,表示没能找到得到当前背景的图片的）实际上播放着的，暂停
		// if(btn_play_pause.getBackground().getCurrent().equals(R.drawable.play)){
		if (isservicerunning) {// 服务启动着，这里点击播放暂停按钮时只需要当前音乐暂停或者播放就好
			if (isplay) {
				pause();
			} else {
				// 暂停--->继续播放
				player("2");
			}
		} else {
			if (isplay) {
				pause();
			} else {
				// Log.i("MusicPlayerService",
				// "MusicActivity...notplay.........");
				// 当前是play的图标,是 暂停 着的
				// 初始化时，没有点击列表，直接点击了播放按钮
				if (currentposition == -1) {

				} else {
					// 暂停--->继续播放
					player("2");
				}
			}
		}

	}

	private void player(String info) {

		intent.putExtra("MSG", info);
		isplay = true;

		mContext.startService(intent);

	}

	/*
	 * MSG : 0 未播放--->播放 1 播放--->暂停 2 暂停--->继续播放
	 *
	 */
	private void pause() {
		intent.putExtra("MSG", "1");
		isplay = false;

		mContext.startService(intent);
	}

	private void previousMusic() {
		if (currentposition > 0) {
			currentposition -= 1;
			player();
		} else {

		}
	}

	private void player() {
		player(currentposition);
	}

	private void player(int position) {

		String[] fileName = musicDataPoList.get(position).getMusicURL().split("/");
		intent.putExtra("curposition", position);// 把位置传回去，方便再启动时调用
		intent.putExtra("url", FileUtils.SDPATH + "musics/" + fileName[fileName.length - 1]);
		intent.putExtra("MSG", "0");
		isplay = true;

		mContext.startService(intent);
		mContext.bindService(intent, MusicListActivity.conn, Context.BIND_AUTO_CREATE);
	}

}
