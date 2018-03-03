package com.sleticalboy.doup.module.girl

import android.widget.ImageView

import com.sleticalboy.base.IBaseView

/**
 * Created on 18-2-19.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
interface IGirlContract {

    interface View : IBaseView

    interface Presenter {
        fun saveImage(imgGirl: ImageView, imgDesc: String)
    }
}
