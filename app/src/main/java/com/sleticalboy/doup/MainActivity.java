package com.sleticalboy.doup;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.sleticalboy.doup.fragment.book.BookFragment;
import com.sleticalboy.doup.fragment.meizi.MeiziFragment;
import com.sleticalboy.doup.fragment.mine.MineFragment;
import com.sleticalboy.doup.fragment.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    @BindView(R.id.tab_news)
    RadioButton tabNews;
    @BindView(R.id.rg_navigator)
    RadioGroup rgNavigator;

    private List<Fragment> mFragments;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initFragments();

        rgNavigator.setOnCheckedChangeListener((group, checkedId) -> {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            switch (checkedId) {
                case R.id.tab_news:
                    Log.d(TAG, "news");
                    handleFragment(transaction, mFragments.get(0));
                    break;
                case R.id.tab_book:
                    Log.d(TAG, "book");
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.tab_meizi:
                    Log.d(TAG, "meizi");
                    handleFragment(transaction, mFragments.get(1));
                    break;
                case R.id.tab_mine:
                    Log.d(TAG, "mine");
                    handleFragment(transaction, mFragments.get(3));
                    break;
                default:
                    break;
            }
            transaction.commit();
        });
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
            mFragments = new ArrayList<>(4);

        mFragments.add(new NewsFragment());
        mFragments.add(new BookFragment());
        mFragments.add(new MeiziFragment());
        mFragments.add(new MineFragment());

        Log.d(TAG, mFragments.toString());

        // 设置默认显示的 Fragment
        getSupportFragmentManager()
                .beginTransaction()
                .add(R.id.fl_container, mFragments.get(0))
                .commit();
        tabNews.setChecked(true);
    }
}
