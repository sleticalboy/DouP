package com.sleticalboy.doup.bean.meizi;

import com.sleticalboy.doup.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */
public class BeautyBean extends BaseBean {
    /**
     * error : false
     * results : [{"_id":"5a392689421aa90fe50c0293","createdAt":"2017-12-19T22:47:37.468Z","desc":"12-19","publishedAt":"2017-12-25T08:28:04.64Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171219224721_wFH5PL_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a388e4c421aa90fe2f02ced","createdAt":"2017-12-19T11:58:04.567Z","desc":"12-19","publishedAt":"2017-12-19T12:00:28.893Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171219115747_tH0TN5_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a2f2486421aa90fe2f02cd2","createdAt":"2017-12-12T08:36:22.670Z","desc":"12-12","publishedAt":"2017-12-15T08:59:11.361Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171212083612_WvLcTr_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a2dd04e421aa90fe2f02ccc","createdAt":"2017-12-11T08:24:46.981Z","desc":"12-11","publishedAt":"2017-12-11T08:43:39.682Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171211082435_CCblJd_Screenshot.jpeg","used":true,"who":"daimajia"},{"_id":"5a273d40421aa90fef203585","createdAt":"2017-12-06T08:43:44.386Z","desc":"12-6","publishedAt":"2017-12-06T08:49:34.303Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171206084331_wylXWG_misafighting_6_12_2017_8_43_16_390.jpeg","used":true,"who":"daimajia"},{"_id":"5a20ace0421aa90fe50c024c","createdAt":"2017-12-01T09:14:08.63Z","desc":"12-1","publishedAt":"2017-12-05T08:48:31.384Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171201091356_OPqmuO_kanna399_1_12_2017_9_13_42_126.jpeg","used":true,"who":"daimajia"},{"_id":"5a1ad043421aa90fe725366c","createdAt":"2017-11-26T22:31:31.363Z","desc":"11-26","publishedAt":"2017-11-30T13:11:10.665Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171126223118_jeCYtY_chayexiaoguo_apple_26_11_2017_22_30_59_409.jpeg","used":true,"who":"代码家"},{"_id":"5a16171d421aa90fef203553","createdAt":"2017-11-23T08:32:29.546Z","desc":"11-23","publishedAt":"2017-11-24T11:08:03.624Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171123083218_5mhRLg_sakura.gun_23_11_2017_8_32_9_312.jpeg","used":true,"who":"daimajia"},{"_id":"5a121895421aa90fe50c021e","createdAt":"2017-11-20T07:49:41.797Z","desc":"2017-11-20","publishedAt":"2017-11-20T12:42:06.454Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/20171120074925_ZXDh6l_joanne_722_20_11_2017_7_49_16_336.jpeg","used":true,"who":"daimajia"},{"_id":"5a0e4a0d421aa90fe7253643","createdAt":"2017-11-17T10:31:41.155Z","desc":"11-17","publishedAt":"2017-11-17T12:39:48.189Z","source":"chrome","type":"福利","url":"http://7xi8d6.com1.z0.glb.clouddn.com/2017-11-17-22794158_128707347832045_9158114204975104000_n.jpg","used":true,"who":"代码家"}]
     */

    public boolean error;
    public List<ResultsBean> results;

    public static class ResultsBean {
        /**
         * _id : 5a392689421aa90fe50c0293
         * createdAt : 2017-12-19T22:47:37.468Z
         * desc : 12-19
         * publishedAt : 2017-12-25T08:28:04.64Z
         * source : chrome
         * type : 福利
         * url : http://7xi8d6.com1.z0.glb.clouddn.com/20171219224721_wFH5PL_Screenshot.jpeg
         * used : true
         * who : daimajia
         */

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
        return "BeautyBean{" +
                "error=" + error +
                ", results=" + results.toString() +
                '}';
    }
}
