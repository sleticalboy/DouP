package com.sleticalboy.doup.bean.book;

import com.sleticalboy.doup.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public class BookBean extends BaseBean {
    /**
     * rating : {"max":10,"numRaters":18,"average":"7.4","min":0}
     * subtitle :
     * author : ["少华"]
     * pubdate : 2005-01
     * tags : [{"count":15,"name":"军事","title":"军事"},{"count":11,"name":"豆瓣第一个条目","title":"豆瓣第一个条目"},{"count":6,"name":"第一","title":"第一"},{"count":5,"name":"传记","title":"传记"},{"count":5,"name":"一本见证了豆瓣诞生的书","title":"一本见证了豆瓣诞生的书"},{"count":2,"name":"历史","title":"历史"},{"count":2,"name":"中国","title":"中国"},{"count":2,"name":"中共无衔军事家","title":"中共无衔军事家"}]
     * origin_title :
     * image : https://img1.doubanio.com/mpic/s1727038.jpg
     * binding : 平装
     * translator : []
     * catalog : 飞天骄将黄公略
     * 百劫将星段德昌
     * 无衔元帅叶挺
     * 铁血儒将曾中生
     * 圣地能人刘志丹
     * 碧血悲歌许继慎
     * 神行太保罗炳辉
     * 殉国名将左权
     * 文武全才彭雪枫
     * 赤胆农王方志敏
     * 军中智囊蔡申熙
     * 后记
     * pages : 403
     * images : {"small":"https://img1.doubanio.com/spic/s1727038.jpg","large":"https://img1.doubanio.com/lpic/s1727038.jpg","medium":"https://img1.doubanio.com/mpic/s1727038.jpg"}
     * alt : https://book.douban.com/subject/1000001/
     * id : 1000001
     * publisher : 湖北人民出版社
     * isbn10 : 7216041283
     * isbn13 : 9787216041287
     * title : 十一位牺牲在建国前的中共无衔军事家
     * url : https://api.douban.com/v2/book/1000001
     * alt_title :
     * author_intro :
     * summary : 十一位牺牲在建国前的中共无衔军事家，ISBN：9787216041287，作者：少华著
     * price : 26.00元
     */

    public RatingBean rating;
    public String subtitle;
    public String pubdate;
    public String origin_title;
    public String image;
    public String binding;
    public String catalog;
    public String pages;
    public ImagesBean images;
    public String alt;
    public String id;
    public String publisher;
    public String isbn10;
    public String isbn13;
    public String title;
    public String url;
    public String alt_title;
    public String author_intro;
    public String summary;
    public String price;
    public List<String> author;
    public List<TagsBean> tags;
    public List<?> translator;

    public static class RatingBean {
        /**
         * max : 10
         * numRaters : 18
         * average : 7.4
         * min : 0
         */

        public int max;
        public int numRaters;
        public String average;
        public int min;
    }

    public static class ImagesBean {
        /**
         * small : https://img1.doubanio.com/spic/s1727038.jpg
         * large : https://img1.doubanio.com/lpic/s1727038.jpg
         * medium : https://img1.doubanio.com/mpic/s1727038.jpg
         */

        public String small;
        public String large;
        public String medium;
    }

    public static class TagsBean {
        /**
         * count : 15
         * name : 军事
         * title : 军事
         */

        public int count;
        public String name;
        public String title;
    }
}
