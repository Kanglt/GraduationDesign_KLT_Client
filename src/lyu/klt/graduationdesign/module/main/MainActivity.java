package lyu.klt.graduationdesign.module.main;

import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;
import lyu.klt.frame.ab.util.AbToastUtil;
import lyu.klt.graduationdesign.base.BaseActivity;
import lyu.klt.graduationdesign.module.ResideMenu.ResideMenu;
import lyu.klt.graduationdesign.module.ResideMenu.ResideMenuItem;

import com.lyu.graduationdesign_klt.R;


public class MainActivity extends BaseActivity implements View.OnClickListener{
    private static final String TAG = MainActivity.class
			.getSimpleName();
	private Activity context;
	
	
	private ResideMenu resideMenu;
    private ResideMenuItem itemHome;
    private ResideMenuItem item2;
    private ResideMenuItem item3;
    private ResideMenuItem item4;
    private ResideMenuItem item5;
    private ResideMenuItem itemSettings;
    
    private Button title_bar_left_menu;
    private Button title_bar_right_menu;
    
    Window window; 

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /*
         * 隐藏虚拟键盘
         */
//        window = getWindow();  
//        WindowManager.LayoutParams params = window.getAttributes();  
//        params.systemUiVisibility = View.SYSTEM_UI_FLAG_LOW_PROFILE;  
//        window.setAttributes(params); 
        setAbContentView(R.layout.main);
        init();
        if( savedInstanceState == null )
            changeFragment(new HomeFragment());
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
		context = this;
	}

	@Override
	public void initData() {
		// TODO Auto-generated method stub
		super.initData();
	}

	@Override
	public void initView() {
		// TODO Auto-generated method stub
		super.initView();
		
		title_bar_left_menu=(Button) findViewById(R.id.title_bar_left_menu);
		title_bar_right_menu=(Button) findViewById(R.id.title_bar_right_menu);
		
		// attach to current activity;
        resideMenu = new ResideMenu(this);
        resideMenu.setUse3D(true);
        resideMenu.setBackground(R.drawable.menu_background);
        resideMenu.attachToActivity(this);
        resideMenu.setMenuListener(menuListener);
        //valid scale factor is between 0.0f and 1.0f. leftmenu'width is 150dip. 
        resideMenu.setScaleValue(0.6f);

        // create menu items;
        itemHome     = new ResideMenuItem(this, R.drawable.icon_home,     "Home");
        item2  = new ResideMenuItem(this, R.drawable.icon_profile,  "item2");
        item3 = new ResideMenuItem(this, R.drawable.icon_profile, "item3");
        item4 = new ResideMenuItem(this, R.drawable.icon_profile, "item4");
        item5 = new ResideMenuItem(this, R.drawable.icon_profile, "item5");
        itemSettings = new ResideMenuItem(this, R.drawable.icon_settings, "Settings");

        
	}

	@Override
	public void initViewData() {
		// TODO Auto-generated method stub
		super.initViewData();
		
		resideMenu.addMenuItem(itemHome, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item2, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item3, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item4, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(item5, ResideMenu.DIRECTION_LEFT);
        resideMenu.addMenuItem(itemSettings, ResideMenu.DIRECTION_RIGHT);
	}

	@Override
	public void initEvent() {
		// TODO Auto-generated method stub
		super.initEvent();
		 itemHome.setOnClickListener(this);
		 item2.setOnClickListener(this);
		 item3.setOnClickListener(this);
		 item4.setOnClickListener(this);
		 item5.setOnClickListener(this);
	     itemSettings.setOnClickListener(this);
	     title_bar_left_menu.setOnClickListener(this);
	     title_bar_right_menu.setOnClickListener(this);
	}

	@Override
	public void startGame() {
		// TODO Auto-generated method stub
		super.startGame();
	}


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        return resideMenu.dispatchTouchEvent(ev);
    }

    @Override
    public void onClick(View view) {

        if (view == itemHome){
            changeFragment(new HomeFragment());
            resideMenu.closeMenu();
        }else if (view == item2){
            changeFragment(new FargmentActivity());
            resideMenu.closeMenu();
        }else if (view == item3){
            changeFragment(new FargmentActivity2());
            resideMenu.closeMenu();
        }else if (view == item4){
            changeFragment(new FargmentActivity3());
            resideMenu.closeMenu();
        }else if (view == item5){
            changeFragment(new FargmentActivity4());
            resideMenu.closeMenu();
        }else if (view == itemSettings){
            changeFragment(new SettingsFragment());
            resideMenu.closeMenu();
        }else if(view==title_bar_left_menu){
        	 resideMenu.openMenu(ResideMenu.DIRECTION_LEFT);
        }else if(view==title_bar_right_menu){
        	 resideMenu.openMenu(ResideMenu.DIRECTION_RIGHT);
        }

        
    }

    private ResideMenu.OnMenuListener menuListener = new ResideMenu.OnMenuListener() {
        @Override
        public void openMenu() {
//            Toast.makeText(context, "Menu is opened!", Toast.LENGTH_SHORT).show();
        	//AbToastUtil.showToast(context, "Menu is opened!");
        }

        @Override
        public void closeMenu() {
//            Toast.makeText(context, "Menu is closed!", Toast.LENGTH_SHORT).show();
        	//AbToastUtil.showToast(context, "Menu is closed!");
        }
    };

    private void changeFragment(Fragment targetFragment){
        resideMenu.clearIgnoredViewList();
        getFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment, targetFragment, "fragment")
                .setTransitionStyle(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                .commit();
    }

    // What good method is to access resideMenu锛�
    public ResideMenu getResideMenu(){
        return resideMenu;
    }
}
