package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.bean.eye.FindingBean;
import com.sleticalboy.doup.bean.eye.PopularBean;
import com.sleticalboy.doup.bean.eye.RecommendBean;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.EyesApi;

import java.lang.ref.WeakReference;
import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class EyesModel {

    public static final String UDID = "26868b32e808498db32fd51fb422d00175e179df";
    public static final int VC = 83;
    public static final String CATEGORY_NAME = "date";

    private WeakReference<Context> mWeakReference;
    private EyesApi mEyesApiService;

    public EyesModel(Context context) {
        mWeakReference = new WeakReference<Context>(context);
        RetrofitClient client = RetrofitClient.getInstance(context, ApiConstant.BASE_EYE_URL);
        mEyesApiService = client.create(EyesApi.class);
    }

    public rx.Observable<RecommendBean> getRecommend() {
        return mEyesApiService.getRecommend()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<RecommendBean> getMoreRecommend(String date) {
        return mEyesApiService.getMoreRecommend(date, "2")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<List<FindingBean>> getFindings() {
        return mEyesApiService.getFindings()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public rx.Observable<PopularBean> getFindingsDetail(String name) {
        return mEyesApiService.getFindingsDetail(name, CATEGORY_NAME, UDID, VC)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
