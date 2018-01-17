package com.sleticalboy.doup.message;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;
import com.sleticalboy.widget.recyclerview.swipe.CircleImageView;

/**
 * Created by Android Studio.
 * Date: 1/11/18.
 *
 * @author sleticalboy
 */
public class MessageAdapter extends RecyclerArrayAdapter<MessageBean> {

    public MessageAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(parent);
    }

    static class ViewHolder extends BaseViewHolder<MessageBean> {

        CircleImageView imgHeader;
        TextView msgTitle;
        TextView msgContent;
        TextView msgTime;

        public ViewHolder(ViewGroup itemView) {
            super(itemView, R.layout.item_message);
            imgHeader = dollar(R.id.img_header);
            msgTitle = dollar(R.id.msg_title);
            msgContent = dollar(R.id.msg_content);
            msgTime = dollar(R.id.msg_time);
        }

        @Override
        public void setData(MessageBean data) {
            if (data == null) {
                return;
            }
            ImageLoader.load(getContext(), imgHeader, R.mipmap.ic_launcher);
            msgTitle.setText(data.title);
            msgContent.setText(data.content);
            msgTime.setText(data.time);
        }
    }
}
