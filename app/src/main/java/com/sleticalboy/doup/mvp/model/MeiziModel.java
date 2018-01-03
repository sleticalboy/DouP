package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.MeiziApi;
import com.sleticalboy.doup.mvp.model.bean.meizi.BeautyBean;

import java.lang.ref.WeakReference;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class MeiziModel {

    private WeakReference<Context> mWeakReference;
    private MeiziApi mMeiziApiService;

    public MeiziModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_MEIZI_URL);
        mMeiziApiService = client.create(MeiziApi.class);
    }

    public Observable<BeautyBean> getMeizi(int page) {
        return mMeiziApiService.getBeauty(page);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
