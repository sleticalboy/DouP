package com.sleticalboy.doup.bean.weather;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */
public class Province implements Serializable {

    private static final long serialVersionUID = 5834583142067139664L;

    public int id;
    public String name;
    public int provinceCode;

    public Province(int id, String name, int provinceCode) {
        this.id = id;
        this.name = name;
        this.provinceCode = provinceCode;
    }

    public Province() {
    }

    @Override
    public String toString() {
        return "Province{" +
                "weatherId=" + id +
                ", name='" + name + '\'' +
                ", provinceCode=" + provinceCode +
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

    public int getProvinceCode() {
        return this.provinceCode;
    }

    public void setProvinceCode(int provinceCode) {
        this.provinceCode = provinceCode;
    }
}
