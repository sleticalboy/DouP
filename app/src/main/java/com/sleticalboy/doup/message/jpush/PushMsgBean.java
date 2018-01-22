package com.sleticalboy.doup.message.jpush;

import android.os.Parcel;
import android.os.Parcelable;

import com.sleticalboy.base.BaseBean;

/**
 * Created by Android Studio.
 * Date: 1/11/18.
 *
 * @author sleticalboy
 */
public class PushMsgBean extends BaseBean implements Parcelable {

    public String title;
    public String content;
    public String time;

    public PushMsgBean() {
    }

    protected PushMsgBean(Parcel in) {
        title = in.readString();
        content = in.readString();
        time = in.readString();
    }

    public static final Creator<PushMsgBean> CREATOR = new Creator<PushMsgBean>() {
        @Override
        public PushMsgBean createFromParcel(Parcel in) {
            return new PushMsgBean(in);
        }

        @Override
        public PushMsgBean[] newArray(int size) {
            return new PushMsgBean[size];
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
