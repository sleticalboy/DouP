package com.sleticalboy.doup.model.todo;

import com.sleticalboy.doup.DouApp;

import java.util.List;

/**
 * Created on 18-3-3.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
public class NoteModel {

    private final NoteDao mDao;

    public NoteModel() {
        mDao = ((DaoSession) DouApp.getDaoSession()).getNoteDao();
    }

    public void addNote(Note note) {
        mDao.insert(note);
    }

    public void removeNote(Note note) {
        mDao.delete(note);
    }

    public void updateNote(Note note) {
        mDao.update(note);
    }

    public List<Note> getNotes() {
        return mDao.loadAll();
    }
}
