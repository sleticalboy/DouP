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

    private final static Class[] CLICK_HOLDER = {View.OnClickListener.class};
    private final static Class[] LONG_CLICK_HOLDER = {View.OnLongClickListener.class};

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

    /*public static void bind(@NonNull Object host) {
        try {
            // when the host is activity, the host is activity too.
            // when the host is fragment, the host is view
            final Object root;
            if (host instanceof Fragment) {
                if ((root = ((Fragment) host).getView()) == null) {
                    throw new IllegalArgumentException("root must be a View object.");
                }
            } else {
                // if (host instanceof Activity || host instanceof Dialog) {}
                root = host;
            }
            bindViews(host.getClass().getDeclaredFields(), host, root);
            bindClicks(host.getClass().getDeclaredMethods(), host, root);
        } catch (Throwable e) {
            Log.w("ButterKnife", "bind() error with host = " + host, e);
        }
    }*/

    private static void bindViews(final Field[] fields, final Object host, final Object root)
            throws IllegalAccessException {
        for (final Field field : fields) {
            final BindView bindView = field.getAnnotation(BindView.class);
            if (bindView != null && bindView.value() != -1) {
                field.setAccessible(true);
                field.set(host, valueOf(root, bindView.value()));
            }
        }
    }

    private static void bindClicks(final Method[] methods, final Object host, final Object root) {
        if (host == null) {
            throw new NullPointerException("bindViews: the host is null.");
        }
        for (final Method method : methods) {
            final OnClick onClick = method.getAnnotation(OnClick.class);
            if (onClick != null && onClick.value().length != 0) {
                for (final int id : onClick.value()) {
                    // invokeWhenClick(method, host, valueOf(root, id), false);
                    setOnClickListener(method, host, valueOf(root, id), false);
                }
            }
            final OnLongClick onLongClick = method.getAnnotation(OnLongClick.class);
            if (onLongClick != null && onLongClick.value().length != 0) {
                for (final int id : onLongClick.value()) {
                    // invokeWhenClick(method, host, valueOf(root, id), true);
                    setOnClickListener(method, host, valueOf(root, id), true);
                }
            }
        }
    }

    private static void setOnClickListener(Method action, final Object host, final View target,
                                           final boolean longClick) {
        if (target == null || host == null) {
            return;
        }
        try {
            action.setAccessible(true);
            final Object listener = Proxy.newProxyInstance(target.getContext().getClassLoader(),
                    longClick ? LONG_CLICK_HOLDER : CLICK_HOLDER, (proxy, method, args) -> {
                        if ("onClick".equals(method.getName())) {
                            if (args == null || args.length != 1) {
                                throw new IllegalArgumentException("method " + method.getName()
                                        + " only can have one argument.");
                            }
                            Log.d("ButterKnife", "onClick() action = " + action);
                            action.invoke(host, args[0]);
                        } else if ("onLongClick".equals(method.getName())) {
                            if (args == null || args.length != 1) {
                                throw new IllegalArgumentException("method " + method.getName()
                                        + " only can have one argument.");
                            }
                            // action 方法的返回值类型需要和接口方法返回值类型保持一致,
                            // 否则方法逻辑不会被执行
                            // final Object value = action.invoke(host, args[0]);
                            Log.d("ButterKnife", "onLongClick() action " + action);
                            return action.invoke(host, args[0]);
                        }
                        return null;
                    }
            );
            if (longClick) {
                target.setOnLongClickListener(((View.OnLongClickListener) listener));
                return;
            }
            target.setOnClickListener(((View.OnClickListener) listener));
        } catch (Throwable e) {
            Log.w("ButterKnife", "setOnClickListener() error method = " + action
                    + ", targetId = " + target.getId(), e);
        }
    }

    private static void invokeWhenClick(final Method method, final Object host, final View target,
                                        final boolean longClick) {
        if (target == null) {
            return;
        }
        if (longClick) {
            target.setOnLongClickListener(v -> {
                try {
                    method.invoke(host, target);
                } catch (Throwable e) {
                    Log.w("ButterKnife", "invokeWhenClick() error method = " + method
                            + ", targetId = " + 0, e);
                    return false;
                }
                return true;
            });
            return;
        }
        target.setOnClickListener(v -> {
            try {
                method.invoke(host, target);
            } catch (Throwable e) {
                Log.w("ButterKnife", "invokeWhenClick() error method = " + method
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
