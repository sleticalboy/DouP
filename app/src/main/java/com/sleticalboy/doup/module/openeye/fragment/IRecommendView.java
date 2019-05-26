package com.sleticalboy.doup.module.openeye.fragment;

import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.bean.openeye.VideoBean;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/18/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface IRecommendView extends IBaseView {

    void setLayoutManager();

    void setAdapter(BaseRecyclerAdapter adapter);

    void showVideoDetail(VideoBean videoBean);
}
