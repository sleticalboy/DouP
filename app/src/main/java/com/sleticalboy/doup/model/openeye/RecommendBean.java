package com.sleticalboy.doup.model.openeye;

import com.sleticalboy.base.IBaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class RecommendBean implements IBaseBean {

    public String nextPageUrl;
    public long nextPublishTime;
    public String newestIssueType;
    public Object dialog;
    public List<IssueListBean> issueList;
    public String type;
    public Object tag;
    public int id;
    public int adIndex;

    public static class IssueListBean {
        public long releaseTime;
        public String type;
        public long date;
        public long publishTime;
        public int count;
        public List<ItemListBean> itemList;
    }
}
