package com.sleticalboy.doup.model;

import android.content.Context;

import com.sleticalboy.base.BaseModel;
import com.sleticalboy.doup.http.ApiConstant;
import com.sleticalboy.doup.http.RetrofitClient;
import com.sleticalboy.doup.http.api.GirlsApi;
import com.sleticalboy.doup.model.girl.GirlBean;

import io.reactivex.Observable;

/**
 * Created by Android Studio.
 * Date: 1/2/18.
 *
 * @author sleticalboy
 */
public class GirlModel extends BaseModel {

    private GirlsApi mGirlsApiService;

    public GirlModel(Context context) {
        super(context);
        RetrofitClient client = RetrofitClient.getInstance(mContext.get(), ApiConstant.BASE_MEIZI_URL);
        mGirlsApiService = client.create(GirlsApi.class);
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
