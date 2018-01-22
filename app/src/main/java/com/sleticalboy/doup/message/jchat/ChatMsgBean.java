package com.sleticalboy.doup.message.jchat;

import android.os.Parcel;
import android.os.Parcelable;

import com.sleticalboy.base.BaseBean;

/**
 * Created by Android Studio.
 * Date: 1/11/18.
 *
 * @author sleticalboy
 */
public class ChatMsgBean extends BaseBean implements Parcelable {

    public String title;
    public String content;
    public String time;

    public ChatMsgBean() {
    }

    protected ChatMsgBean(Parcel in) {
        title = in.readString();
        content = in.readString();
        time = in.readString();
    }

    public static final Creator<ChatMsgBean> CREATOR = new Creator<ChatMsgBean>() {
        @Override
        public ChatMsgBean createFromParcel(Parcel in) {
            return new ChatMsgBean(in);
        }

        @Override
        public ChatMsgBean[] newArray(int size) {
            return new ChatMsgBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(time);
    }
}
