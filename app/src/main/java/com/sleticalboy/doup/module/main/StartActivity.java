package com.sleticalboy.doup.module.main;

import android.Manifest;
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
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.weather.WeatherActivity;
import com.sleticalboy.doup.module.baidumap.MapListActivity;
import com.sleticalboy.doup.module.openeye.fragment.OpeneyeFragment;
import com.sleticalboy.doup.module.girl.GirlFragment;
import com.sleticalboy.doup.module.home.NewsFragment;
import com.sleticalboy.doup.jpush.activity.IndexActivity;
import com.sleticalboy.doup.jpush.SettingsActivity;
import com.sleticalboy.util.ToastUtils;
import com.tbruyelle.rxpermissions2.RxPermissions;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";

    @BindView(R.id.fl_container)
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

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        checkAndRequestPermissions();

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_start);
        ButterKnife.bind(this);

        initFragments();

        initView();
    }

    private void checkAndRequestPermissions() {
        RxPermissions rxPermissions = new RxPermissions(this);
        if (!rxPermissions.isGranted(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                || !rxPermissions.isGranted(Manifest.permission.CAMERA)
                || !rxPermissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.CAMERA,
                    Manifest.permission.READ_PHONE_STATE)
                    .subscribe(granted -> {
                        if (!granted) {
                            ToastUtils.showToast(this, "没有授予相关权限");
                        }
                    });
        }
    }

    /**
     * 初始化控件
     */
    private void initView() {
        initActionBar();
        initSlideNavigation();
        initBottomNavigation();
    }

    private void initBottomNavigation() {
        bottomNav.setOnNavigationItemSelectedListener(item -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (item.getItemId()) {
                case R.id.navigation_index:
                    handleFragment(transaction, mFragments.get(0));
                    break;
                case R.id.navigation_meizi:
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.navigation_eye:
                    handleFragment(transaction, mFragments.get(2));
                    break;
                case R.id.navigation_message:
                    break;
            }
            transaction.commit();
            return true;
        });
    }

    // 初始化侧滑菜单
    private void initSlideNavigation() {
        navMenu.setCheckedItem(R.id.nav_call);
        navMenu.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_call:
                    SettingsActivity.actionStart(this);
                    break;
                case R.id.nav_contacts:
                    // TODO: 12/30/17 联系人列表 Activity
                    break;
                case R.id.nav_location:
                    MapListActivity.actionStart(this);
                    break;
                case R.id.nav_weather:
                    WeatherActivity.actionStart(this);
                    break;
                case R.id.nav_tasks:
                    // TODO: 12/30/17 todo list Activity
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
    }

    private void handleFragment(FragmentTransaction transaction, Fragment target) {
        if (target != null) {
            for (Fragment fragment : mFragments) {
                if (fragment != target && fragment.isAdded())
                    transaction.hide(fragment);
            }
            if (target.isAdded())
                transaction.show(target);
            else
                transaction.add(R.id.fl_container, target);
        }
    }

    // 初始化Fragment
    private void initFragments() {
        if (mFragments == null)
            mFragments = new ArrayList<>();

        mFragments.add(new NewsFragment());
        mFragments.add(new GirlFragment());
        mFragments.add(new OpeneyeFragment());

        Log.d(TAG, mFragments.toString());

        // 设置默认显示的 Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mFragments.get(0))
                .commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
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

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, StartActivity.class));
    }

    @OnClick({R.id.btn_change_theme, R.id.btn_settings, R.id.btn_exit})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_change_theme:
                ToastUtils.showToast(this, "change theme");
                break;
            case R.id.btn_settings:
                // TODO: 1/8/18 测试极光推送
                IndexActivity.actionStart(this);
                break;
            case R.id.btn_exit:
                ToastUtils.showToast(this, "exit...");
//                ActivityController.finishAll();
//                android.os.Process.killProcess(android.os.Process.myPid());
                break;
        }
    }
}
