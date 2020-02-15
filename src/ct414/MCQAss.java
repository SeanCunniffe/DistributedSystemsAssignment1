package ct414;
import java.io.Serializable;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class MCQAss implements Serializable,Assessment {
    private String information;
    private Date date;
    private LinkedList<Question> questions;
    private LinkedList<int[]> answerBook; //a list of int[questionNumber,optionNumber] matches questions with chosen options
    private int ID;

    public MCQAss(int ID,String information, Date date, LinkedList<Question> questions){
        this.ID = ID;
        this.information=information;
        this.date = date;
        this.questions = questions;
    }
    @Override
    public String getInformation() {
        return information;
    }

    @Override
    public Date getClosingDate() {
        return date;
    }

    @Override
    public List<Question> getQuestions() {
        return questions;
    }

    @Override
    public Question getQuestion(int questionNumber) throws InvalidQuestionNumber {
        return questions.get(questionNumber);
    }

    @Override
    public void selectAnswer(int questionNumber, int optionNumber) throws InvalidQuestionNumber, InvalidOptionNumber {
        for(int[] q: answerBook){
            if(q[0] == questionNumber){
                q[1] = optionNumber;
                return;
            }
            answerBook.add(new int[]{questionNumber,optionNumber});
        }
    }

    /**
     * returns -1 if no answer found for question
     * @param questionNumber
     * @return
     */
    @Override
    public int getSelectedAnswer(int questionNumber) {
        for(int[] q: answerBook){
            if(q[0] == questionNumber){
                return q[1];
            }
        }
        return -1;
    }

    @Override
    public int getAssociatedID() {
        return ID;
    }
}
