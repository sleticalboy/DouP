package com.sleticalboy.doup.module.main;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.jpush.activity.IndexActivity;
import com.sleticalboy.doup.message.MessageFragment;
import com.sleticalboy.doup.module.girl.GirlFragment;
import com.sleticalboy.doup.module.home.NewsFragment;
import com.sleticalboy.doup.module.openeye.fragment.OpeneyeFragment;
import com.sleticalboy.doup.module.weather.WeatherActivity;
import com.sleticalboy.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * 应用的启动主页面
 */
public class StartActivity extends BaseActivity {
    
    private static final String TAG = "StartActivity";
    
    @BindView(R.id.fl_common_container)
    FrameLayout flContainer;
    
    @BindView(R.id.nav_slide_menu)
    NavigationView navMenu;
    @BindView(R.id.drawer)
    DrawerLayout drawer;
    
    @BindView(R.id.btn_change_theme)
    TextView btnChangeTheme;
    @BindView(R.id.btn_settings)
    TextView btnSettings;
    @BindView(R.id.btn_exit)
    TextView btnExit;
    
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.bottom_nav)
    BottomNavigationView bottomNav;
    
    private final SparseArray<Fragment> mFragments = new SparseArray<>();
    private Fragment mCurFragment;
    
    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, StartActivity.class));
    }
    
    @Override
    protected void beforeViews() {
        checkAndRequestPermissions();
        
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }
    }
    
    @Override
    protected int attachLayout() {
        return R.layout.main_activity_start;
    }
    
    @Override
    protected void initView(final Bundle savedInstanceState) {
        // 设置默认显示的 Fragment
        setFragment(R.id.navigation_index);
        
        initActionBar();
        initSlideNavigation();
        initBottomNavigation();
    }
    
    private void initBottomNavigation() {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            setFragment(item.getItemId());
            return true;
        });
    }
    
    // 初始化侧滑菜单
    private void initSlideNavigation() {
        navMenu.setCheckedItem(R.id.nav_call);
        navMenu.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_call:
//                    SettingsActivity.actionStart(this);
                    break;
                case R.id.nav_contacts:
                    //
                    break;
                case R.id.nav_location:
//                    MainMapActivity.actionStart(this);
                    break;
                case R.id.nav_weather:
                    WeatherActivity.actionStart(this);
                    break;
                default:
                    break;
            }
            drawer.closeDrawers();
            return true;
        });
        
    }
    
    // 初始化 ActionBar
    private void initActionBar() {
        setSupportActionBar(toolbar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.app_name);
            supportActionBar.setHomeAsUpIndicator(R.mipmap.ic_menu);
        }
        
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
    }
    
    private void setFragment(final int key) {
        final FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        
        Fragment fragment = mFragments.get(key);
        if (fragment == null) {
            switch (key) {
                default:
                case R.id.navigation_index:
                    fragment = new NewsFragment();
                    break;
                case R.id.navigation_meizi:
                    fragment = new GirlFragment();
                    break;
                case R.id.navigation_eye:
                    fragment = new OpeneyeFragment();
                    break;
                case R.id.navigation_message:
                    fragment = new MessageFragment();
                    break;
            }
            transaction.add(R.id.fl_common_container, fragment);
            mFragments.put(key, fragment);
        }
        if (mCurFragment == fragment) {
            return;
        }
        if (mCurFragment != null) {
            transaction.hide(mCurFragment);
        }
        transaction.show(fragment);
        mCurFragment = fragment;
        transaction.commit();
        // ---------------------
        // 作者：鸿洋_
        // 来源：CSDN
        // 原文：https://blog.csdn.net/lmj623565791/article/details/37992017
        // 版权声明：本文为博主原创文章，转载请附上博文链接！
        // add 往Activity中添加一个Fragment
        // remove 从Activity中移除一个Fragment，如果被移除的Fragment没有添加到回退栈（回退栈后面会详细说），这个Fragment实例将会被销毁
        // hide 隐藏当前的Fragment，仅仅是设为不可见，并不会销毁
        // show 显示之前隐藏的Fragment
        // replace 使用另一个Fragment替换当前的，实际上就是remove()然后add()的合体~
        // addToBackStack 类似Android系统为Activity维护一个任务栈，我们也可以通过Activity维护一个回退栈来保存每次Fragment事务发生的变化。
        // 如果你将Fragment任务添加到回退栈，当用户点击后退按钮时，将看到上一次的保存的Fragment。一旦Fragment完全从后退栈中弹出，用户再次点击后退键，则退出当前Activity。
        // attach 重建view视图，附加到UI上并显示
        // detach 会将view从UI中移除,和remove()不同,此时fragment的状态依然由FragmentManager维护
        // 注意：Activity状态不一致：State loss这样的错误。主要是因为：commit方法一定要在Activity.onSaveInstance()之前调用。
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawer.openDrawer(Gravity.START);
                break;
//            case R.id.backup:
//                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
//                break;
//            case R.id.delete:
//                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
//                break;
            case R.id.settings:
//                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                AboutMeActivity.startAction(this);
                break;
            default:
                break;
        }
        return true;
    }
    
    private void checkAndRequestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        if (!rxPermissions.isGranted(android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !rxPermissions.isGranted(android.Manifest.permission.CAMERA)
                || !rxPermissions.isGranted(android.Manifest.permission.READ_PHONE_STATE)) {
            rxPermissions.request(android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    android.Manifest.permission.CAMERA,
                    android.Manifest.permission.READ_PHONE_STATE)
                    .subscribe(granted -> {
                        if (!granted) {
                            // Do nothing
                        }
                    });
        }
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }
    
    @OnClick({R.id.btn_change_theme, R.id.btn_settings, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            default:
                break;
            case R.id.btn_change_theme:
                ToastUtils.INSTANCE.showToast(this, "change theme");
                break;
            case R.id.btn_settings:
                IndexActivity.actionStart(this);
                break;
            case R.id.btn_exit:
                ToastUtils.INSTANCE.showToast(this, "exit...");
//                ActivityController.finishAll();
//                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
    }
}
