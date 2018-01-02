package com.sleticalboy.doup.mvp.model.bean.weather;

import com.google.gson.annotations.SerializedName;

import org.litepal.crud.DataSupport;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class County extends DataSupport implements Serializable {
    public int id;
    public String name;
    @SerializedName("weather_id")
    public String weatherId;
    public int cityId;

    @Override
    public String toString() {
        return "County{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
