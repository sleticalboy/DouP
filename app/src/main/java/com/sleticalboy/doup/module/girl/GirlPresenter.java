package com.sleticalboy.doup.module.girl;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.widget.ImageView;

import com.sleticalboy.doup.base.BasePresenter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/15/18.
 * </pre>
 *
 * @author sleticalboy
 */
class GirlPresenter extends BasePresenter {

    private GirlActivity mGirlView;

    public GirlPresenter(Context context, GirlActivity girlView) {
        super(context);
        mGirlView = girlView;
    }

    @Override
    protected void onUnTokenView() {
        super.onUnTokenView();
        mGirlView = null;
    }

    /**
     * 保存图片
     */
    public void saveImage(ImageView imgGirl, String imgDesc) {
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
            mGirlView.updateGallery(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
