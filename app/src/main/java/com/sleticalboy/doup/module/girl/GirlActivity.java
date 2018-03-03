package com.sleticalboy.doup.module.girl;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.base.IBaseView;
import com.sleticalboy.doup.R;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.ToastUtils;

import java.io.File;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/14/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class GirlActivity extends BaseActivity implements IBaseView {

    public static final String TAG = "GirlActivity";
    public static final String IMG_DESC = "img_desc";
    public static final String IMG_URL = "img_url";
    public static final String TRANSIT_PIC = "picture";

    @BindView(R.id.img_girl)
    ImageView imgGirl;
    @BindView(R.id.btn_save_img)
    FloatingActionButton btnSaveImg;

    private String imgDesc;
    private String imgUrl;

    private GirlPresenter mPresenter;

    @Override
    protected void prepareTask() {
        Intent intent = getIntent();
        if (intent != null) {
            imgUrl = intent.getStringExtra(IMG_URL);
            imgDesc = intent.getStringExtra(IMG_DESC);
        }
    }

    @Override
    protected void initView() {
        mPresenter = new GirlPresenter(this, this);

        ViewCompat.setTransitionName(imgGirl, TRANSIT_PIC);
        // FIXME: 1/15/18 加载圆角图片
        ImageLoader.loadPhotoView(this, imgGirl, imgUrl);
    }

    @Override
    protected int attachLayout() {
        return R.layout.girl_activity_girl_detial;
    }

    @Override
    protected void onDestroy() {
        ImageLoader.clear(imgGirl);
        super.onDestroy();
    }

    public static void actionStart(Context context, Intent intent) {
        context.startActivity(intent);
    }

    public static Intent newIntent(Context context, String url, String desc) {
        Intent intent = new Intent(context, GirlActivity.class);
        intent.putExtra(IMG_URL, url);
        intent.putExtra(IMG_DESC, desc);
        return intent;
    }

    @OnClick(R.id.btn_save_img)
    public void onViewClicked(View view) {
        Snackbar.make(view, "Save Image", Snackbar.LENGTH_LONG)
                .setAction("OK", v -> mPresenter.saveImage(imgGirl, imgDesc))
                .show();
    }


    @Override
    public void onLoad() {

    }

    @Override
    public void onLoadFinished() {

    }

    @Override
    public void onNetError() {

    }

    public void updateGallery(File file) {
        Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        intent.setData(Uri.fromFile(file));
        this.sendBroadcast(intent);
        ToastUtils.INSTANCE.showToast(this, "保存成功");
    }
}
