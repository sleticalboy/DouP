package com.sleticalboy.doup.adapter.eye;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.eye.FindingBean;
import com.sleticalboy.doup.util.ImageLoader;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Android Studio.
 * Date: 12/28/17.
 *
 * @author sleticalboy
 */

public class FindingAdapter extends BaseAdapter {

    private Context mContext;
    private List<FindingBean> mData;

    public FindingAdapter(Context context) {
        mContext = context;
    }

    public void setData(List<FindingBean> data) {
        mData = data;
    }

    @Override
    public int getCount() {
        return mData == null ? 0 : mData.size();
    }

    @Override
    public FindingBean getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = View.inflate(mContext, R.layout.item_finding, null);
            holder = ViewHolder.getHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        FindingBean findingBean = mData.get(position);
        ImageLoader.load(mContext, holder.imgPhoto, findingBean.bgPicture);
        holder.tvTitle.setText(findingBean.name);

        return convertView;
    }

    static class ViewHolder {

        @BindView(R.id.img_photo)
        ImageView imgPhoto;
        @BindView(R.id.tv_title)
        TextView tvTitle;

        ViewHolder(View itemView) {
            ButterKnife.bind(this, itemView);
        }

        static ViewHolder getHolder(View itemView) {
            return new ViewHolder(itemView);
        }
    }
}
