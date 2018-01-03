package com.sleticalboy.doup.mvp.model;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.EyesApi;
import com.sleticalboy.doup.mvp.model.bean.eye.FindingBean;
import com.sleticalboy.doup.mvp.model.bean.eye.PopularBean;
import com.sleticalboy.doup.mvp.model.bean.eye.RecommendBean;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class EyesModel {

    private static final String UDID = "26868b32e808498db32fd51fb422d00175e179df";
    private static final int VC = 83;
    private static final String CATEGORY_NAME = "date";

    private WeakReference<Context> mWeakReference;
    private EyesApi mEyesApiService;

    public EyesModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_EYE_URL);
        mEyesApiService = client.create(EyesApi.class);
    }

    public Observable<RecommendBean> getRecommend() {
        return mEyesApiService.getRecommend();
    }

    public Observable<RecommendBean> getMoreRecommend(String date) {
        return mEyesApiService.getMoreRecommend(date, "2");
    }

    public Observable<List<FindingBean>> getFindings() {
        return mEyesApiService.getFindings();
    }

    public Observable<PopularBean> getFindingsDetail(String name) {
        return mEyesApiService.getFindingsDetail(name, CATEGORY_NAME, UDID, VC);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
