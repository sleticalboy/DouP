package com.sleticalboy.doup.http;

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
}
