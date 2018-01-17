package com.sleticalboy.doup.model.openeye;

import com.sleticalboy.base.IBaseBean;

import java.util.List;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/18/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class DataBean implements IBaseBean {
    public String dataType;
    public int id;
    public String title;
    public String slogan;
    public String description;
    public ProviderBean provider;
    public String category;
    public AuthorBean author;
    public CoverBean cover;
    public String playUrl;
    public Object thumbPlayUrl;
    public int duration;
    public long releaseTime;
    public String library;
    public ConsumptionBean consumption;
    public Object campaign;
    public Object waterMarks;
    public Object adTrack;
    public String type;
    public Object titlePgc;
    public Object descriptionPgc;
    public String remark;
    public int idx;
    public Object shareAdTrack;
    public Object favoriteAdTrack;
    public Object webAdTrack;
    public long date;
    public Object promotion;
    public Object label;
    public String descriptionEditor;
    public boolean collected;
    public boolean played;
    public Object lastViewTime;
    public Object playlists;
    public Object src;
    public List<PlayInfoBean> playInfo;
    public List<TagsBean> tags;
    public List<?> labelList;
    public List<?> subtitles;

    public static class ProviderBean {
        public String name;
        public String alias;
        public String icon;
    }

    public static class AuthorBean {
        public int id;
        public String icon;
        public String name;
        public String description;
        public String link;
        public long latestReleaseTime;
        public int videoNum;
        public Object adTrack;
        public FollowBean follow;
        public ShieldBean shield;
        public int approvedNotReadyVideoCount;
        public boolean ifPgc;

        public static class FollowBean {
            public String itemType;
            public int itemId;
            public boolean followed;
        }

        public static class ShieldBean {
            public String itemType;
            public int itemId;
            public boolean shielded;
        }
    }

    public static class CoverBean {
        public String feed;
        public String detail;
        public String blurred;
        public Object sharing;
        public String homepage;

        @Override
        public String toString() {
            return "CoverBean{" +
                    "feed='" + feed + '\'' +
                    ", detail='" + detail + '\'' +
                    ", blurred='" + blurred + '\'' +
                    ", sharing=" + sharing +
                    ", homepage='" + homepage + '\'' +
                    '}';
        }
    }

    public static class ConsumptionBean {
        public int collectionCount;
        public int shareCount;
        public int replyCount;
    }

    public static class PlayInfoBean {
        public int height;
        public int width;
        public String name;
        public String type;
        public String url;
        public List<UrlListBean> urlList;

        public static class UrlListBean {
            public String name;
            public String url;
            public int size;
        }
    }

    public static class TagsBean {
        public int id;
        public String name;
        public String actionUrl;
        public Object adTrack;
        public Object desc;
        public String bgPicture;
        public String headerImage;
        public String tagRecType;
    }

    @Override
    public String toString() {
        return "DataBean{" +
                "dataType='" + dataType + '\'' +
                ", id=" + id +
                ", title='" + title + '\'' +
                ", slogan='" + slogan + '\'' +
                ", description='" + description + '\'' +
                ", provider=" + provider +
                ", category='" + category + '\'' +
                ", author=" + author +
                ", cover=" + cover +
                ", playUrl='" + playUrl + '\'' +
                ", thumbPlayUrl=" + thumbPlayUrl +
                ", duration=" + duration +
                ", releaseTime=" + releaseTime +
                ", library='" + library + '\'' +
                ", consumption=" + consumption +
                ", campaign=" + campaign +
                ", waterMarks=" + waterMarks +
                ", adTrack=" + adTrack +
                ", type='" + type + '\'' +
                ", titlePgc=" + titlePgc +
                ", descriptionPgc=" + descriptionPgc +
                ", remark='" + remark + '\'' +
                ", idx=" + idx +
                ", shareAdTrack=" + shareAdTrack +
                ", favoriteAdTrack=" + favoriteAdTrack +
                ", webAdTrack=" + webAdTrack +
                ", date=" + date +
                ", promotion=" + promotion +
                ", label=" + label +
                ", descriptionEditor='" + descriptionEditor + '\'' +
                ", collected=" + collected +
                ", played=" + played +
                ", lastViewTime=" + lastViewTime +
                ", playlists=" + playlists +
                ", src=" + src +
                ", playInfo=" + playInfo +
                ", tags=" + tags +
                ", labelList=" + labelList +
                ", subtitles=" + subtitles +
                '}';
    }
}
