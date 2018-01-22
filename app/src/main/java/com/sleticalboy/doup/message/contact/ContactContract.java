package com.sleticalboy.doup.message.contact;

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
public interface ContactContract {

    interface IContactView extends IBaseListView {

        void showContactDetail(Parcelable data);
    }

    interface IContactPresenter {

        void clickItem(int position);
    }
}
