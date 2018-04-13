package com.sleticalboy.doup.module.todo;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.sleticalboy.base.BaseActivity;
import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.todo.Note;
import com.sleticalboy.doup.bean.todo.NoteModel;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import butterknife.BindView;

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
    private TodoAdapter mAdapter;
    private NoteModel mNoteModel;

    public static void actionStart(Context context) {
        context.startActivity(new Intent(context, TodoListActivity.class));
    }

    @Override
    protected void afterViews() {
        mNoteModel = new NoteModel();
        updateList();
    }

    @Override
    protected void initView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TodoAdapter(this);
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
        final TodoDialog dialog = new TodoDialog();
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
        mNoteModel.addNote(note);
        updateList();
    }

    private void updateList() {
        mAdapter.setDataSet(mNoteModel.getNotes());
    }
}
