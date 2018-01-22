package com.sleticalboy.doup.message.jpush;

import android.os.Parcelable;

import com.sleticalboy.base.IBaseListView;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/19/18.
 * </pre>
 *
 * @author sleticalboy
 */
public interface PushContract {

    interface IPushView extends IBaseListView {

        /**
         * 显示推送详情
         *
         * @param data
         */
        void showPushDetail(Parcelable data);
    }

    interface IPushPresenter {

        /**
         * 初始化数据
         */
        void initData();

        void initRecyclerView();

        /**
         * 点击条目
         *
         * @param position 位置
         */
        void clickItem(int position);
    }
}
