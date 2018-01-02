package com.sleticalboy.doup.mvp.model.bean.weather;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class Province extends DataSupport implements Serializable {
    public int id;
    public String name;
    public int provinceCode;

    @Override
    public String toString() {
        return "Province{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", provinceCode=" + provinceCode +
                '}';
    }
}
