package com.sleticalboy.doup.model.girl;

import com.sleticalboy.doup.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public class GirlBean extends BaseBean {
    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        public String _id;
        public String createdAt;
        public String desc;
        public String publishedAt;
        public String source;
        public String type;
        public String url;
        public boolean used;
        public String who;

        @Override
        public String toString() {
            return "ResultsBean{" +
                    "_id='" + _id + '\'' +
                    ", createdAt='" + createdAt + '\'' +
                    ", desc='" + desc + '\'' +
                    ", publishedAt='" + publishedAt + '\'' +
                    ", source='" + source + '\'' +
                    ", type='" + type + '\'' +
                    ", url='" + url + '\'' +
                    ", used=" + used +
                    ", who='" + who + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "GirlBean{" +
                "error=" + error +
                ", results=" + results.toString() +
                '}';
    }
}
