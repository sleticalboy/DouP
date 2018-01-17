package com.sleticalboy.doup.module.openeye.fragment;

import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.model.openeye.VideoBean;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

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

    void setAdapter(RecyclerArrayAdapter adapter);

    void showVideoDetail(VideoBean videoBean);
}
