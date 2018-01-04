package com.sleticalboy.doup.util;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

/**
 * Created by Android Studio.
 * Date: 1/3/18.
 *
 * @author sleticalboy
 */
public class RxBus {

    private ConcurrentHashMap<Object, List<Subject>> mSubjectContainer = new ConcurrentHashMap<>();

    private RxBus() {
    }

    @NonNull
    public static RxBus getBus() {
        return Holder.sInstance;
    }

    @NonNull
    public <T> Observable<T> register(@NonNull Class<T> clazz) {
        return register(clazz.getName());
    }

    @NonNull
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjectList = mSubjectContainer.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            mSubjectContainer.put(tag, subjectList);
        }

        Subject<T> subject = PublishSubject.create();
        subjectList.add(subject);

        return subject;
    }

    public <T> void unregister(@NonNull Class<T> clazz, @NonNull Observable observable) {
        unregister(clazz.getName(), observable);
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjectList = mSubjectContainer.get(tag);
        if (subjectList != null) {
            subjectList.remove(observable);
            if (subjectList.isEmpty()) {
                mSubjectContainer.remove(tag);
            }
        }
    }

    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = mSubjectContainer.get(tag);
        if (subjectList != null && !subjectList.isEmpty()) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    private static class Holder {
        private static RxBus sInstance = new RxBus();
    }
}
