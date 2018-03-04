package com.sleticalboy.doup.module.todo;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.DouApp;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.todo.DaoSession;
import com.sleticalboy.doup.model.todo.Note;
import com.sleticalboy.doup.model.todo.NoteDao;
import com.sleticalboy.util.RxBus;
import com.sleticalboy.widget.recyclerview.EasyRecyclerView;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;

/**
 * <pre>
 *   Created by Android Studio.
 *
 *   Date: 1/13/18.
 * </pre>
 *
 * @author sleticalboy
 */
public class TodoListActivity extends BaseActivity/* implements ITodoContract.View */{

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    @BindView(R.id.fabAdd)
    FloatingActionButton fabAdd;

    private String[] mTabTitleIds = {"所有", "已做", "待做"};
    private List<Note> mDataSet = new ArrayList<>();
    private TodoAdapter mAdapter;
    private NoteDao mNoteDao;
    private Query<Note> mQuery;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }

    @Override
    protected void afterViews() {
        mNoteDao = ((DaoSession) DouApp.getDaoSession()).getNoteDao();
        mQuery = mNoteDao.queryBuilder().build();
        updateList();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoAdapter(this);
        mAdapter.addAll(mDataSet);
        recyclerView.setAdapter(mAdapter);

        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialog();
            }
        });
    }

    @Override
    protected int attachLayout() {
        return R.layout.todo_activity_main;
    }

//    @Override
    public void showDialog() {
        TodoDialog dialog = new TodoDialog();
        dialog.show(getSupportFragmentManager(), "");
    }

//    @Override
    public void setAdapter(RecyclerArrayAdapter adapter) {
    }

//    @Override
    public Context getContext() {
        return this;
    }

    public void updateList(Note note) {
        mNoteDao.insert(note);
        updateList();
    }

    private void updateList() {
        mAdapter.addAll(mQuery.list());
    }
}
