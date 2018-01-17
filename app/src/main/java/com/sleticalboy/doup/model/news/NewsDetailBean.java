package com.sleticalboy.doup.model.news;

import com.sleticalboy.base.IBaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */
public class NewsDetailBean implements IBaseBean {

    public String body;
    public String image_source;
    public String title;
    public String image;
    public String share_url;
    public String ga_prefix;
    public int type;
    public int id;
    public List<String> js;
    public List<String> images;
    public List<String> css;

    @Override
    public String toString() {
        return "NewsDetailBean{" +
                "body='" + body + '\'' +
                ", image_source='" + image_source + '\'' +
                ", title='" + title + '\'' +
                ", image='" + image + '\'' +
                ", share_url='" + share_url + '\'' +
                ", ga_prefix='" + ga_prefix + '\'' +
                ", type=" + type +
                ", id=" + id +
                ", js=" + js.toString() +
                ", images=" + images.toString() +
                ", css=" + css.toString() +
                '}';
    }
}
