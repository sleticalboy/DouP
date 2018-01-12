package com.sleticalboy.doup.message;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseFragment;
import com.sleticalboy.doup.base.IBaseView;
import com.sleticalboy.doup.module.openeye.adapter.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class MessageFragment extends BaseFragment implements IBaseView {

    public static final String TAG = "MessageFragment";

    private final String[] mTabTitles = {"系统通知", "聊天消息"};

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    @Override
    protected void initView(View rootView) {

        tabLayout.addTab(tabLayout.newTab(), 0);
        tabLayout.addTab(tabLayout.newTab(), 1);

        BasePagerAdapter adapter = new BasePagerAdapter(getActivity(), getChildFragmentManager());

        adapter.setTabTitles(mTabTitles);

        List<Fragment> subFragments = new ArrayList<>();
        subFragments.add(new PushFragment());
        subFragments.add(new ChatFragment());
        adapter.setSubFragments(subFragments);

        viewPager.setAdapter(adapter);

        tabLayout.setupWithViewPager(viewPager, true);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.d(TAG, "tab.getPosition():" + tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    @Override
    protected void initToolBar(Toolbar toolbar, boolean homeAsUpEnabled, String title) {
        super.initToolBar(toolbar, true, mTabTitles[1]);
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_message;
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadingEnd() {
    }

    @Override
    public void onNetError() {

    }
}
