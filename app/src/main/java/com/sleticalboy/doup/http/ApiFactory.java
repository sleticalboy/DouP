package com.sleticalboy.doup.http;

import com.sleticalboy.doup.http.api.EyesApi;
import com.sleticalboy.doup.http.api.MeiziApi;
import com.sleticalboy.doup.http.api.NewsApi;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class ApiFactory {

    private static NewsApi sNewsApi;
    private static MeiziApi sMeiziApi;
    private static EyesApi sEyesApi;

    public static NewsApi getNewsApi() {
        if (sNewsApi == null)
            sNewsApi = RetrofitClient.getClient().getNewsApiService();
        return sNewsApi;
    }

    public static MeiziApi getMeiziApi() {
        if (sMeiziApi == null)
            sMeiziApi = RetrofitClient.getClient().getMeiziApiService();
        return sMeiziApi;
    }

    public static EyesApi getEyesApi() {
        if (sEyesApi == null)
            sEyesApi = RetrofitClient.getClient().getEyesApiService();
        return sEyesApi;
    }
}
