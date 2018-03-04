package com.sleticalboy.doup.model.todo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Property;

import java.io.Serializable;

/**
 * Created on 18-3-3.
 *
 * @author sleticalboy
 * @version 1.0
 * @description
 */
@Entity
public class Note implements Parcelable, Serializable {

    public static final Creator<Note> CREATOR = new Creator<Note>() {
        @Override
        public Note createFromParcel(Parcel in) {
            return new Note(in);
        }

        @Override
        public Note[] newArray(int size) {
            return new Note[size];
        }
    };

    private static final long serialVersionUID = -3055753026905017167L;

    @Property
    private String createTime;
    @Property
    private String content;
    @Property
    private boolean isDone = false;
    @Property
    private int priority; // 优先级，数字越小，优先级越高，但是不能是负数

    @Generated(hash = 179265577)
    public Note(String createTime, String content, boolean isDone, int priority) {
        this.createTime = createTime;
        this.content = content;
        this.isDone = isDone;
        this.priority = priority;
    }

    @Generated(hash = 1272611929)
    public Note() {
    }

    protected Note(Parcel in) {
        createTime = in.readString();
        content = in.readString();
        isDone = in.readByte() != 0;
        priority = in.readInt();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(createTime);
        dest.writeString(content);
        dest.writeByte((byte) (isDone ? 1 : 0));
        dest.writeInt(priority);
    }

    public String getCreateTime() {
        return this.createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getContent() {
        return this.content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(boolean isDone) {
        this.isDone = isDone;
    }

    public int getPriority() {
        return this.priority;
    }

    public void setPriority(int priority) {
        if (priority < 0) {
            throw new IllegalArgumentException("priority can not be negative" + priority);
        }
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Note{" +
                "createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", isDone=" + isDone +
                ", priority=" + priority +
                '}';
    }
}
