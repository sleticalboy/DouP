package com.sleticalboy.doup.message.jpush;

import android.os.Parcelable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

import butterknife.BindView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/12/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class PushFragment extends LazyFragment implements PushContract.IPushView,
        BaseRecyclerAdapter.OnItemClickListener {

    @BindView(R.id.rv_push)
    EasyRecyclerView rvPush;

    private PushContract.IPushPresenter mPresenter;

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
        rvPush.setLayoutManager(new LinearLayoutManager(getActivity()));
    }

    @Override
    protected void initView(View rootView) {
        mPresenter = onCreatePresenter();
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_push;
    }

    @Override
    protected void fetchData() {
        mPresenter.initData();
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.clickItem(position);
    }

    public PushContract.IPushPresenter onCreatePresenter() {
        return new PushPresenter(getActivity(), this);
    }

    @Override
    public void showPushDetail(Parcelable data) {
        PushDetailActivity.actionStart(getActivity(), data);
    }
}
