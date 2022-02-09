package com.ass.mcoerctest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.Date;


@Entity
public class Test implements Parcelable {
    @PrimaryKey
    private int id;
    private String title;
    private int examId;
    private String courseCode;
    private int totalQuestions;
    private String timeDuration;
    private boolean isActive;
    private String courseName;
    private String examName;
    private String date;
    private boolean isSubmitted;

    public Test() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getExamId() {
        return examId;
    }

    public void setExamId(int examId) {
        this.examId = examId;
    }

    public String getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(String courseCode) {
        this.courseCode = courseCode;
    }

    public int getTotalQuestions() {
        return totalQuestions;
    }

    public void setTotalQuestions(int totalQuestions) {
        this.totalQuestions = totalQuestions;
    }

    public String getTimeDuration() {
        return timeDuration;
    }

    public void setTimeDuration(String timeDuration) {
        this.timeDuration = timeDuration;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getExamName() {
        return examName;
    }

    public void setExamName(String examName) {
        this.examName = examName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public boolean getIsSubmitted() {
        return isSubmitted;
    }

    public void setIsSubmitted(boolean isSubmitted) {
        this.isSubmitted = isSubmitted;
    }

    protected Test(Parcel in) {
        id = in.readInt();
        title = in.readString();
        examId = in.readInt();
        courseCode = in.readString();
        totalQuestions = in.readInt();
        timeDuration = in.readString();
        isActive = in.readByte() != 0;
        courseName = in.readString();
        examName = in.readString();
        date = in.readString();
        isSubmitted = in.readByte() != 0;
    }

    public static final Creator<Test> CREATOR = new Creator<Test>() {
        @Override
        public Test createFromParcel(Parcel in) {
            return new Test(in);
        }

        @Override
        public Test[] newArray(int size) {
            return new Test[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(title);
        dest.writeInt(examId);
        dest.writeString(courseCode);
        dest.writeInt(totalQuestions);
        dest.writeString(timeDuration);
        dest.writeByte((byte) (isActive ? 1 : 0));
        dest.writeString(courseName);
        dest.writeString(examName);
        dest.writeString(date);
        dest.writeByte((byte) (isSubmitted ? 1 : 0));
    }

    public int getCurrentStatus() {
        return 0;
    }
}
