package com.sleticalboy.doup.bean.openeye;

import com.sleticalboy.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class HotBean extends BaseBean {
    public int count;
    public int total;
    public String nextPageUrl;
    public boolean adExist;
    public List<ItemListBean> itemList;
}
