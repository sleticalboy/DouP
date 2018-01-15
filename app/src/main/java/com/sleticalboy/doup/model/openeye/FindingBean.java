package com.sleticalboy.doup.model.openeye;

import java.io.Serializable;

/**
 * Created by Android Studio.
 * Date: 12/27/17.
 *
 * @author sleticalboy
 */

public class FindingBean implements Serializable {
    public int id;
    public String name;
    public Object alias;
    public String description;
    public String bgPicture;
    public String bgColor;
    public String headerImage;
    public Object defaultAuthorId;

    @Override
    public String toString() {
        return "FindingBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", alias=" + alias +
                ", description='" + description + '\'' +
                ", bgPicture='" + bgPicture + '\'' +
                ", bgColor='" + bgColor + '\'' +
                ", headerImage='" + headerImage + '\'' +
                ", defaultAuthorId=" + defaultAuthorId +
                '}';
    }
}
