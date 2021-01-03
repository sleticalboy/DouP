package com.sleticalboy.doup.bean.weather;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class City implements Serializable {

    private static final long serialVersionUID = 8069227581691915901L;

    public int id;
    public String name;
    public int cityCode;
    public int provinceId;

    public City(int id, String name, int cityCode, int provinceId) {
        this.id = id;
        this.name = name;
        this.cityCode = cityCode;
        this.provinceId = provinceId;
    }

    public City() {
    }

    @Override
    public String toString() {
        return "City{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", cityCode=" + cityCode +
                ", provinceId=" + provinceId +
                '}';
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCityCode() {
        return this.cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public int getProvinceId() {
        return this.provinceId;
    }

    public void setProvinceId(int provinceId) {
        this.provinceId = provinceId;
    }
}
