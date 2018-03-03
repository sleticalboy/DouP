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
public class TodoListActivity extends BaseActivity implements ITodoListContract.View {

    @Override
    protected void initView() {
    }

    @Override
    protected int attachLayout() {
        return R.layout.todo_activity_main;
    }

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onLoadFinished() {

    }

    @Override
    public void onNetError() {

    }
}
