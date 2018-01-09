package com.sleticalboy.doup.module.openeye.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.openeye.adapter.FindingAdapter;
import com.sleticalboy.doup.model.OpeneyeModel;
import com.sleticalboy.doup.model.openeye.FindingBean;
import com.sleticalboy.doup.module.openeye.activity.FindingDetailActivity;
import com.sleticalboy.util.ToastUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class FindingsFragment extends Fragment {

    private static final String TAG = "FindingsFragment";

    @BindView(R.id.gv_finding)
    GridView gvFinding;

    private FindingAdapter mAdapter;
    private List<FindingBean> mData;

    private OpeneyeModel mOpeneyeModel;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_findings, null);
        ButterKnife.bind(this, rootView);

        mOpeneyeModel = new OpeneyeModel(getContext());

        initView();

        initData();

        return rootView;
    }

    private void initData() {
        mOpeneyeModel.getFindings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(findingBeans -> {
                    mData = findingBeans;
                    mAdapter.setData(mData);
                    mAdapter.notifyDataSetChanged();
                }, throwable -> {
                    throwable.printStackTrace();
                    ToastUtils.showToast(getContext(), "网络异常");
                });

    }

    private void initView() {
        mAdapter = new FindingAdapter(getContext());
        gvFinding.setAdapter(mAdapter);

        gvFinding.setOnItemClickListener((parent, view, position, id) -> actionStart(position));
    }

    private void actionStart(int position) {
        FindingDetailActivity.actionStart(getContext(), mData.get(position).name);
    }
}
