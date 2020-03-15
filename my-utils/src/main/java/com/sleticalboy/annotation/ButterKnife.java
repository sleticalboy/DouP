package com.sleticalboy.annotation;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ButterKnife {

    private final static Class[] HOLDER = {View.OnClickListener.class};

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
                    // invokeWhenClick(method, valueOf(host, id));
                    setOnClickListener(method, valueOf(host, id));
                }
            }
        }
    }

    private static void setOnClickListener(Method action, final View target) {
        if (target == null) {
            return;
        }
        final Object listener = Proxy.newProxyInstance(target.getContext().getClassLoader(),
                HOLDER, (proxy, method, args) -> action.invoke(target, target));
        try {
            View.class.getDeclaredMethod("setOnClickListener", HOLDER[0])
                    .invoke(target, listener);
        } catch (Throwable e) {
            Log.d("ButterKnife", "setOnClickListener() error method = " + action
                    + ", targetId = " + target.getId(), e);
        }
    }

    private static void invokeWhenClick(final Method method, final View target) {
        if (target == null) {
            return;
        }
        target.setOnClickListener(v -> {
            try {
                method.invoke(v, v);
            } catch (Throwable e) {
                Log.d("ButterKnife", "invokeWhenClick() error method = " + method
                        + ", targetId = " + 0, e);
            }
        });
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

}
