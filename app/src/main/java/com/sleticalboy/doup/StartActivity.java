package com.sleticalboy.doup;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.sleticalboy.doup.activity.WeatherActivity;
import com.sleticalboy.doup.baidumap.MapListActivity;
import com.sleticalboy.doup.fragment.eye.EyeFragment;
import com.sleticalboy.doup.fragment.meizi.MeiziFragment;
import com.sleticalboy.doup.fragment.news.NewsFragment;
import com.sleticalboy.doup.jpush.MainActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StartActivity extends AppCompatActivity {

    private static final String TAG = "StartActivity";

    @BindView(R.id.rg_navigator)
    RadioGroup rgNavigator;
    @BindView(R.id.tab_index)
    RadioButton tabNews;
    @BindView(R.id.tab_meizi)
    RadioButton tabMeizi;
    @BindView(R.id.tab_eyes)
    RadioButton tabEyes;

    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    @BindView(R.id.nav_menu)
    NavigationView navMenu;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getWindow().requestFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
        }

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragments();

        initView();
    }

    private void initView() {
//        initActionBar();
        initNavMenu();
        initNavigator();
    }

    private void initNavigator() {
        rgNavigator.setOnCheckedChangeListener((group, checkedId) -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (checkedId) {
                case R.id.tab_index:
                    Log.d(TAG, "news");
//                    getSupportActionBar().setTitle(R.string.index);
                    handleFragment(transaction, mFragments.get(0));
                    break;
                case R.id.tab_meizi:
                    Log.d(TAG, "meizi");
//                    getSupportActionBar().setTitle(R.string.meizi);
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.tab_eyes:
                    Log.d(TAG, "eye");
//                    getSupportActionBar().setTitle(R.string.open_eye);
                    handleFragment(transaction, mFragments.get(2));
                    break;
//                case R.weatherId.tab_weather:
//                    Log.d(TAG, "weather");
//                    getSupportActionBar().setTitle(R.string.weather);
//                    handleFragment(transaction, mFragments.get(3));
//                    break;
                default:
                    break;
            }
            transaction.commit();
        });
    }

    // 初始化侧滑菜单
    private void initNavMenu() {
        navMenu.setCheckedItem(R.id.nav_call);
        navMenu.setNavigationItemSelectedListener(item -> {
            switch (item.getItemId()) {
                case R.id.nav_call:
                    // TODO: 1/8/18 测试极光推送
                    MainActivity.actionStart(this);
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
//        setSupportActionBar(toolBar);
        ActionBar supportActionBar = getSupportActionBar();
        if (supportActionBar != null) {
            supportActionBar.setDisplayHomeAsUpEnabled(true);
            supportActionBar.setTitle(R.string.index);
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
        mFragments.add(new MeiziFragment());
        mFragments.add(new EyeFragment());

        Log.d(TAG, mFragments.toString());

        // 设置默认显示的 Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mFragments.get(0))
                .commit();
        tabNews.setChecked(true);
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
            case R.id.backup:
                Toast.makeText(this, "You clicked Backup", Toast.LENGTH_SHORT).show();
                break;
            case R.id.delete:
                Toast.makeText(this, "You clicked Delete", Toast.LENGTH_SHORT).show();
                break;
            case R.id.settings:
                Toast.makeText(this, "You clicked Settings", Toast.LENGTH_SHORT).show();
                break;
            default:
                break;
        }
        return true;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, StartActivity.class));
    }
}
