package com.sleticalboy.doup.model.openeye;

import android.content.Context;

import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.OpeneyeApi;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class OpeneyeModel {

    private static final String UDID = "26868b32e808498db32fd51fb422d00175e179df";
    private static final int VC = 83;
    private static final String CATEGORY_NAME = "date";

    private WeakReference<Context> mWeakReference;
    private OpeneyeApi mOpeneyeApiService;

    public OpeneyeModel(Context context) {
        mWeakReference = new WeakReference<>(context);
        RetrofitClient client = RetrofitClient.getInstance(mWeakReference.get(), ApiConstant.BASE_EYE_URL);
        mOpeneyeApiService = client.create(OpeneyeApi.class);
    }

    public Observable<RecommendBean> getRecommend() {
        return mOpeneyeApiService.getRecommend();
    }

    public Observable<RecommendBean> getMoreRecommend(String date) {
        return mOpeneyeApiService.getMoreRecommend(date, "2");
    }

    public Observable<List<FindingBean>> getFindings() {
        return mOpeneyeApiService.getFindings();
    }

    public Observable<HotBean> getFindingsDetail(String name) {
        return mOpeneyeApiService.getFindingsDetail(name, CATEGORY_NAME, UDID, VC);
    }

    public Observable<HotBean> getPopular(int num, String strategy) {
        return mOpeneyeApiService.getPopular(num, strategy, UDID, VC);
    }

    public Observable<HotBean> getSearch(int num, String query, int start) {
        return mOpeneyeApiService.getSearchData(num, query, start);
    }

    public void clear() {
        if (mWeakReference != null) {
            mWeakReference.clear();
            mWeakReference = null;
        }
    }
}
