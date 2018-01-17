package com.sleticalboy.doup.model.weather;

import com.sleticalboy.base.IBaseBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class Province extends DataSupport implements IBaseBean {
    public int id;
    public String name;
    public int provinceCode;

    @Override
    public String toString() {
        return "Province{" +
                "weatherId=" + id +
                ", name='" + name + '\'' +
                ", provinceCode=" + provinceCode +
                '}';
    }
}
