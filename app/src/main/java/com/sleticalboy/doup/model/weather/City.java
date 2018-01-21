package com.sleticalboy.doup.model.weather;

import org.litepal.crud.DataSupport;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class City extends DataSupport {
    public int id;
    public String name;
    public int cityCode;
    public int provinceId;

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityCode=" + cityCode +
                ", provinceId=" + provinceId +
                '}';
    }
}
