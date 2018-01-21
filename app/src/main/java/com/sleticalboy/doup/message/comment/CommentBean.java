package com.sleticalboy.doup.message.comment;

import android.os.Parcel;
import android.os.Parcelable;

import com.sleticalboy.base.BaseBean;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class CommentBean extends BaseBean implements Parcelable {

    public CommentBean() {}

    protected CommentBean(Parcel in) {
    }

    public static final Creator<CommentBean> CREATOR = new Creator<CommentBean>() {
        @Override
        public CommentBean createFromParcel(Parcel in) {
            return new CommentBean(in);
        }

        @Override
        public CommentBean[] newArray(int size) {
            return new CommentBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }
}
