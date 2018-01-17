package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.sleticalboy.base.IBaseView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.activity.RankActivity;
import com.sleticalboy.util.ToastUtils;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class FindingFragment extends LazyFragment implements IBaseView,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "FindingFragment";

    @BindView(R.id.rv_finding)
    EasyRecyclerView rvFindings;

    private FindingPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        mPresenter = new FindingPresenter(getActivity(), this);
//        rvFindings.addItemDecoration(new DividerGridItemDecoration(getActivity(), 4));
//        rvFindings.addItemDecoration(new DividerDecoration());
//        rvFindings.addItemDecoration(new DividerItemDecoration(getActivity(), DividerItemDecoration.VERTICAL));
        mPresenter.initRecyclerView();
    }

    public void setLayoutManager() {
        rvFindings.setLayoutManager(new GridLayoutManager(getContext(), 2));
    }

    public void setAdapter(RecyclerArrayAdapter adapter) {
        adapter.setError(R.layout.layout_error)
                .setOnClickListener(v -> adapter.resumeMore());
        adapter.setOnItemClickListener(this);
        rvFindings.setAdapterWithProgress(adapter);
    }

    @Override
    protected int attachLayout() {
        return R.layout.frag_findings;
    }

    @Override
    public void onLoading() {
    }

    @Override
    public void onLoadingOver() {
        rvFindings.setRefreshing(false);
    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(getActivity(), "网络异常");
    }

    @Override
    protected void fetchData() {
        mPresenter.initData();
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.clickItem(position);
    }

    public void showFindingDetail(String name) {
        RankActivity.actionStart(getContext(), name);
    }
}
