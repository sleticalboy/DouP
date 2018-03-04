package com.sleticalboy.doup.module.todo;

import com.sleticalboy.doup.bean.todo.Note;
import com.sleticalboy.doup.bean.todo.NoteModel;

/**
 * Created on 18-2-19.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
public class TodoPresenter implements ITodoContract.Presenter {

    private NoteModel mModel;

    public TodoPresenter(ITodoContract.View view) {
        mModel = new NoteModel();
    }

    @Override
    public void addNote(Note note) {
        mModel.addNote(note);
    }

    @Override
    public void removeNote(Note note) {
        mModel.removeNote(note);
    }

    @Override
    public void updateNote(Note note) {
        mModel.updateNote(note);
    }
}
