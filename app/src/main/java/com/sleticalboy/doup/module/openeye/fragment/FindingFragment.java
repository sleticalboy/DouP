package com.sleticalboy.doup.module.openeye.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;

import com.sleticalboy.base.IBaseListView;
import com.sleticalboy.base.LazyFragment;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.activity.FindingDetailActivity;
import com.sleticalboy.util.ToastUtils;
import com.sleticalboy.widget.myrecyclerview.EasyRecyclerView;
import com.sleticalboy.widget.myrecyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class FindingFragment extends LazyFragment implements IBaseListView,
        RecyclerArrayAdapter.OnItemClickListener {

    private static final String TAG = "FindingFragment";

    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.rv_finding)
    EasyRecyclerView rvFindings;

    private FindingPresenter mPresenter;

    @Override
    protected void initView(View rootView) {
        srl.setEnabled(false);
        mPresenter = new FindingPresenter(getActivity(), this);
        mPresenter.setLayoutManager();
        mPresenter.setAdapter();
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

    }

    @Override
    public void onNetError() {
        ToastUtils.showToast(getActivity(), "网络异常");
    }

    @Override
    public EasyRecyclerView getRecyclerView() {
        return rvFindings;
    }

    @Override
    public void onNoMore() {

    }

    @Override
    public void onShowMore() {

    }

    @Override
    protected void fetchData() {
        mPresenter.initData();
    }

    @Override
    public void onItemClick(int position) {
        mPresenter.onItemClick(position);
    }

    public void showFindingDetail(String name) {
        FindingDetailActivity.actionStart(getContext(), name);
    }
}
