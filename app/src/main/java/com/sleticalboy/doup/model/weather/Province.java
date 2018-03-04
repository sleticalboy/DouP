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
public class Province implements Serializable {

    private static final long serialVersionUID = 5834583142067139664L;

    @Id
    public int id;
    @Property
    public String name;
    @Property
    public int provinceCode;

    @Generated(hash = 964095859)
    public Province(int id, String name, int provinceCode) {
        this.id = id;
        this.name = name;
        this.provinceCode = provinceCode;
    }

    @Generated(hash = 1309009906)
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
