package com.sleticalboy.doup.bean.todo;

import android.os.Parcel;
import android.os.Parcelable;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
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
    private Boolean isDone = false;
    @Property
    private Integer priority; // 优先级，数字越小，优先级越高，但是不能是负数

    protected Note(Parcel in) {
        createTime = in.readString();
        content = in.readString();
        isDone = in.readByte() != 0;
        priority = in.readInt();
    }

    @Generated(hash = 1645647354)
    public Note(String createTime, String content, Boolean isDone, Integer priority) {
        this.createTime = createTime;
        this.content = content;
        this.isDone = isDone;
        this.priority = priority;
    }

    @Generated(hash = 1272611929)
    public Note() {
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

    public Boolean getIsDone() {
        return this.isDone;
    }

    public void setIsDone(Boolean isDone) {
        this.isDone = isDone;
    }

    public Integer getPriority() {
        return this.priority;
    }

    public void setPriority(Integer priority) {
        if (priority < 0) {
            throw new IllegalArgumentException("priority can not be negative" + priority);
        }
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Note{" +
                ", createTime='" + createTime + '\'' +
                ", content='" + content + '\'' +
                ", isDone=" + isDone +
                ", priority=" + priority +
                '}';
    }
}
