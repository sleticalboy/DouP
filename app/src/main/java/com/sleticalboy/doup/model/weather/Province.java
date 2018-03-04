package com.sleticalboy.doup.model.weather;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class Province /*extends DataSupport*/ {
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
