package com.sleticalboy.doup.http;

import com.sleticalboy.doup.http.api.BeautyApi;
import com.sleticalboy.doup.http.api.NewsApi;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class ApiFactory {

//    private static BookApi sBookApi;
//    private static MovieApi sMovieApi;
    private static NewsApi sNewsApi;
    private static BeautyApi sBeautyApi;

    public static NewsApi getNewsApi() {
        if (sNewsApi == null)
            sNewsApi = RetrofitClient.getClient().getNewsApiService();
        return sNewsApi;
    }

    public static BeautyApi getBeautyApi() {
        if (sBeautyApi == null)
            sBeautyApi = RetrofitClient.getClient().getBeautyApiService();
        return sBeautyApi;
    }
}
