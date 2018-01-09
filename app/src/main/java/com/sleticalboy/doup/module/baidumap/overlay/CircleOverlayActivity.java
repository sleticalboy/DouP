package com.sleticalboy.doup.module.baidumap.overlay;

import com.baidu.location.BDLocation;
import com.baidu.mapapi.map.CircleOptions;
import com.baidu.mapapi.map.Stroke;
import com.sleticalboy.doup.module.baidumap.BaseMapActivity;
import com.sleticalboy.doup.module.baidumap.LocationManager;

/**
 * Created by Android Studio.
 * Date: 1/6/18.
 *
 * @author sleticalboy
 */
public class CircleOverlayActivity extends BaseMapActivity {

    @Override
    protected void handleLocation(BDLocation location) {
        animate2tam();
    }

    @Override
    protected void init() {
        // 圆心 半径 颜色 线条宽度 填充色等
        CircleOptions options = new CircleOptions()
                .center(LocationManager.TAM)
                .radius(1000)
                .stroke(new Stroke(2, 0x55ff0000))
                .fillColor(0x5500ff00);
        mBaiduMap.addOverlay(options); // 向地图添加图层
    }
}
