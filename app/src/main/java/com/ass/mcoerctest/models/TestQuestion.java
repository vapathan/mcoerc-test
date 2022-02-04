package com.ass.mcoerctest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class TestQuestion implements Parcelable {
    @PrimaryKey
    int id;
    private int testId;
    private int questionId;
    private int subjectCode;
    private boolean isAttempted;
    private String selectedOption;
    @Ignore
    private Question question;

    protected TestQuestion(Parcel in) {
        id = in.readInt();
        testId = in.readInt();
        questionId = in.readInt();
        subjectCode=in.readInt();
        isAttempted = in.readByte() != 0;
        selectedOption = in.readString();
        question = in.readParcelable(Question.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(testId);
        dest.writeInt(questionId);
        dest.writeInt(subjectCode);
        dest.writeByte((byte) (isAttempted ? 1 : 0));
        dest.writeString(selectedOption);
        dest.writeParcelable(question, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TestQuestion> CREATOR = new Creator<TestQuestion>() {
        @Override
        public TestQuestion createFromParcel(Parcel in) {
            return new TestQuestion(in);
        }

        @Override
        public TestQuestion[] newArray(int size) {
            return new TestQuestion[size];
        }
    };

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }

    public TestQuestion() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getQuestionId() {
        return questionId;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public void setQuestionId(int questionId) {
        this.questionId = questionId;
    }

    public int getSubjectCode() {
        return subjectCode;
    }

    public void setSubjectCode(int subjectCode) {
        this.subjectCode = subjectCode;
    }
}
