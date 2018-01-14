package com.sleticalboy.doup.module.girl;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.girl.GirlBean;
import com.sleticalboy.util.ImageLoader;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class GirlAdapter extends RecyclerView.Adapter<GirlAdapter.ViewHolder> {

    public static final String TAG = "GirlAdapter";

    private Context mContext;
    private GirlBean mData;

    public GirlAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(View.inflate(mContext, R.layout.item_meizi, null));
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        GirlBean.ResultsBean resultsBean = mData.results.get(position);

        ImageLoader.load(mContext, holder.imgMeizi, resultsBean.url);

        String desc = resultsBean._id;
        String url = resultsBean.url;
        holder.cardView.setOnClickListener(v -> {
            // 点击显示妹子大图，并可以保存图片到本地
//            GirlActivity.actionStart(mContext, url, desc);
            Intent intent = GirlActivity.newIntent(mContext, url, desc);
            ActivityOptionsCompat optionsCompat =
                    ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) mContext,
                            holder.imgMeizi, GirlActivity.TRANSIT_PIC);
            // android 5.0+
            try {
                ActivityCompat.startActivity(mContext, intent, optionsCompat.toBundle());
            } catch (Exception e) {
                e.printStackTrace();
//                GirlActivity.actionStart(mContext, intent);
                mContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.results.size();
    }

    public void setData(GirlBean data) {
        mData = data;
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.img_meizi)
        ImageView imgMeizi;
        @BindView(R.id.card_view)
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
