package com.sleticalboy.doup.module.todo;

import android.content.Context;

import com.sleticalboy.doup.bean.todo.Note;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by Android Studio.
 *
 * @author sleticalboy
 */
public interface ITodoContract {

    interface View/* extends IBaseView*/ {

        void showDialog();

        void setAdapter(RecyclerArrayAdapter adapter);

        Context getContext();
    }

    interface Presenter {

        void addNote(Note note);

        void removeNote(Note note);

        void updateNote(Note note);
    }
}
