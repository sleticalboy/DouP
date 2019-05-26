package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.openeye.FindingBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseRecyclerAdapter;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */
public class FindingAdapterBase extends BaseRecyclerAdapter<FindingBean> {

    public FindingAdapterBase(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<FindingBean> {

        ImageView imgPhoto;
        TextView tvTitle;

        ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.openeye_recycle_item_finding);
            imgPhoto = obtainView(R.id.img_photo);
            tvTitle = obtainView(R.id.tv_title);
        }

        @Override
        public void bindData(FindingBean data) {
            ImageLoader.load(getContext(), imgPhoto, data.bgPicture);
            tvTitle.setText(data.name);
        }
    }
}
