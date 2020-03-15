package com.sleticalboy.annotation;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public final class ButterKnife {

    public static void bind(@NonNull Object obj) {
        try {
            // when the obj is activity, the obj is activity too.
            // when the obj is fragment, the obj is view
            final Object host;
            if (obj instanceof Fragment) {
                if ((host = ((Fragment) obj).getView()) == null) {
                    throw new IllegalArgumentException("root must be a View object.");
                }
            } else {
                // if (obj instanceof Activity || obj instanceof Dialog) {}
                host = obj;
            }
            bindViews(obj.getClass().getDeclaredFields(), host);
            bindClicks(obj.getClass().getDeclaredMethods(), host);
        } catch (Throwable e) {
            Log.d("ButterKnife", "bind() error obj = " + obj, e);
        }
    }

    private static void bindViews(final Field[] fields, final Object host)
            throws IllegalAccessException {
        for (final Field field : fields) {
            final BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null && bindView.value() != -1) {
                field.setAccessible(true);
                field.set(host, valueOf(host, bindView.value()));
            }
        }
    }

    private static void bindClicks(final Method[] methods, Object host) {
        if (host == null) {
            throw new NullPointerException("bindViews: the host is null.");
        }
        for (final Method method : methods) {
            final OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null && onClick.value().length != 0) {
                for (final int id : onClick.value()) {
                    invokeWhenClick(method, valueOf(host, id));
                }
            }
        }
    }

    private static void invokeWhenClick(final Method method, final View target) {
        if (target == null) {
            return;
        }
        target.setOnClickListener(new OnClickWrapper(method));
        new InvocationHandler() {
            @Override
            public Object invoke(final Object proxy, final Method method, final Object[] args) throws Throwable {
                return null;
            }
        };
    }

    private static View valueOf(Object host, int id) {
        if (host instanceof View) {
            return ((View) host).findViewById(id);
        }
        if (host instanceof Activity) {
            return ((Activity) host).findViewById(id);
        }
        if (host instanceof Dialog) {
            return ((Dialog) host).findViewById(id);
        }
        Log.w("ButterKnife", "valueOf return null, host = " + host + ", id = " + id);
        return null;
    }

    private static final class OnClickWrapper implements View.OnClickListener {

        final Method mOnClick;

        OnClickWrapper(final Method onClick) {
            mOnClick = onClick;
        }

        @Override
        public void onClick(final View v) {
            try {
                mOnClick.invoke(v, v);
            } catch (Throwable e) {
                Log.d("ButterKnife", "invokeWhenClick() error method = " + mOnClick
                        + ", targetId = " + 0, e);
            }
        }
    }
}
