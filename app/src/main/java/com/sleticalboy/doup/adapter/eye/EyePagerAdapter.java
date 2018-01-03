package com.sleticalboy.doup.adapter.eye;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.fragment.eye.FindingsFragment;
import com.sleticalboy.doup.fragment.eye.PopularFragment;
import com.sleticalboy.doup.fragment.eye.RecommendFragment;
import com.sleticalboy.doup.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class EyePagerAdapter extends FragmentPagerAdapter {

    private int[] mTabTitleId = {R.string.recommend, R.string.findings, R.string.popular};
    private List<Fragment> mSubFragments = new ArrayList<>();

    public EyePagerAdapter(FragmentManager fm) {
        super(fm);
        mSubFragments.add(new RecommendFragment());
        mSubFragments.add(new FindingsFragment());
        mSubFragments.add(new PopularFragment());
    }

    @Override
    public Fragment getItem(int position) {
        return mSubFragments.get(position);
    }

    @Override
    public int getCount() {
        return mTabTitleId.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ResUtils.getString(mTabTitleId[position]);
    }
}
