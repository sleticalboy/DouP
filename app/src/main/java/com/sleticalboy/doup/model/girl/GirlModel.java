package com.sleticalboy.doup.model.girl;

import android.content.Context;

import com.sleticalboy.base.BaseModel;
import com.sleticalboy.doup.bean.girl.GirlBean;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.GirlsApi;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public final class GirlModel extends BaseModel {

    private GirlsApi mGirlsApiService;

    public GirlModel(Context context) {
        super(context);
        // RetrofitClient client = RetrofitClient.retrofit(getWeakReference().get(), ApiConstant.BASE_MEIZI_URL);
        mGirlsApiService = RetrofitClient.retrofit().create(GirlsApi.class);
    }

    public Observable<GirlBean> getMeizi(int page) {
        return mGirlsApiService.getBeauty(page);
    }

    @Override
    public void clear() {
        super.clear();
        if (mGirlsApiService != null) {
            mGirlsApiService = null;
        }
    }
}
