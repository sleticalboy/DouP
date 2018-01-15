package com.sleticalboy.doup.module.todo;

import android.content.Context;
import android.content.Intent;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class TodoListActivity extends BaseActivity {

    @Override
    protected void initView() {

    }

    @Override
    protected int attachLayout() {
        return R.layout.layout_empty;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }
}
