package com.sleticalboy.router;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Android Studio.
 * Date: 11/6/17.
 *
 * @author sleticalboy
 */
public class Route implements Parcelable {

    public String schema;
    public String host;
    public String path;
    public String pkgName;
    public String activityName;

    private Route() {
    }

    public static Route newInstance() {
        return new Route();
    }

    protected Route(Parcel in) {
        schema = in.readString();
        host = in.readString();
        path = in.readString();
        pkgName = in.readString();
        activityName = in.readString();
    }

    public static final Creator<Route> CREATOR = new Creator<Route>() {
        @Override
        public Route createFromParcel(Parcel in) {
            return new Route(in);
        }

        @Override
        public Route[] newArray(int size) {
            return new Route[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(schema);
        dest.writeString(host);
        dest.writeString(path);
        dest.writeString(pkgName);
        dest.writeString(activityName);
    }

    @Override
    public String toString() {
        return "Route{" +
                "schema='" + schema + '\'' +
                ", host='" + host + '\'' +
                ", path='" + path + '\'' +
                ", pkgName='" + pkgName + '\'' +
                ", activityName='" + activityName + '\'' +
                '}';
    }
}
