package com.sleticalboy.doup.module.todo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.todo.Note;

import java.text.DateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by Android Studio.
 * Date: 1/8/18.
 *
 * @author sleticalboy
 */
public class TodoDialog extends BaseDialogFragment {

    @BindView(R.id.btnBack)
    TextView btnBack;
    @BindView(R.id.btnDone)
    TextView btnDone;
    @BindView(R.id.tvCreationTime)
    TextView tvCreationTime;
    @BindView(R.id.etNoteContent)
    EditText etNoteContent;
    private DateFormat mFormat;

    @Override
    protected void setupViews() {
        mFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        tvCreationTime.setText(mFormat.format(new Date()));
        etNoteContent.requestFocus();
        getDialog().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
    }

    @Override
    protected int attachLayout() {
        return R.layout.todo_dialog_edit;
    }

    @Override
    protected void postData() {
        Note note = new Note();
        note.setCreateTime(mFormat.format(new Date()));
        note.setIsDone(false);
        note.setContent(etNoteContent.getText().toString().trim());
        note.setPriority(10);
        ((TodoListActivity) getActivity()).updateList(note);
    }

    @OnClick({R.id.btnBack, R.id.btnDone})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btnBack:
                dismiss();
                break;
            case R.id.btnDone:
                postData();
                dismiss();
                break;
        }
    }
}
