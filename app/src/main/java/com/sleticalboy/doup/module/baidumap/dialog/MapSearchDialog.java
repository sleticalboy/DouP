package com.sleticalboy.doup.module.baidumap.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import com.baidu.mapapi.search.core.SearchResult;
import com.baidu.mapapi.search.poi.OnGetPoiSearchResultListener;
import com.baidu.mapapi.search.poi.PoiCitySearchOption;
import com.baidu.mapapi.search.poi.PoiDetailResult;
import com.baidu.mapapi.search.poi.PoiResult;
import com.baidu.mapapi.search.poi.PoiSearch;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.module.baidumap.MapActivity;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.util.ToastUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 1/8/18.
 *
 * @author sleticalboy
 */
public class MapSearchDialog extends DialogFragment {

    @BindView(R.id.btn_back)
    ImageButton btnBack;
    @BindView(R.id.et_search_keyword)
    EditText etSearchKeyword;
    @BindView(R.id.btn_scan)
    ImageButton btnScan;

    Unbinder unbinder;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        assert window != null;
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(R.layout.dialog_map_search, window.findViewById(android.R.id.content), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();

        final Window window = getDialog().getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.0f;
        params.alpha = 0.9f;
        window.setAttributes(params);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.btn_back, R.id.et_search_keyword, R.id.btn_scan})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                dismiss();
                break;
            case R.id.et_search_keyword:
                searchKeyWord(etSearchKeyword.getText().toString().trim());
                break;
            case R.id.btn_scan:
                break;
        }
    }

    /**
     * 搜索关键字
     *
     * @param keyWord 关键字
     */
    private void searchKeyWord(String keyWord) {
        if (TextUtils.isEmpty(keyWord)) {
            ToastUtils.showToast(getActivity(), "请输入关键字");
            return;
        }
        PoiSearch poiSearch = PoiSearch.newInstance();
        poiSearch.searchInCity(new PoiCitySearchOption().city("北京").keyword(keyWord).pageCapacity(10));
        poiSearch.setOnGetPoiSearchResultListener(new OnGetPoiSearchResultListener() {
            @Override
            public void onGetPoiResult(PoiResult poiResult) {
                if (poiResult == null || poiResult.error != SearchResult.ERRORNO.NO_ERROR) {
                    ToastUtils.showToast(getActivity(), "没有搜索结果");
                    return;
                }
                RxBus.getBus().post(MapActivity.TAG_POI_RESULT, poiResult);
                dismiss();
            }

            @Override
            public void onGetPoiDetailResult(PoiDetailResult poiDetailResult) {

            }
        });
    }
}
