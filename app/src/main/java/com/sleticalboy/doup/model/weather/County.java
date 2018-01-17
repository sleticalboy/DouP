package com.sleticalboy.doup.model.weather;

import com.google.gson.annotations.SerializedName;
import com.sleticalboy.base.IBaseBean;

import org.litepal.crud.DataSupport;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class County extends DataSupport implements IBaseBean {
    public int id;
    public String name;
    @SerializedName("weather_id")
    public String weatherId;
    public int cityId;

    @Override
    public String toString() {
        return "County{" +
                "weatherId=" + id +
                ", name='" + name + '\'' +
                ", weatherId='" + weatherId + '\'' +
                ", cityId=" + cityId +
                '}';
    }
}
