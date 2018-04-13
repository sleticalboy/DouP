package com.sleticalboy.doup.bean.todo;

import com.sleticalboy.doup.db.DBController;
import com.sleticalboy.doup.bean.weather.DaoSession;

import org.greenrobot.greendao.query.QueryBuilder;

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
    private final QueryBuilder<Note> mQueryBuilder;

    public NoteModel() {
        mDao = ((DaoSession) DBController.getDaoSession()).getNoteDao();
        mQueryBuilder = mDao.queryBuilder();
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

    public Note getByCreationTime(String creationTime) {
        return mQueryBuilder.where(NoteDao.Properties.CreateTime.eq(creationTime)).build().unique();
    }

    public List<Note> getNotes() {
        return mDao.loadAll();
    }
}
