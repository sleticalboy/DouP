package com.sleticalboy.doup.bean.book;

import com.sleticalboy.doup.bean.base.BaseBean;

/**
 * Created by Android Studio.
 * Date: 12/26/17.
 *
 * @author sleticalboy
 */

public class Book extends BaseBean {

    public String imgUrl;
    public String title;
    public int imgId;

    @Override
    public String toString() {
        return "Book{" +
                "imgUrl='" + imgUrl + '\'' +
                ", title='" + title + '\'' +
                ", imgId=" + imgId +
                '}';
    }
}
