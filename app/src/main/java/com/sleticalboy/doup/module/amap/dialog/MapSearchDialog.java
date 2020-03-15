package com.sleticalboy.doup.module.amap.dialog;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.sleticalboy.annotation.BindView;
import com.sleticalboy.annotation.ButterKnife;
import com.sleticalboy.annotation.OnClick;
import com.sleticalboy.doup.R;
import com.sleticalboy.util.ToastUtils;

/**
 * Created by Android Studio.
 * Date: 1/8/18.
 *
 * @author sleticalboy
 */
public class MapSearchDialog extends DialogFragment {

    @BindView(R.id.btn_back)
    private ImageButton btnBack;
    @BindView(R.id.et_search_keyword)
    private EditText etSearchKeyword;
    @BindView(R.id.btn_scan)
    private ImageButton btnScan;

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
        View rootView = inflater.inflate(
                R.layout.map_dialog_search, window.findViewById(android.R.id.content), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);

        ButterKnife.bind(this, rootView);
        return rootView;
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
            ToastUtils.INSTANCE.showToast(getActivity(), "请输入关键字");
            return;
        }
    }
}
