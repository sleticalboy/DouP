package com.sleticalboy.annotation;

import android.app.Activity;
import android.app.Dialog;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public final class ButterKnife {

    private final static Class[] ON_CLICK = {View.OnClickListener.class};
    private final static Class[] ON_LONG_CLICK = {View.OnLongClickListener.class};
    private final static Class[] ON_ITEM_CLICK = {AdapterView.OnItemClickListener.class};

    public static void bind(@NonNull Activity host) {
        // called in a activity
        bind(host, host.getWindow().getDecorView());
    }

    public static void bind(@NonNull Fragment host) {
        final View root = host.getView();
        if (root == null) {
            throw new IllegalStateException("Bind after #onCreateView() or when #onViewCreated");
        }
        // called in a fragment
        bind(host, root);
    }

    public static void bind(@NonNull Dialog host) {
        // called in a dialog
        bind(host, host.getWindow().getDecorView());
    }

    public static void bind(@NonNull Object host, @NonNull View root) {
        // called in a ViewHolder or somewhere
        try {
            bindViews(host.getClass().getDeclaredFields(), host, root);
            bindClicks(host.getClass().getDeclaredMethods(), host, root);
        } catch (Throwable e) {
            Log.w("ButterKnife", "bind() error with host = " + host, e);
        }
    }

    private static void bindViews(final Field[] fields, final Object host, final View root)
            throws IllegalAccessException {
        for (final Field field : fields) {
            final BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null && bindView.value() != -1) {
                field.setAccessible(true);
                field.set(host, viewOf(root, bindView.value()));
            }
        }
    }

    private static void bindClicks(final Method[] methods, final Object host, final View root) {
        if (host == null) {
            throw new NullPointerException("bindViews: the host is null.");
        }
        for (final Method method : methods) {
            final OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null && onClick.value().length != 0) {
                for (final int id : onClick.value()) {
                    setOnClickListener(method, host, viewOf(root, id), onClick);
                }
            }
            final OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
            if (onLongClick != null && onLongClick.value().length != 0) {
                for (final int id : onLongClick.value()) {
                    setOnClickListener(method, host, viewOf(root, id), onLongClick);
                }
            }
            final OnItemClick onItemClick = method.getAnnotation(OnItemClick.class);
            if (onItemClick != null && onItemClick.value() != -1) {
                setOnClickListener(method, host, viewOf(root, onItemClick.value()), onItemClick);
            }
        }
    }

    private static void setOnClickListener(Method action, final Object host, final View target,
                                           final Annotation a) {
        if (target == null || host == null) {
            return;
        }
        try {
            action.setAccessible(true);

            final Object listener = Proxy.newProxyInstance(target.getContext().getClassLoader(),
                    getInterfaces(a), (proxy, method, args) -> {
                        if ("onClick".equals(method.getName())) {
                            if (args == null || args.length != 1) {
                                throw new IllegalArgumentException("method " + method.getName()
                                        + " only can have one argument.");
                            }
                            Log.d("ButterKnife", "onClick() action = " + action);
                            action.invoke(host, /*view*/args[0]);
                        } else if ("onLongClick".equals(method.getName())) {
                            if (args == null || args.length != 1) {
                                throw new IllegalArgumentException("method " + method.getName()
                                        + " only can have one argument.");
                            }
                            // action 方法的返回值类型需要和接口方法返回值类型保持一致,
                            // 否则方法逻辑不会被执行
                            // final Object value = action.invoke(host, args[0]);
                            Log.d("ButterKnife", "onLongClick() action " + action);
                            return action.invoke(host, /*view*/args[0]);
                        } else if ("onItemClick".equals(method.getName())) {
                            // void onItemClick(AdapterView<?> parent, View view, int position, long id);
                            action.invoke(host, /*position*/args[0]);
                        }
                        return null;
                    }
            );
            setListener(target, listener);
        } catch (Throwable e) {
            Log.w("ButterKnife", "setOnClickListener() error method = " + action
                    + ", targetId = " + target.getId(), e);
        }
    }

    private static void setListener(final View target, final Object listener) throws Exception {
        final Class<? extends View> clazz = target.getClass();
        Method method = null;
        if (listener instanceof View.OnClickListener) {
            target.setOnClickListener(((View.OnClickListener) listener));
            method = clazz.getMethod("setOnClickListener", ON_CLICK);
        } else if (listener instanceof View.OnLongClickListener) {
            target.setOnLongClickListener(((View.OnLongClickListener) listener));
            method = clazz.getMethod("setOnLongClickListener", ON_LONG_CLICK);
        } else if (listener instanceof AdapterView.OnItemClickListener) {
            method = clazz.getMethod("onItemClickListener", ON_ITEM_CLICK);
        }
        if (method != null) {
            method.invoke(target, listener);
        }
    }

    private static Class<?>[] getInterfaces(final Annotation annotation) {
        if (annotation instanceof OnClick) {
            return ON_CLICK;
        } else if (annotation instanceof OnLongClick) {
            return ON_LONG_CLICK;
        } else if (annotation instanceof OnItemClick) {
            return ON_ITEM_CLICK;
        }
        return new Class[0];
    }

    private static View viewOf(final View root, final int id) {
        final View value = root == null ? null : root.findViewById(id);
        if (value == null) {
            Log.w("ButterKnife", "valueOf return null, root = " + root + ", id = " + id);
        }
        return value;
    }

}
