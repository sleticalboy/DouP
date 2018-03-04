package com.sleticalboy.doup.bean.weather;

import com.google.gson.annotations.SerializedName;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
@Entity
public class County implements Serializable {

    private static final long serialVersionUID = -4374131938089667906L;

    @Id
    public int id;
    @Property
    public String name;
    @SerializedName("weather_id")
    @Property
    public String weatherId;
    @Property
    public int cityId;

    @Generated(hash = 731093275)
    public County(int id, String name, String weatherId, int cityId) {
        this.id = id;
        this.name = name;
        this.weatherId = weatherId;
        this.cityId = cityId;
    }

    @Generated(hash = 1991272252)
    public County() {
    }

    @Override
    public String toString() {
        return "County{" +
                "weatherId=" + id +
                ", name='" + name + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", cityId=" + cityId +
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

    public String getWeatherId() {
        return this.weatherId;
    }

    public void setWeatherId(String weatherId) {
        this.weatherId = weatherId;
    }

    public int getCityId() {
        return this.cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
