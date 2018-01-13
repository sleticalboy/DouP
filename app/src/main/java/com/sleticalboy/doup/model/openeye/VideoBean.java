package com.sleticalboy.doup.model.openeye;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */
public class VideoBean implements Parcelable {

    public String feed;
    public String title;
    public String desc;
    public int duration;
    public String playUrl;
    public String category;
    public String blurred;
    public int collectCount;
    public int shareCount;
    public int replyCount;
    public long time;

    public VideoBean() {
    }

    protected VideoBean(Parcel in) {
        feed = in.readString();
        title = in.readString();
        desc = in.readString();
        duration = in.readInt();
        playUrl = in.readString();
        category = in.readString();
        blurred = in.readString();
        collectCount = in.readInt();
        shareCount = in.readInt();
        replyCount = in.readInt();
        time = in.readLong();
    }

    public static final Creator<VideoBean> CREATOR = new Creator<VideoBean>() {
        @Override
        public VideoBean createFromParcel(Parcel in) {
            return new VideoBean(in);
        }

        @Override
        public VideoBean[] newArray(int size) {
            return new VideoBean[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(feed);
        dest.writeString(title);
        dest.writeString(desc);
        dest.writeInt(duration);
        dest.writeString(playUrl);
        dest.writeString(category);
        dest.writeString(blurred);
        dest.writeInt(collectCount);
        dest.writeInt(shareCount);
        dest.writeInt(replyCount);
        dest.writeLong(time);
    }

    @Override
    public String toString() {
        return "VideoBean{" +
                "feed='" + feed + '\'' +
                ", title='" + title + '\'' +
                ", desc='" + desc + '\'' +
                ", duration=" + duration +
                ", playUrl='" + playUrl + '\'' +
                ", category='" + category + '\'' +
                ", blurred='" + blurred + '\'' +
                ", collectCount=" + collectCount +
                ", shareCount=" + shareCount +
                ", replyCount=" + replyCount +
                ", time=" + time +
                '}';
    }
}
