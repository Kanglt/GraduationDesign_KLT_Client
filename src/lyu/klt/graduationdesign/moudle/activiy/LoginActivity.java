package lyu.klt.graduationdesign.moudle.activiy;

import com.lyu.graduationdesign_klt.R;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.sqlite.KangSQLiteOpenHelper;
import lyu.klt.graduationdesign.moudle.client.MyApplication;

public class LoginActivity extends BaseActivity {
	private static final String TAG = LoginActivity.class
				.getSimpleName();
	private Activity context;
		
	public KangSQLiteOpenHelper kangSQLiteOpenHelper;
	public SQLiteDatabase sqLiteDatabase;
	public String[] userNameStr=new String[]{};
	public String[] userPasswordStr=new String[]{};
	public ArrayAdapter<String> autoAdapter;
	
	public AutoCompleteTextView autotv_userName;
	public Button btn_login;
	public EditText edi_password;
	public TextView tv_changePassword,tv_registered;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setAbContentView(R.layout.activity_login);
		init();     
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		super.init();
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
		context=this;
		MyApplication.getInstance().addActivity(this);
		kangSQLiteOpenHelper = new KangSQLiteOpenHelper(context, "testDB", null, 1);	
		
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
		getLogindata();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		autotv_userName = (AutoCompleteTextView) findViewById(R.id.autotv_userName);
		edi_password=(EditText) findViewById(R.id.edi_password);
		btn_login=(Button) findViewById(R.id.btn_login);
		tv_changePassword=(TextView) findViewById(R.id.tv_changePassword);
		tv_registered=(TextView) findViewById(R.id.tv_registered);
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		autoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userNameStr);
		autotv_userName.setAdapter(autoAdapter);
		autotv_userName.setThreshold(1);//第几个字符开始检索
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		btn_login.setOnClickListener(onClickListener);
		tv_changePassword.setOnClickListener(onClickListener);
		tv_registered.setOnClickListener(onClickListener);
		
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}
	
	public void getLogindata() {
		sqLiteDatabase = kangSQLiteOpenHelper.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery("select * from 'loginData'", null);
        int i = cursor.getCount();
        userNameStr = new String[i];
    	userPasswordStr = new String[i];
        cursor.moveToNext();
        for (; cursor != null && i > 0; i--) {
        	int userNameColumnIndex = cursor.getColumnIndex("userName");
        	int userPasswordColumnIndex = cursor.getColumnIndex("userPassword");
        	userNameStr[i-1]=cursor.getString(userNameColumnIndex);
        	userPasswordStr[i-1]=cursor.getString(userPasswordColumnIndex);
            cursor.moveToNext();
        }
        sqLiteDatabase.close();
        cursor.close();
    }
	
	private OnClickListener onClickListener = new OnClickListener(){

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Intent intent=new Intent();
			switch (v.getId()) {
			case R.id.btn_login:
				String userName=autotv_userName.getText().toString();
				String userPassword=edi_password.getText().toString();
				if(!userName.isEmpty()&&!userPassword.isEmpty()){
					sqLiteDatabase = kangSQLiteOpenHelper.getReadableDatabase();
					ContentValues cv=new ContentValues();
					cv.put("userName",userName);
					cv.put("userPassword",userPassword);
					sqLiteDatabase.insert("loginData",null,cv);
					//sqLiteDatabase.execSQL("insert into loginData(userName, userPassword) values(?,?)",new Object[]{userName, userPassword});
					sqLiteDatabase.close();
					getLogindata();
					autoAdapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, userNameStr);
					autoAdapter.notifyDataSetChanged();
					autotv_userName.setAdapter(autoAdapter);
				}else{
					AbToastUtil.showToast(context, "用户名或密码不能为空！");
				}
				
				break;
			case R.id.tv_registered:
				intent.setClass(context, RegisteredActivity.class);
				startActivity(intent);
				break;
			default:
				break;
			}
		}
		
	};
	
}
