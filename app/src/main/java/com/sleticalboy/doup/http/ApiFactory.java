package com.sleticalboy.doup.http;

import com.sleticalboy.doup.http.api.BookApi;
import com.sleticalboy.doup.http.api.MovieApi;
import com.sleticalboy.doup.http.api.NewsApi;

/**
 * Created by Android Studio.
 * Date: 12/25/17.
 *
 * @author sleticalboy
 */

public class ApiFactory {

    private static BookApi sBookApi;
    private static MovieApi sMovieApi;
    private static NewsApi sNewsApi;

    public static BookApi getBookApi() {
        if (sBookApi == null)
            sBookApi = RetrofitClient.getClient().getBookApiService();
        return sBookApi;
    }

    public static MovieApi getMovieApi() {
        if (sMovieApi == null)
            sMovieApi = RetrofitClient.getClient().getMovieApiService();
        return sMovieApi;
    }

    public static NewsApi getNewsApi() {
        if (sNewsApi == null)
            sNewsApi = RetrofitClient.getClient().getNewsApiService();
        return sNewsApi;
    }
}
