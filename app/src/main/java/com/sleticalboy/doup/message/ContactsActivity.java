package com.sleticalboy.doup.message;

import android.content.Context;
import android.content.Intent;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class ContactsActivity extends BaseActivity implements IBaseListView {

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, ContactsActivity.class));
    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingOver() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return null;
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

    }
}
