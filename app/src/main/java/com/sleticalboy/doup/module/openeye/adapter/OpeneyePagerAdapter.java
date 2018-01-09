package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.fragment.FindingsFragment;
import com.sleticalboy.doup.module.openeye.fragment.PopularFragment;
import com.sleticalboy.doup.module.openeye.fragment.RecommendFragment;
import com.sleticalboy.util.ResUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class OpeneyePagerAdapter extends FragmentPagerAdapter {

    private int[] mTabTitleId = {R.string.recommend, R.string.findings, R.string.popular};
    private List<Fragment> mSubFragments = new ArrayList<>();
    private Context mContext;

    public OpeneyePagerAdapter(Context context, FragmentManager fm) {
        super(fm);
        mContext = context;
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
        return ResUtils.getString(mContext, mTabTitleId[position]);
    }
}
