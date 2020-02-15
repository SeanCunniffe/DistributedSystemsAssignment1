package ct414;

import java.io.Serializable;

public class MCQ implements Question, Serializable {
    private int questionNumber;
    private String questionDetails;
    private String[] answerOptions;

    public MCQ(int questionNumber,String questionDetails,String[] answerOptions){
        this.questionDetails = questionDetails;
        this.questionNumber = questionNumber;
        this.answerOptions = answerOptions;
    }

    @Override
    public int getQuestionNumber() {
        return questionNumber;
    }

    @Override
    public String getQuestionDetail() {
        return questionDetails;
    }

    @Override
    public String[] getAnswerOptions() {
        return answerOptions;
    }
}
