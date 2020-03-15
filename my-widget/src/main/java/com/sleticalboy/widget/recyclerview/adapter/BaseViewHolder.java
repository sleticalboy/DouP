package com.sleticalboy.widget.recyclerview.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Field;

/**
 * M为这个itemView对应的model。
 * 使用RecyclerArrayAdapter就一定要用这个ViewHolder。
 * 这个ViewHolder将ItemView与Adapter解耦。
 * 推荐子类继承第二个构造函数。并将子类的构造函数设为一个ViewGroup parent。
 * 然后这个ViewHolder就完全独立。adapter在new的时候只需将parentView传进来。View的生成与管理由ViewHolder执行。
 * 实现setData来实现UI修改。Adapter会在onCreateViewHolder里自动调用。
 * <p>
 * 在一些特殊情况下，只能在setData里设置监听。
 *
 * @param <M>
 * @author xxx
 */
public abstract class BaseViewHolder<M> extends RecyclerView.ViewHolder {
    
    private RecyclerView mOwnerRecyclerView;
    
    public BaseViewHolder(View itemView) {
        super(itemView);
    }
    
    public BaseViewHolder(ViewGroup parent, @LayoutRes int res) {
        super(LayoutInflater.from(parent.getContext()).inflate(res, parent, false));
    }
    
    /**
     * ViewHolder 的数据
     *
     * @param data
     */
    public abstract void bindData(M data);
    
    protected <T extends View> T obtainView(@IdRes int id) {
        return itemView.findViewById(id);
    }
    
    protected Context getContext() {
        return itemView.getContext();
    }
    
    protected int getDataPosition() {
        if (mOwnerRecyclerView == null) {
            try {
                final Field rv = RecyclerView.ViewHolder.class.getDeclaredField("mOwnerRecyclerView");
                rv.setAccessible(true);
                mOwnerRecyclerView = (RecyclerView) rv.get(this);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                mOwnerRecyclerView = null;
            }
        }
        RecyclerView.Adapter adapter = null;
        if (mOwnerRecyclerView != null) {
            adapter = mOwnerRecyclerView.getAdapter();
        }
        if (adapter instanceof BaseRecyclerAdapter) {
            return getAdapterPosition() - ((BaseRecyclerAdapter) adapter).getHeaderCount();
        }
        return getAdapterPosition();
    }
}