package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sleticalboy.util.ResUtils;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class BasePagerAdapter extends FragmentPagerAdapter {

    private int[] mTabTitleIds;
    private String[] mTabTitles = {"Tab 1", "Tab 2", "Tab 3"};
    private List<Fragment> mSubFragments;
    private Context mContext;

    public BasePagerAdapter(@NonNull Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
    }

    public void setSubFragments(List<Fragment> subFragments) {
        mSubFragments = subFragments;
    }

    public void setTabTitleIds(int... tabTitleIds) {
        mTabTitleIds = tabTitleIds;
    }

    public void setTabTitles(String... tabTitles) {
        mTabTitles = tabTitles;
    }

    @Override
    public Fragment getItem(int position) {
        return mSubFragments == null ? null : mSubFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabTitleIds == null ? mTabTitles.length : mTabTitleIds.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return getTabTitle(position);
    }

    @NonNull
    private String getTabTitle(int index) {
        return mTabTitleIds == null ? mTabTitles[index] : ResUtils.INSTANCE.getString(mContext, mTabTitleIds[index]);
    }
}
