package com.sleticalboy.doup.bean.eye;

import com.sleticalboy.doup.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class PopularBean extends BaseBean {
    /**
     *
     */

    public int count;
    public int total;
    public Object nextPageUrl;
    public boolean adExist;
    public List<ItemListBean> itemList;

    public static class ItemListBean {

        public String type;
        public DataBean data;
        public Object tag;
        public int id;
        public int adIndex;

        public static class DataBean {

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
            public WebUrlBean webUrl;
            public long releaseTime;
            public String library;
            public ConsumptionBean consumption;
            public Object campaign;
            public Object waterMarks;
            public Object adTrack;
            public String type;
            public Object titlePgc;
            public Object descriptionPgc;
            public Object remark;
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
            }

            public static class WebUrlBean {

                public String raw;
                public String forWeibo;
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
        }
    }
}
