package com.sleticalboy.doup;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.FrameLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.sleticalboy.doup.activity.AboutMeActivity;
import com.sleticalboy.doup.fragment.eye.EyeFragment;
import com.sleticalboy.doup.fragment.meizi.MeiziFragment;
import com.sleticalboy.doup.fragment.news.NewsFragment;
import com.sleticalboy.doup.fragment.weather.WeatherFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tab_news)
    RadioButton tabNews;
    @BindView(R.id.rg_navigator)
    RadioGroup rgNavigator;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.about_me)
    TextView aboutMe;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;
    @BindView(R.id.tab_meizi)
    RadioButton tabMeizi;
    @BindView(R.id.tab_eyes)
    RadioButton tabEyes;
    @BindView(R.id.tab_weather)
    RadioButton tabWeather;
    @BindView(R.id.nav_menu)
    NavigationView navMenu;
    @BindView(R.id.drawer)
    DrawerLayout drawer;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragments();

        initHeader();

        rgNavigator.setOnCheckedChangeListener((group, checkedId) -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (checkedId) {
                case R.id.tab_news:
                    Log.d(TAG, "news");
                    tabTitle.setText(R.string.index);
                    handleFragment(transaction, mFragments.get(0));
                    break;
                case R.id.tab_meizi:
                    Log.d(TAG, "meizi");
                    tabTitle.setText(R.string.meizi);
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.tab_eyes:
                    Log.d(TAG, "eye");
                    handleFragment(transaction, mFragments.get(2));
                case R.id.tab_weather:
                    Log.d(TAG, "weather");
                    handleFragment(transaction, mFragments.get(3));
                default:
                    break;
            }
            transaction.commit();
        });
    }

    private void initHeader() {
        btnBack.setText(R.string.back);
        tabTitle.setText(R.string.index);
        aboutMe.setText(R.string.about_me);
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
        mFragments.add(new WeatherFragment());

        Log.d(TAG, mFragments.toString());

        // 设置默认显示的 Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mFragments.get(0))
                .commit();
        tabNews.setChecked(true);
    }

    @OnClick(R.id.about_me)
    public void onViewClicked() {
        AboutMeActivity.startAction(this);
    }
}
