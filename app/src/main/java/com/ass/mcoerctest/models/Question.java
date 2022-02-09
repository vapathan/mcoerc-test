package com.ass.mcoerctest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Question implements Parcelable {
    @PrimaryKey
    private int id;
    private int testId;
    private String title;
    private String optA;
    private String optB;
    private String optC;
    private String optD;
    private String correctOption;
    private String selectedOption;
    private boolean isAttempted;
    private String explanation;
    private int courseCode;
    private int unitId;
    private boolean isVisited;

    protected Question(Parcel in) {
        id = in.readInt();
        testId = in.readInt();
        title = in.readString();
        optA = in.readString();
        optB = in.readString();
        optC = in.readString();
        optD = in.readString();
        correctOption = in.readString();
        selectedOption = in.readString();
        isAttempted = in.readByte()!=0;
        explanation = in.readString();
        courseCode = in.readInt();
        unitId = in.readInt();
        isVisited = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(testId);
        dest.writeString(title);
        dest.writeString(optA);
        dest.writeString(optB);
        dest.writeString(optC);
        dest.writeString(optD);
        dest.writeString(correctOption);
        dest.writeString(selectedOption);
        dest.writeByte((byte) (isAttempted ? 1 : 0));
        dest.writeString(explanation);
        dest.writeInt(courseCode);
        dest.writeInt(unitId);
        dest.writeByte((byte) (isVisited ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel in) {
            return new Question(in);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };

    public boolean isVisited() {
        return isVisited;
    }

    public void setVisited(boolean visited) {
        isVisited = visited;
    }

    public Question() {
    }

    @Ignore
    public Question(int id, String title, String optA, String optB, String optC, String optD, String correctOption, String explanation, int subjectCode, int chapterId, boolean isVisited) {
        this.id = id;
        this.title = title;
        this.optA = optA;
        this.optB = optB;
        this.optC = optC;
        this.optD = optD;
        this.correctOption = correctOption;
        this.explanation = explanation;
        this.courseCode = subjectCode;
        this.unitId = chapterId;
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

    public String getTitle(boolean isFormatted) {
        if (isFormatted) {
            return generateHtml(title);
        }
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getOptA(boolean isFormatted) {
        if (isFormatted) {
            return generateHtml(optA);
        }
        return optA;
    }

    public void setOptA(String optA) {
        this.optA = optA;
    }

    public String getOptB(boolean isFormatted) {
        if (isFormatted) {
            return generateHtml(optB);
        }
        return optB;
    }

    public void setOptB(String optB) {
        this.optB = optB;
    }

    public String getOptC(boolean isFormatted) {
        if (isFormatted) {
            return generateHtml(optC);
        }
        return optC;
    }

    public void setOptC(String optC) {
        this.optC = optC;
    }

    public String getOptD(boolean isFormatted) {
        if (isFormatted) {
            return generateHtml(optD);
        }
        return optD;
    }

    public void setOptD(String optD) {
        this.optD = optD;
    }

    public String getCorrectOption() {
        return correctOption;
    }

    public void setCorrectOption(String correctOption) {
        this.correctOption = correctOption;
    }

    public String getExplanation(boolean isFormatted) {

        if (isFormatted) {
            return generateHtml(explanation);
        }
        return explanation;
    }

    public void setExplanation(String explanation) {
        this.explanation = explanation;
    }

    public int getCourseCode() {
        return courseCode;
    }

    public void setCourseCode(int courseCode) {
        this.courseCode = courseCode;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    public String getTitle() {
        return title;
    }

    public String getOptA() {
        return optA;
    }

    public String getOptB() {
        return optB;
    }

    public String getOptC() {
        return optC;
    }

    public String getOptD() {
        return optD;
    }

    public String getExplanation() {
        return explanation;
    }

    public String getSelectedOption() {
        return selectedOption;
    }

    public void setSelectedOption(String selectedOption) {
        this.selectedOption = selectedOption;
    }

    public boolean isAttempted() {
        return isAttempted;
    }

    public void setAttempted(boolean attempted) {
        isAttempted = attempted;
    }

    private String generateHtml(String text) {
        String html = "<html><body  style=\"text-align:left;\">" + text + "</body></html>";
        return html;
    }

    public String getCorrectAnswer(String correctOption) {
        switch (correctOption) {
            case "A":
                return generateHtml(optA);
            case "B":
                return generateHtml(optB);
            case "C":
                return generateHtml(optC);
            case "D":
                return generateHtml(optD);
        }
        return "";
    }
}
