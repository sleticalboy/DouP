package com.sleticalboy.doup.fragment.eye;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class EyeFragment extends Fragment {

    //    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    //    @BindView(R.id.view_pager)
    ViewPager viewPager;

    private EyePagerAdapter mAdapter;
    private static List<Fragment> sSubFragments = new ArrayList<>();

    static {
        sSubFragments.add(new RecommendFragment());
        sSubFragments.add(new FindingsFragment());
        sSubFragments.add(new PopularFragment());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.frag_eyes, container, false);
//        ButterKnife.bind(rootView);
        initView(rootView);

        return rootView;
    }

    private void initView(View rootView) {
        viewPager = rootView.findViewById(R.id.view_pager);
        tabLayout = rootView.findViewById(R.id.tab_layout);

        mAdapter = new EyePagerAdapter(getChildFragmentManager());
        viewPager.setAdapter(mAdapter);

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

    static class EyePagerAdapter extends FragmentPagerAdapter {

        private int[] mTabTitleId = {R.string.recommend, R.string.findings, R.string.popular};

        public EyePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return sSubFragments.get(position);
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return ResUtils.getString(mTabTitleId[position]);
        }
    }
}
