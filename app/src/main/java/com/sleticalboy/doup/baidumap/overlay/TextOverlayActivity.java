package com.sleticalboy.doup.baidumap.overlay;

import android.graphics.Color;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.TextOptions;
import com.sleticalboy.doup.baidumap.BaseMapActivity;
import com.sleticalboy.doup.baidumap.LocationManager;

/**
 * Created by Android Studio.
 * Date: 1/7/18.
 *
 * @author sleticalboy
 */
public class TextOverlayActivity extends BaseMapActivity {
    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }

    @Override
    protected void init() {
        TextOptions options = new TextOptions()
                .position(LocationManager.TAM)  // 位置
                .text("sleticalboy")            // 文本
                .fontColor(Color.RED)           // 颜色
                .fontSize(50)                   // 字号
                .rotate(45);                    // 旋转角度
        mBaiduMap.addOverlay(options);
    }
}
