package com.sleticalboy.doup.message.jchat;

import android.view.View;

import androidx.annotation.NonNull;

import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/12/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class ChatFragment extends LazyFragment implements IBaseListView {

    @Override
    public void onLoad() {
    }

    @Override
    public void onLoadFinished() {
    }

    @Override
    public void onNetError() {
    }

    @Override
    public void setAdapter(@NonNull BaseRecyclerAdapter adapter) {
    }

    @Override
    public void setLayoutManager() {
    }

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    @Override
    protected void fetchData() {
    }
}
