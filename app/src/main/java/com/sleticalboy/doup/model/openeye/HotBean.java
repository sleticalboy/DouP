package com.sleticalboy.doup.model.openeye;

import com.sleticalboy.base.IBaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class HotBean implements IBaseBean {
    public int count;
    public int total;
    public String nextPageUrl;
    public boolean adExist;
    public List<ItemListBean> itemList;
}
