package com.sleticalboy.doup.module.girl;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.widget.ImageView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.base.BaseActivity;
import com.sleticalboy.doup.base.IBaseView;
import com.sleticalboy.util.ImageLoader;
import com.sleticalboy.util.ToastUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

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

    @Override
    public void onLoading() {

    }

    @Override
    public void onLoadingEnd() {

    }

    @Override
    public void onNetError() {

    }

    @Override
    protected void initView() {
        Intent intent = getIntent();
        if (intent != null) {
            imgUrl = intent.getStringExtra(IMG_URL);
            imgDesc = intent.getStringExtra(IMG_DESC);
        }
        ViewCompat.setTransitionName(imgGirl, TRANSIT_PIC);
//        ImageLoader.load(this, imgGirl, imgUrl);
//        ImageLoader.attachView(imgGirl);
        // FIXME: 1/15/18 加载圆角图片
        ImageLoader.loadPhotoView(this, imgGirl, imgUrl);
    }

    @Override
    protected int attachLayout() {
        return R.layout.activity_girl;
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
                .setAction("OK", v -> saveImage())
                .show();
    }

    /**
     * 保存图片
     */
    private void saveImage() {
        imgGirl.buildDrawingCache();
        Bitmap girlBitmap = imgGirl.getDrawingCache();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        girlBitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] bytes = baos.toByteArray();
        File dir = new File(Environment.getExternalStorageDirectory().getAbsolutePath() + "/DouP");
        if (!dir.exists()) {
            dir.mkdir();
        }
        try {
            File file = new File(dir, imgDesc.substring(0, 10) + ".png");
            FileOutputStream fos = new FileOutputStream(file);
            fos.write(bytes, 0, bytes.length);
            fos.flush();
            // 更新相册
            Intent intent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
            intent.setData(Uri.fromFile(file));
            this.sendBroadcast(intent);
            ToastUtils.showToast(this, "保存成功");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
