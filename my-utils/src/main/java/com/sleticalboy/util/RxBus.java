package com.sleticalboy.util;

import androidx.annotation.NonNull;

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

    private final ConcurrentHashMap<Object, List<Subject>> mSubjectBus = new ConcurrentHashMap<>();

    private RxBus() {
    }

    @NonNull
    public static RxBus getBus() {
        return Holder.RX_BUS;
    }

    @NonNull
    public <T> Observable<T> register(@NonNull Class<T> clazz) {
        return register(clazz.getName());
    }

    @NonNull
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjectList = mSubjectBus.get(tag);
        if (subjectList == null) {
            subjectList = new ArrayList<>();
            mSubjectBus.put(tag, subjectList);
        }

        Subject<T> subject = PublishSubject.create();
        subjectList.add(subject);

        return subject;
    }

    public <T> void unregister(@NonNull Class<T> clazz, @NonNull Observable observable) {
        unregister(clazz.getName(), observable);
    }

    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjectList = mSubjectBus.get(tag);
        if (subjectList != null) {
            subjectList.remove(observable);
            if (subjectList.isEmpty()) {
                mSubjectBus.remove(tag);
            }
        }
    }

    /**
     * post 一条消息
     *
     * @param content 消息的主体内容
     */
    public void post(@NonNull Object content) {
        post(content.getClass().getName(), content);
    }

    /**
     * post 一条消息
     *
     * @param tag     消息的 tag
     * @param content 消息的主体内容
     */
    public void post(@NonNull Object tag, @NonNull Object content) {
        List<Subject> subjectList = mSubjectBus.get(tag);
        if (subjectList != null && !subjectList.isEmpty()) {
            for (Subject subject : subjectList) {
                subject.onNext(content);
            }
        }
    }

    private static final class Holder {
        private static final RxBus RX_BUS = new RxBus();
    }
}
