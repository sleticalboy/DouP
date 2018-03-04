package com.sleticalboy.doup.module.todo;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.model.todo.Note;

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
public class TodoDialog extends DialogFragment {

    private static final String TAG = "TodoDialog";

    public static final int NEW_NOTE = 0x001;

    Unbinder unbinder;
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
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        final Window window = getDialog().getWindow();
        assert window != null;
        window.requestFeature(Window.FEATURE_NO_TITLE);
        View rootView = inflater.inflate(
                R.layout.todo_dialog_edit, window.findViewById(android.R.id.content), false);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
        unbinder = ButterKnife.bind(this, rootView);
        setupViews();
        return rootView;
    }

    private void setupViews() {
        mFormat = DateFormat.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.MEDIUM);
        tvCreationTime.setText(mFormat.format(new Date()));
    }

    @Override
    public void onStart() {
        super.onStart();
        final Window window = getDialog().getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.dimAmount = 0.0f;
        params.alpha = 1.0f;
        window.setAttributes(params);

    }

    public void showIME() {
        etNoteContent.setFocusable(true);
        etNoteContent.setFocusableInTouchMode(true);
        etNoteContent.requestFocus();
        getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
        showIME();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
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

    private void postData() {
        Note note = new Note();
        note.setCreateTime(mFormat.format(new Date()));
        note.setIsDone(false);
        note.setContent(etNoteContent.getText().toString().trim());
        ((TodoListActivity) getActivity()).updateList(note);
    }
}
