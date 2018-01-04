package com.sleticalboy.doup;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import org.junit.Test;
import org.junit.runner.RunWith;

/**
 * Created by Android Studio.
 * Date: 12/29/17.
 *
 * @author sleticalboy
 */

@RunWith(AndroidJUnit4.class)
public class TestAll {

    @Test
    public void weatherTest() {

        Context targetContext = InstrumentationRegistry.getTargetContext();
        Log.d("TestAll", "targetContext:" + targetContext);
    }
}
