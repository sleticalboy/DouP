package com.sleticalboy.doup.message;

import android.util.Log;
import android.view.View;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.sleticalboy.annotation.BindView;
import com.sleticalboy.base.BaseFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.message.comment.CommentFragment;
import com.sleticalboy.doup.message.contact.ContactListFragment;
import com.sleticalboy.doup.message.jchat.ChatFragment;
import com.sleticalboy.doup.message.jpush.PushFragment;
import com.sleticalboy.doup.module.openeye.adapter.BasePagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Android Studio.
 * Date: 1/9/18.
 *
 * @author sleticalboy
 */
public class MessageFragment extends BaseFragment {

    public static final String TAG = "MessageFragment";

    private final String[] mTabTitles = {"通知", "聊天", "联系人", "朋友圈"};

    @BindView(R.id.tab_layout)
    private TabLayout tabLayout;
    @BindView(R.id.viewPager)
    private ViewPager viewPager;

    @Override
    protected void initView(View rootView) {

        tabLayout.addTab(tabLayout.newTab(), 0);
        tabLayout.addTab(tabLayout.newTab(), 1);
        tabLayout.addTab(tabLayout.newTab(), 2);
        tabLayout.addTab(tabLayout.newTab(), 3);

        BasePagerAdapter adapter = new BasePagerAdapter(getActivity(), getChildFragmentManager());

        adapter.setTabTitles(mTabTitles);

        List<Fragment> subFragments = new ArrayList<>();
        subFragments.add(new PushFragment());
        subFragments.add(new ChatFragment());
        subFragments.add(new ContactListFragment());
        subFragments.add(new CommentFragment());
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
        return R.layout.jchat_frag_main;
    }
}
