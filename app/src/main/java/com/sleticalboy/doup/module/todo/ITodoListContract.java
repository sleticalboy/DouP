package com.sleticalboy.doup.module.todo;

import com.sleticalboy.base.IBaseView;

/**
 * Created by Android Studio.
 *
 * @author sleticalboy
 */
public interface ITodoListContract {

    interface View extends IBaseView {
    }

    interface Presenter {
        void add();
        void delete();
        void update();
    }
}
