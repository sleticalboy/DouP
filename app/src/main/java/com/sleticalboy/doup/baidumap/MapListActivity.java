package com.sleticalboy.doup.baidumap;

import android.app.ListActivity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.baidu.mapapi.SDKInitializer;
import com.sleticalboy.doup.baidumap.location.LocationActivity;
import com.sleticalboy.doup.baidumap.overlay.CircleOverlayActivity;
import com.sleticalboy.doup.baidumap.overlay.MarkerOverlayActivity;
import com.sleticalboy.doup.baidumap.overlay.TextOverlayActivity;
import com.sleticalboy.doup.baidumap.search.poi.SearchInBoundActivity;
import com.sleticalboy.doup.baidumap.search.poi.SearchInCityActivity;
import com.sleticalboy.doup.baidumap.search.poi.SearchNearbyActivity;
import com.sleticalboy.doup.baidumap.search.route.DrivingRouteSearchActivity;
import com.sleticalboy.doup.baidumap.search.route.TransitRouteSearchActivity;
import com.sleticalboy.doup.baidumap.search.route.WalkingRouteSearchActivity;
import com.sleticalboy.doup.util.ToastUtils;

/**
 * Created by Android Studio.
 * Date: 1/6/18.
 *
 * @author sleticalboy
 */
public class MapListActivity extends ListActivity {

    public static final String TAG = "MapListActivity";

    private BroadcastReceiver mReceiver;

    private ItemHolder[] mData = {
            new ItemHolder(LocationActivity.class, "基本定位"),
            new ItemHolder(MapActivity.class, "基本地图"),
            new ItemHolder(CircleOverlayActivity.class, "圆形覆盖物"),
            new ItemHolder(TextOverlayActivity.class, "文字覆盖物"),
            new ItemHolder(MarkerOverlayActivity.class, "标志覆盖物"),
            new ItemHolder(SearchInBoundActivity.class, "在范围内搜索"),
            new ItemHolder(SearchInCityActivity.class, "在城市内搜索"),
            new ItemHolder(SearchNearbyActivity.class, "周边搜索"),
            new ItemHolder(DrivingRouteSearchActivity.class, "驾车路线搜索"),
            new ItemHolder(TransitRouteSearchActivity.class, "换乘路线搜索"),
            new ItemHolder(WalkingRouteSearchActivity.class, "步行路线搜索"),
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ArrayAdapter<ItemHolder> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, mData);
        setListAdapter(adapter);

        registerSdkCheckReceiver();
    }

    /**
     * 检测百度地图 appkey 是否正确
     */
    private void registerSdkCheckReceiver() {
        mReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null)
                    switch (action) {
                        case SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR:
                            ToastUtils.showToast(MapListActivity.this, "网络错误");
                            break;
                        case SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR:
                            ToastUtils.showToast(MapListActivity.this, "appkey 验证失败");
                            break;
                        default:
                            break;
                    }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(SDKInitializer.SDK_BROADCAST_ACTION_STRING_NETWORK_ERROR);
        filter.addAction(SDKInitializer.SDK_BROADTCAST_ACTION_STRING_PERMISSION_CHECK_ERROR);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ItemHolder item = (ItemHolder) l.getItemAtPosition(position);
        startActivity(new Intent(this, item.clazz));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, MapListActivity.class));
    }

    /**
     * 用于存储数据
     */
    static class ItemHolder {
        public Class<?> clazz;
        public String name;

        public ItemHolder(Class<?> clazz, String name) {
            this.clazz = clazz;
            this.name = name;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}
