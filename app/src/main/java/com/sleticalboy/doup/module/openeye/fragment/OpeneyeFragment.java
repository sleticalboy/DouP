package com.sleticalboy.doup.module.openeye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.adapter.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class OpeneyeFragment extends Fragment {

    private int[] mTabTitleIds = {R.string.recommend, R.string.findings, R.string.popular};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.openeye_frag_main, container, false);
        ButterKnife.bind(rootView);
        initView(rootView);
        return rootView;
    }

    private void initView(View rootView) {
        ViewPager viewPager = rootView.findViewById(R.id.viewPager);
        TabLayout tabLayout = rootView.findViewById(R.id.tab_layout);

        List<Fragment> subFragments = new ArrayList<>();
        subFragments.add(new RecommendFragment());
        subFragments.add(new FindingFragment());
        subFragments.add(new HotFragment());

        BasePagerAdapter adapter = new BasePagerAdapter(getActivity(), getChildFragmentManager());
        adapter.setTabTitleIds(mTabTitleIds);
        adapter.setSubFragments(subFragments);

        viewPager.setAdapter(adapter);

        tabLayout.addTab(tabLayout.newTab(), 0, true);
        tabLayout.addTab(tabLayout.newTab(), 1);
        tabLayout.addTab(tabLayout.newTab(), 2);

        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // 当选 tabItem 被中时
                viewPager.setCurrentItem(tab.getPosition(), true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
            }
        });
    }
}
