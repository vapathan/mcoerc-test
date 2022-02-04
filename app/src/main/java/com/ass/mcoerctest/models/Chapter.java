package com.ass.mcoerctest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Chapter implements Parcelable {
    @PrimaryKey
    private int id;
    private int srNum;
    private String name;
    private int subjectCode;
    @Ignore
    private int questionCount;

    protected Chapter(Parcel in) {
        id = in.readInt();
        srNum = in.readInt();
        name = in.readString();
        subjectCode = in.readInt();
        questionCount = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(srNum);
        dest.writeString(name);
        dest.writeInt(subjectCode);
        dest.writeInt(questionCount);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Chapter> CREATOR = new Creator<Chapter>() {
        @Override
        public Chapter createFromParcel(Parcel in) {
            return new Chapter(in);
        }

        @Override
        public Chapter[] newArray(int size) {
            return new Chapter[size];
        }
    };

    public int getQuestionCount() {
        return questionCount;
    }

    public void setQuestionCount(int questionCount) {
        this.questionCount = questionCount;
    }

    public Chapter() {
    }

    @Ignore
    public Chapter(int id, int srNum, String name, int subjectCode) {
        this.id = id;
        this.srNum = srNum;
        this.name = name;
        this.subjectCode = subjectCode;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSrNum() {
        return srNum;
    }

    public void setSrNum(int srNum) {
        this.srNum = srNum;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }


}
