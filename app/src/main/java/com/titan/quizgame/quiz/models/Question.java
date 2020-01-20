package com.titan.quizgame.quiz.models;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Ignore;
import androidx.room.Index;
import androidx.room.PrimaryKey;


@Entity(tableName = "questions",
        foreignKeys = @ForeignKey(entity = Category.class,
                                            parentColumns = "id",
                                            childColumns = "categoryId",
                                            onDelete = ForeignKey.CASCADE),
        indices = {@Index("categoryId")})
public class Question implements Parcelable {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @NonNull
    @ColumnInfo(name = "question")
    private String question;

    @NonNull
    @ColumnInfo(name = "option1")
    private String option1;

    @NonNull
    @ColumnInfo(name = "option2")
    private String option2;

    @NonNull
    @ColumnInfo(name = "option3")
    private String option3;

    @NonNull
    @ColumnInfo(name = "answer")
    private int answerNr;


    @NonNull
    @ColumnInfo(name = "difficulty")
    private String difficulty;


    @ColumnInfo(name = "categoryId")
    private int categoryId;


    public Question(String question, String option1, String option2, String option3, int answerNr, String difficulty, int categoryId) {
        this.question = question;
        this.option1 = option1;
        this.option2 = option2;
        this.option3 = option3;
        this.answerNr = answerNr;
        this.difficulty = difficulty;
        this.categoryId = categoryId;
    }

    @Ignore
    protected Question(Parcel in) {
        id = in.readInt();
        question = in.readString();
        option1 = in.readString();
        option2 = in.readString();
        option3 = in.readString();
        answerNr = in.readInt();
        difficulty = in.readString();
        categoryId = in.readInt();
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

    public String getQuestion() {
        return question;
    }

    public String getOption1() {
        return option1;
    }

    public String getOption2() {
        return option2;
    }

    public String getOption3() {
        return option3;
    }

    public int getAnswerNr() {
        return answerNr;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public int getCategoryId() {
        return categoryId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(question);
        dest.writeString(option1);
        dest.writeString(option2);
        dest.writeString(option3);
        dest.writeInt(answerNr);
        dest.writeString(difficulty);
        dest.writeInt(categoryId);
    }
}
