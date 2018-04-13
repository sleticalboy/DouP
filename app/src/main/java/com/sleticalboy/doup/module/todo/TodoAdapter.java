package com.sleticalboy.doup.module.todo;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sleticalboy.doup.R;
import com.sleticalboy.doup.bean.todo.Note;
import com.sleticalboy.widget.recyclerview.adapter.BaseViewHolder;
import com.sleticalboy.widget.recyclerview.adapter.RecyclerArrayAdapter;

import java.util.List;

/**
 * Created on 18-3-3.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
public class TodoAdapter extends RecyclerArrayAdapter<Note> {

    public TodoAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new NoteHolder(parent, R.layout.todo_recycle_item_note);
    }

    public void setDataSet(List<Note> dataSet) {
        mDataList = dataSet;
        notifyDataSetChanged();
    }

    static class NoteHolder extends BaseViewHolder<Note> {

        TextView tvContent;
        TextView tvCreationTime;


        public NoteHolder(ViewGroup parent, int res) {
            super(parent, res);
            tvContent = (TextView) itemView.findViewById(R.id.tvContent);
            tvCreationTime = (TextView) itemView.findViewById(R.id.tvCreationTime);
        }

        @Override
        public void setData(Note data) {
            tvContent.setText(data.getContent());
            tvCreationTime.setText(data.getCreateTime());
        }
    }
}
