package com.sleticalboy.doup.fragment.news;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.adapter.NewsListAdapter;
import com.sleticalboy.doup.bean.news.NewsBean;
import com.sleticalboy.doup.http.ApiFactory;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsFragment extends Fragment {

    @BindView(R.id.rv_news)
    RecyclerView rvNews;
    @BindView(R.id.srl)
    SwipeRefreshLayout srl;
    @BindView(R.id.fab_top)
    FloatingActionButton fabTop;

    Unbinder unbinder;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = View.inflate(getContext(), R.layout.frag_news, null);
        unbinder = ButterKnife.bind(this, rootView);

        NewsListAdapter adapter = new NewsListAdapter(getContext());
        ApiFactory.getNewsApi().getLatestNews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<NewsBean>() {
                    @Override
                    public void onCompleted() {
                        Toast.makeText(getContext(), "加载完成", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        Toast.makeText(getContext(), "网络繁忙...", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onNext(NewsBean newsBean) {
                        // 将数据设置给 adapter
                        adapter.setData(newsBean);
                        adapter.notifyDataSetChanged();
                    }
                });
        rvNews.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rvNews.setAdapter(adapter);

        srl.setColorSchemeResources(R.color.refresh_progress_1, R.color.refresh_progress_2,
                R.color.refresh_progress_3);
        srl.setProgressViewOffset(true, 0, (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 24,
                getResources().getDisplayMetrics()));
        srl.setOnRefreshListener(() -> {
            adapter.notifyDataSetChanged();
            srl.setRefreshing(false);
        });

        fabTop.setOnClickListener(v -> {
            // 返回顶部
            Toast.makeText(getContext(), "返回顶部", Toast.LENGTH_SHORT).show();
            rvNews.scrollToPosition(0);
        });

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
