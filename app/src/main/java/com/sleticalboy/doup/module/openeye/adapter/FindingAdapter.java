package com.sleticalboy.doup.module.openeye.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.openeye.FindingBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */
public class FindingAdapter extends RecyclerArrayAdapter<FindingBean> {

    public FindingAdapter(Context context) {
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
            super(itemView, R.layout.item_finding);
            imgPhoto = dollar(R.id.img_photo);
            tvTitle = dollar(R.id.tv_title);
        }

        @Override
        public void setData(FindingBean data) {
            ImageLoader.load(getContext(), imgPhoto, data.bgPicture);
            tvTitle.setText(data.name);
        }
    }
}
