package com.sleticalboy.doup.bean.news;

import com.sleticalboy.doup.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class NewsBean extends BaseBean {
    /**
     * date : 20171225
     * stories : [{"images":["https://pic2.zhimg.com/v2-9131d9be85259099f1fa1bbc4200f1bd.jpg"],"type":0,"id":9662117,"ga_prefix":"122519","title":"上班锻炼没时间？下班没器材？这些借口你可不能再用了"},{"title":"为什么蔚来、威马、拜腾都认为自己造电动车比传统车企有优势？","ga_prefix":"122518","images":["https://pic1.zhimg.com/v2-ca7731f615ca5de9140761704141ed24.jpg"],"multipic":true,"type":0,"id":9662526},{"images":["https://pic1.zhimg.com/v2-51871c037216de1999c8103c455988c0.jpg"],"type":0,"id":9662450,"ga_prefix":"122517","title":"指责苹果「故意弄慢老手机，逼用户换机」前，先看看是怎么回事"},{"title":"周末刷屏了朋友圈的视频里，天上这怪象到底怎么回事？","ga_prefix":"122516","images":["https://pic2.zhimg.com/v2-9effb9107289e39f1e7aaf8973f53239.jpg"],"multipic":true,"type":0,"id":9662646},{"images":["https://pic2.zhimg.com/v2-32eb9b0dc03f27306d4cfa0f6004fb21.jpg"],"type":0,"id":9662598,"ga_prefix":"122514","title":"圣诞老人真的存在吗？不如让孩子在能相信的年纪去相信"},{"images":["https://pic4.zhimg.com/v2-0f9a684a69570f5ef2f69d59a0ae561b.jpg"],"type":0,"id":9661860,"ga_prefix":"122513","title":"为什么有些人抽一辈子烟都不患癌，而有些不抽烟的人却得了癌？"},{"images":["https://pic1.zhimg.com/v2-3783e9abe5b13446faf79cbfec95e418.jpg"],"type":0,"id":9662477,"ga_prefix":"122512","title":"大误 · 平安夜前的梦"},{"images":["https://pic1.zhimg.com/v2-a70f9c94e0d31de35adfa62074e2bef8.jpg"],"type":0,"id":9662330,"ga_prefix":"122510","title":"短短几年间，干我们这一行的，几乎快要「跪着赚钱」了"},{"images":["https://pic3.zhimg.com/v2-150f92736367161d20e2a813c3b34176.jpg"],"type":0,"id":9661902,"ga_prefix":"122509","title":"你有没有想过，自己正处于会很快衰落的行业中？"},{"title":"使出了毕生绝学，告诉你怎么灭小强最有效","ga_prefix":"122508","images":["https://pic1.zhimg.com/v2-0dac6c6b716633d35972f673d76d4484.jpg"],"multipic":true,"type":0,"id":9662431},{"title":"乔布斯对家具的审美，会是什么样子的？","ga_prefix":"122507","images":["https://pic4.zhimg.com/v2-96fee2438fc03dc42ac3202e0bbfd857.jpg"],"multipic":true,"type":0,"id":9662559},{"title":"地球正经历着第六次生物大灭绝，这句话绝非危言耸听","ga_prefix":"122507","images":["https://pic4.zhimg.com/v2-929ce562b11218193cb6f2e528a312d7.jpg"],"multipic":true,"type":0,"id":9662551},{"images":["https://pic2.zhimg.com/v2-a8806dce2238e6f03f0624247a0d1bfd.jpg"],"type":0,"id":9662061,"ga_prefix":"122507","title":"股海沉浮中的游戏公司们"},{"images":["https://pic1.zhimg.com/v2-8568d79220137340100bc06dd123b9a8.jpg"],"type":0,"id":9662562,"ga_prefix":"122506","title":"瞎扯 · 如何正确地吐槽"}]
     * top_stories : [{"image":"https://pic1.zhimg.com/v2-eed67b11c74b96727892be6097b354ec.jpg","type":0,"id":9662450,"ga_prefix":"122517","title":"指责苹果「故意弄慢老手机，逼用户换机」前，先看看是怎么回事"},{"image":"https://pic3.zhimg.com/v2-41720fb6259434cfe29376d4e0673f16.jpg","type":0,"id":9662646,"ga_prefix":"122516","title":"周末刷屏了朋友圈的视频里，天上这怪象到底怎么回事？"},{"image":"https://pic3.zhimg.com/v2-51d9bc4c447d0ca7f90242a00a2804b2.jpg","type":0,"id":9662598,"ga_prefix":"122514","title":"圣诞老人真的存在吗？不如让孩子在能相信的年纪去相信"},{"image":"https://pic1.zhimg.com/v2-0ad42acfe4ce102576287cd342c7e7b0.jpg","type":0,"id":9662526,"ga_prefix":"122518","title":"为什么蔚来、威马、拜腾都认为自己造电动车比传统车企有优势？"},{"image":"https://pic4.zhimg.com/v2-c49e30e01058ddada2954e7022b15bfb.jpg","type":0,"id":9661499,"ga_prefix":"122409","title":"为什么有些人，情绪特别容易被社交网络影响？"}]
     */

    public String date;
    public List<StoriesBean> stories;
    public List<TopStoriesBean> top_stories;

    public static class StoriesBean {
        /**
         * images : ["https://pic2.zhimg.com/v2-9131d9be85259099f1fa1bbc4200f1bd.jpg"]
         * type : 0
         * id : 9662117
         * ga_prefix : 122519
         * title : 上班锻炼没时间？下班没器材？这些借口你可不能再用了
         * multipic : true
         */

        public int type;
        public int id;
        public String ga_prefix;
        public String title;
        public boolean multipic;
        public List<String> images;
    }

    public static class TopStoriesBean {
        /**
         * image : https://pic1.zhimg.com/v2-eed67b11c74b96727892be6097b354ec.jpg
         * type : 0
         * id : 9662450
         * ga_prefix : 122517
         * title : 指责苹果「故意弄慢老手机，逼用户换机」前，先看看是怎么回事
         */

        public String image;
        public int type;
        public int id;
        public String ga_prefix;
        public String title;
    }
}
