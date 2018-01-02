package com.sleticalboy.doup.mvp.model.bean.weather;

import com.google.gson.annotations.SerializedName;
import com.sleticalboy.doup.mvp.model.bean.base.BaseBean;

import java.util.List;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

public class WeatherBean extends BaseBean {
    @SerializedName("HeWeather")
    public List<HeWeatherBean> HeWeather;

    public static class HeWeatherBean {
        @SerializedName("aqi")
        public AqiBean aqi;
        @SerializedName("basic")
        public BasicBean basic;
        @SerializedName("now")
        public NowBean now;
        @SerializedName("status")
        public String status;
        @SerializedName("suggestion")
        public SuggestionBean suggestion;
        @SerializedName("daily_forecast")
        public List<DailyForecastBean> dailyForecast;

        public static class AqiBean {
            @SerializedName("city")
            public CityBean city;

            public static class CityBean {
                @SerializedName("aqi")
                public String aqi;
                @SerializedName("qlty")
                public String qlty;
                @SerializedName("pm25")
                public String pm25;
                @SerializedName("pm10")
                public String pm10;
                @SerializedName("no2")
                public String no2;
                @SerializedName("so2")
                public String so2;
                @SerializedName("co")
                public String co;
                @SerializedName("o3")
                public String o3;

                @Override
                public String toString() {
                    return "CityBean{" +
                            "aqi='" + aqi + '\'' +
                            ", qlty='" + qlty + '\'' +
                            ", pm25='" + pm25 + '\'' +
                            ", pm10='" + pm10 + '\'' +
                            ", no2='" + no2 + '\'' +
                            ", so2='" + so2 + '\'' +
                            ", co='" + co + '\'' +
                            ", o3='" + o3 + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "AqiBean{" +
                        "city=" + city +
                        '}';
            }
        }

        public static class BasicBean {
            @SerializedName("city")
            public String city;
            @SerializedName("cnty")
            public String cnty;
            @SerializedName("id")
            public String id;
            @SerializedName("lat")
            public String lat;
            @SerializedName("lon")
            public String lon;
            @SerializedName("update")
            public UpdateBean update;

            public static class UpdateBean {
                @SerializedName("loc")
                public String loc;
                @SerializedName("utc")
                public String utc;

                @Override
                public String toString() {
                    return "UpdateBean{" +
                            "loc='" + loc + '\'' +
                            ", utc='" + utc + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "BasicBean{" +
                        "city='" + city + '\'' +
                        ", cnty='" + cnty + '\'' +
                        ", id='" + id + '\'' +
                        ", lat='" + lat + '\'' +
                        ", lon='" + lon + '\'' +
                        ", update=" + update +
                        '}';
            }
        }

        public static class NowBean {
            @SerializedName("cond")
            public CondBean cond;
            @SerializedName("fl")
            public String fl;
            @SerializedName("hum")
            public String hum;
            @SerializedName("pcpn")
            public String pcpn;
            @SerializedName("pres")
            public String pres;
            @SerializedName("tmp")
            public String tmp;
            @SerializedName("vis")
            public String vis;
            @SerializedName("wind")
            public WindBean wind;

            public static class CondBean {
                @SerializedName("code")
                public String code;
                @SerializedName("txt")
                public String txt;

                @Override
                public String toString() {
                    return "CondBean{" +
                            "code='" + code + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            public static class WindBean {
                @SerializedName("deg")
                public String deg;
                @SerializedName("dir")
                public String dir;
                @SerializedName("sc")
                public String sc;
                @SerializedName("spd")
                public String spd;

                @Override
                public String toString() {
                    return "WindBean{" +
                            "deg='" + deg + '\'' +
                            ", dir='" + dir + '\'' +
                            ", sc='" + sc + '\'' +
                            ", spd='" + spd + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "NowBean{" +
                        "cond=" + cond +
                        ", fl='" + fl + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pcpn='" + pcpn + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp='" + tmp + '\'' +
                        ", vis='" + vis + '\'' +
                        ", wind=" + wind +
                        '}';
            }
        }

        public static class SuggestionBean {
            @SerializedName("air")
            public AirBean air;
            @SerializedName("comf")
            public AirBean comf;
            @SerializedName("cw")
            public AirBean cw;
            @SerializedName("drsg")
            public AirBean drsg;
            @SerializedName("flu")
            public AirBean flu;
            @SerializedName("sport")
            public AirBean sport;
            @SerializedName("trav")
            public AirBean trav;
            @SerializedName("uv")
            public AirBean uv;

            public static class AirBean {
                @SerializedName("brf")
                public String brf;
                @SerializedName("txt")
                public String txt;

                @Override
                public String toString() {
                    return "AirBean{" +
                            "brf='" + brf + '\'' +
                            ", txt='" + txt + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "SuggestionBean{" +
                        "air=" + air +
                        ", comf=" + comf +
                        ", cw=" + cw +
                        ", drsg=" + drsg +
                        ", flu=" + flu +
                        ", sport=" + sport +
                        ", trav=" + trav +
                        ", uv=" + uv +
                        '}';
            }
        }

        public static class DailyForecastBean {
            @SerializedName("astro")
            public AstroBean astro;
            @SerializedName("cond")
            public CondBeanX cond;
            @SerializedName("date")
            public String date;
            @SerializedName("hum")
            public String hum;
            @SerializedName("pcpn")
            public String pcpn;
            @SerializedName("pop")
            public String pop;
            @SerializedName("pres")
            public String pres;
            @SerializedName("tmp")
            public TmpBean tmp;
            @SerializedName("uv")
            public String uv;
            @SerializedName("vis")
            public String vis;
            @SerializedName("wind")
            public NowBean.WindBean wind;

            public static class AstroBean {
                @SerializedName("mr")
                public String mr;
                @SerializedName("ms")
                public String ms;
                @SerializedName("sr")
                public String sr;
                @SerializedName("ss")
                public String ss;

                @Override
                public String toString() {
                    return "AstroBean{" +
                            "mr='" + mr + '\'' +
                            ", ms='" + ms + '\'' +
                            ", sr='" + sr + '\'' +
                            ", ss='" + ss + '\'' +
                            '}';
                }
            }

            public static class CondBeanX {
                @SerializedName("code_d")
                public String codeD;
                @SerializedName("code_n")
                public String codeN;
                @SerializedName("txt_d")
                public String txtD;
                @SerializedName("txt_n")
                public String txtN;

                @Override
                public String toString() {
                    return "CondBeanX{" +
                            "codeD='" + codeD + '\'' +
                            ", codeN='" + codeN + '\'' +
                            ", txtD='" + txtD + '\'' +
                            ", txtN='" + txtN + '\'' +
                            '}';
                }
            }

            public static class TmpBean {
                @SerializedName("max")
                public String max;
                @SerializedName("min")
                public String min;

                @Override
                public String toString() {
                    return "TmpBean{" +
                            "max='" + max + '\'' +
                            ", min='" + min + '\'' +
                            '}';
                }
            }

            @Override
            public String toString() {
                return "DailyForecastBean{" +
                        "astro=" + astro +
                        ", cond=" + cond +
                        ", date='" + date + '\'' +
                        ", hum='" + hum + '\'' +
                        ", pcpn='" + pcpn + '\'' +
                        ", pop='" + pop + '\'' +
                        ", pres='" + pres + '\'' +
                        ", tmp=" + tmp +
                        ", uv='" + uv + '\'' +
                        ", vis='" + vis + '\'' +
                        ", wind=" + wind +
                        '}';
            }
        }

        @Override
        public String toString() {
            return "HeWeatherBean{" +
                    "aqi=" + aqi +
                    ", basic=" + basic +
                    ", now=" + now +
                    ", status='" + status + '\'' +
                    ", suggestion=" + suggestion +
                    ", dailyForecast=" + dailyForecast.toString() +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "WeatherBean{" +
                "HeWeather=" + HeWeather.toString() +
                '}';
    }
}
