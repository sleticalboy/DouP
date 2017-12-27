package com.sleticalboy.doup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.sleticalboy.doup.activity.AboutMeActivity;
import com.sleticalboy.doup.fragment.eye.EyeFragment;
import com.sleticalboy.doup.fragment.meizi.MeiziFragment;
import com.sleticalboy.doup.fragment.news.NewsFragment;

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
    @BindView(R.id.toolbar)
    RelativeLayout toolbar;
    @BindView(R.id.btn_back)
    TextView btnBack;
    @BindView(R.id.tab_title)
    TextView tabTitle;
    @BindView(R.id.about_me)
    TextView aboutMe;

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
                    tabTitle.setText(R.string.news);
                    toolbar.setVisibility(View.VISIBLE);
                    handleFragment(transaction, mFragments.get(0));
                    break;
                case R.id.tab_meizi:
                    Log.d(TAG, "meizi");
                    tabTitle.setText(R.string.meizi);
                    toolbar.setVisibility(View.VISIBLE);
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.tab_eyes:
                    Log.d(TAG, "eye");
                    toolbar.setVisibility(View.GONE);
                    handleFragment(transaction, mFragments.get(2));
                default:
                    break;
            }
            transaction.commit();
        });
    }

    private void initHeader() {
        btnBack.setText(R.string.back);
        tabTitle.setText(R.string.news);
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
