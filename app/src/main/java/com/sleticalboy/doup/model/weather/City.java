package com.sleticalboy.doup.model.weather;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
@Entity
public class City implements Serializable {

    private static final long serialVersionUID = 8069227581691915901L;

    @Id
    public int id;
    @Property
    public String name;
    @Property
    public int cityCode;
    @Property
    public int provinceId;

    @Generated(hash = 1795735736)
    public City(int id, String name, int cityCode, int provinceId) {
        this.id = id;
        this.name = name;
        this.cityCode = cityCode;
        this.provinceId = provinceId;
    }

    @Generated(hash = 750791287)
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
