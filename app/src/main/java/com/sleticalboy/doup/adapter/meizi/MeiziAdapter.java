package com.sleticalboy.doup.adapter.meizi;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.meizi.BeautyBean;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class MeiziAdapter extends RecyclerView.Adapter<MeiziAdapter.ViewHolder> {

    private Context mContext;
    private BeautyBean mData;

    public MeiziAdapter(Context context) {
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View rootView = View.inflate(mContext, R.layout.item_meizi, null);
        return new ViewHolder(rootView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        BeautyBean.ResultsBean resultsBean = mData.results.get(position);
        Log.d("MeiziAdapter", "resultsBean:" + resultsBean.url);
        Glide.with(mContext)
                .load(resultsBean.url)
                .placeholder(R.mipmap.ic_launcher)
                .centerCrop()
                .into(holder.imgMeizi);
        holder.cardView.setOnClickListener(v -> {
            // 点击显示妹子大图，并可以保存图片到本地
            // TODO: 12/26/17 待完善
            Toast.makeText(mContext, "显示大图并且保存本地-待完善", Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.results.size();
    }

    public void setData(BeautyBean data) {
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
