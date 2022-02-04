package com.ass.mcoerctest.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;
@Entity
public class ScoreCard implements Parcelable {
    @PrimaryKey(autoGenerate = true)
    private int id;
    private int testId;
    private int studentId;
    private String testTitle;
    @Ignore
    private String studentName;
    private int phyQuestions;
    private int phyAttempted;
    private int phyCorrect;
    private int chemQuestions;
    private int chemAttempted;
    private int chemCorrect;
    private int mathQuestions;
    private int mathAttempted;
    private int mathCorrect;

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public int getStudentId() {
        return studentId;
    }

    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

    public int getTestId() {
        return testId;
    }

    public void setTestId(int testId) {
        this.testId = testId;
    }

    public int getPhyQuestions() {
        return phyQuestions;
    }

    public void setPhyQuestions(int phyQuestions) {
        this.phyQuestions = phyQuestions;
    }

    public int getPhyAttempted() {
        return phyAttempted;
    }

    public void setPhyAttempted(int phyAttempted) {
        this.phyAttempted = phyAttempted;
    }

    public int getPhyCorrect() {
        return phyCorrect;
    }

    public void setPhyCorrect(int phyCorrect) {
        this.phyCorrect = phyCorrect;
    }

    public int getChemQuestions() {
        return chemQuestions;
    }

    public void setChemQuestions(int chemQuestions) {
        this.chemQuestions = chemQuestions;
    }

    public int getChemAttempted() {
        return chemAttempted;
    }

    public void setChemAttempted(int chemAttempted) {
        this.chemAttempted = chemAttempted;
    }

    public int getChemCorrect() {
        return chemCorrect;
    }

    public void setChemCorrect(int chemCorrect) {
        this.chemCorrect = chemCorrect;
    }

    public int getMathQuestions() {
        return mathQuestions;
    }

    public void setMathQuestions(int mathQuestions) {
        this.mathQuestions = mathQuestions;
    }

    public int getMathAttempted() {
        return mathAttempted;
    }

    public void setMathAttempted(int mathAttempted) {
        this.mathAttempted = mathAttempted;
    }

    public int getMathCorrect() {
        return mathCorrect;
    }

    public void setMathCorrect(int mathCorrect) {
        this.mathCorrect = mathCorrect;
    }

    public ScoreCard() {
    }


    protected ScoreCard(Parcel in) {
        id=in.readInt();
        testId = in.readInt();
        studentId = in.readInt();
        testTitle = in.readString();
        studentName = in.readString();
        phyQuestions = in.readInt();
        phyAttempted = in.readInt();
        phyCorrect = in.readInt();
        chemQuestions = in.readInt();
        chemAttempted = in.readInt();
        chemCorrect = in.readInt();
        mathQuestions = in.readInt();
        mathAttempted = in.readInt();
        mathCorrect = in.readInt();
    }

    public static final Creator<ScoreCard> CREATOR = new Creator<ScoreCard>() {
        @Override
        public ScoreCard createFromParcel(Parcel in) {
            return new ScoreCard(in);
        }

        @Override
        public ScoreCard[] newArray(int size) {
            return new ScoreCard[size];
        }
    };

    public String getTestTitle() {
        return testTitle;
    }

    public void setTestTitle(String testTitle) {
        this.testTitle = testTitle;
    }



    public int getQuestionsAttempted() {
        return phyQuestions;
    }

    public void setQuestionsAttempted(int questionsAttempted) {
        this.phyQuestions = questionsAttempted;
    }

    public void prepareScoreCard(Test test, List<TestQuestion> testQuestionList) {
        testId = test.getId();
        testTitle = test.getTitle();
        phyQuestions = 0;
        chemQuestions = 0;
        mathQuestions = 0;

        for (int i = 0; i < testQuestionList.size(); i++) {
            TestQuestion testQuestion = testQuestionList.get(i);
            if (testQuestion.isAttempted()) {

                if (testQuestion.getSelectedOption().equals(testQuestion.getQuestion().getCorrectOption())) {
                    if(testQuestion.getSubjectCode()==1){
                        phyAttempted++;
                        phyCorrect++;
                    }else if(testQuestion.getSubjectCode()==2){
                        chemAttempted++;
                        chemCorrect++;
                    }else{
                        mathAttempted++;
                        mathCorrect++;
                    }
                } else {
                    if(testQuestion.getSubjectCode()==1){
                        phyAttempted++;
                    }else if(testQuestion.getSubjectCode()==2){
                        chemAttempted++;
                    }else{
                        mathAttempted++;
                    }
                }
            }
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(id);
        parcel.writeInt(testId);
        parcel.writeInt(studentId);
        parcel.writeString(testTitle);
        parcel.writeString(studentName);
        parcel.writeInt(phyQuestions);
        parcel.writeInt(phyAttempted);
        parcel.writeInt(phyCorrect);
        parcel.writeInt(chemQuestions);
        parcel.writeInt(chemAttempted);
        parcel.writeInt(chemCorrect);
        parcel.writeInt(mathQuestions);
        parcel.writeInt(mathAttempted);
        parcel.writeInt(mathCorrect);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}