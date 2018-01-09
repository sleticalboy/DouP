package com.sleticalboy.doup.model;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.GirlsApi;
import com.sleticalboy.doup.model.girl.GirlBean;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class GirlModel {

    private WeakReference<Context> mWeakReference;
    private GirlsApi mGirlsApiService;

    public GirlModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_MEIZI_URL);
        mGirlsApiService = client.create(GirlsApi.class);
    }

    public Observable<GirlBean> getMeizi(int page) {
        return mGirlsApiService.getBeauty(page);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
