package com.sleticalboy.doup.module.girl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.girl.GirlBean;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class GirlListAdapter extends RecyclerArrayAdapter<GirlBean.ResultsBean> {

    public static final String TAG = "GirlListAdapter";

    public GirlListAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<GirlBean.ResultsBean> {

        ImageView imgGirl;
        CardView cardView;

        public ViewHolder(ViewGroup item) {
            super(item, R.layout.girl_recycle_item_girl);
            imgGirl = obtainView(R.id.img_girl);
            cardView = obtainView(R.id.card_view);
        }

        @Override
        public void setData(GirlBean.ResultsBean data) {
            ImageLoader.load(getContext(), imgGirl, data.url);
            String desc = data._id;
            String url = data.url;
            cardView.setOnClickListener(v -> {
                // 点击显示妹子大图，并可以保存图片到本地
                Intent intent = GirlActivity.newIntent(getContext(), url, desc);
                ActivityOptionsCompat optionsCompat =
                        ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) getContext(),
                                imgGirl, GirlActivity.TRANSIT_PIC);
                // android 5.0+
                try {
                    ActivityCompat.startActivity(getContext(), intent, optionsCompat.toBundle());
                } catch (Exception e) {
                    Log.wtf(TAG, e);
                    getContext().startActivity(intent);
                }
            });
        }
    }
}
