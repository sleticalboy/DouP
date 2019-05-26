package com.sleticalboy.doup.message.contact;

import android.os.Parcelable;
import android.view.View;

import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class ContactListFragment extends LazyFragment implements ContactContract.IContactView {

    @Override
    protected void initView(View rootView) {
    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

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
    public void setAdapter(BaseRecyclerAdapter adapter) {
    }

    @Override
    public void setLayoutManager() {
    }

    @Override
    public void showContactDetail(Parcelable data) {
        ContactDetailActivity.actionStart(getActivity(), data);
    }

    @Override
    protected void fetchData() {
    }
}
