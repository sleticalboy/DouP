package com.sleticalboy.doup.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.eye.RankAdapter;
import com.sleticalboy.doup.mvp.model.EyesModel;
import com.sleticalboy.doup.mvp.model.bean.eye.PopularBean;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */

public class FindingDetailActivity extends AppCompatActivity {

    private static final String TAG = "FindingDetailActivity";

    public static final String NAME = "name";

    @BindView(R.id.rv_rank)
    RecyclerView rvRank;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;

    private int mStart = 10;
    private List<PopularBean.ItemListBean.DataBean> mData = new ArrayList<>();
    private String mName;
    private String mDate;
    private RankAdapter mAdapter;
    private LinearLayoutManager mLayoutManager;
    private int mLastVisibleItemIndex;

    private EyesModel mEyesModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finding_detail);
        ButterKnife.bind(this);

        mEyesModel = new EyesModel(this);

        initView();

        initData();
    }

    private void initView() {
        mLayoutManager = new LinearLayoutManager(this);
        rvRank.setLayoutManager(mLayoutManager);

        mAdapter = new RankAdapter(this);
        rvRank.setAdapter(mAdapter);

        srl.setOnRefreshListener(() -> {
            if (srl.isRefreshing()) {
                srl.setRefreshing(false);
                // 更新数据
                loadMore();
            } else {
                srl.setRefreshing(true);
            }
        });

        rvRank.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
                if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                    if (mLayoutManager.getItemCount() == 1) return;
                    if (mLayoutManager.getItemCount() + 1 == mLastVisibleItemIndex) {
                        loadMore();
                    }
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                mLastVisibleItemIndex = mLayoutManager.findLastVisibleItemPosition();
            }
        });
    }

    private void loadMore() {
        //
    }

    private void initData() {
        Intent intent = getIntent();
        if (intent != null) {
            mName = intent.getStringExtra(NAME);
        }

        mEyesModel.getFindingsDetail(mName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext(this::resolveDate)
                .subscribe(this::flatMapData);
    }

    private void resolveDate(PopularBean popularBean) {
        String regex = "[^0-9]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(popularBean.nextPageUrl);
        mDate = matcher.replaceAll("")
                .subSequence(1, matcher.replaceAll("").length() - 1)
                .toString();
    }

    private void flatMapData(PopularBean popularBean) {
        Observable.fromIterable(popularBean.itemList)
                .filter(itemListBean -> itemListBean.data.category != null
                        && itemListBean.data.cover != null)
                .forEach(itemListBean -> {
                    Log.d(TAG, itemListBean.data.toString());
                    mData.add(itemListBean.data);

                });
        mAdapter.setDataList(mData);
        mAdapter.notifyDataSetChanged();
    }

    public static void actionStart(Context context, String name) {
        Intent intent = new Intent(context, FindingDetailActivity.class);
        intent.putExtra(NAME, name);
        context.startActivity(intent);
    }
}
