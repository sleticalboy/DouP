package com.sleticalboy.doup.bean.news;

import com.sleticalboy.doup.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsDetailBean extends BaseBean {
    /**
     * body : <div class="main-wrap content-wrap">...</div><script type=“text/javascript”>window.daily=true</script>
     * image_source : Yestone 邑石网正版图库
     * title : 上班锻炼没时间？下班没器材？这些借口你可不能再用了
     * image : https://pic2.zhimg.com/v2-e821be13243d2d6732421dbd2c985e19.jpg
     * share_url : http://daily.zhihu.com/story/9662117
     * js : []
     * ga_prefix : 122519
     * images : ["https://pic2.zhimg.com/v2-9131d9be85259099f1fa1bbc4200f1bd.jpg"]
     * type : 0
     * id : 9662117
     * css : ["http://news-at.zhihu.com/css/news_qa.auto.css?v=4b3e3"]
     */

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
