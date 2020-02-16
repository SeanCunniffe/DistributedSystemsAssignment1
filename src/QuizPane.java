import ct414.Assessment;
import ct414.InvalidOptionNumber;
import ct414.InvalidQuestionNumber;
import ct414.Question;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.TemporalAmount;
import java.util.Date;
import java.util.LinkedList;
import java.util.Timer;
import java.util.TimerTask;

public class QuizPane {
    public Button previousQuestionButton;
    public Label timerLabel;
    public Button nextQuestionButton;
    public Label questionPane;
    public VBox optionPane;
    public ProgressBar quizProgress;
    public GridPane frame;
    private LinkedList<Question> questions = new LinkedList();
    private int questionIndex = 0;
    private Assessment ass;
    private Question question;

    @FXML
    public void initialize(){
        Scene scene = new Scene(frame);
        Main.primaryStage.setScene(scene);
    }

    public void setAssessment(Assessment ass){
        this.ass = ass;
        questions.addAll(ass.getQuestions());
        setupQuestion(questionIndex);
        TimerTask task = new TimerTask() {
            @Override
            public void run(){
                LocalDateTime quizTime = ass.getClosingDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
                Duration d= Duration.between(quizTime,LocalDateTime.now());
                timerLabel.setText(d.toString());
            }
        };

        Timer timer = new Timer(true);
        timer.scheduleAtFixedRate(task,0,1000);
    }

    private void setupQuestion(int i) {
        Question question = questions.get(i);
        this.question = question;
        questionPane.setText(question.getQuestionDetail());
        for(String s:question.getAnswerOptions()){
            optionPane.getChildren().add(new CheckBox(s));
        }
    }
    @FXML
    public void nextQuestionHandler(ActionEvent event) throws InvalidOptionNumber, InvalidQuestionNumber {
        if(questionIndex == ass.getQuestions().size()-1){
            loadMainmenu();
            return;
        }
        int selected = 0;
        for(Node node: optionPane.getChildren()){
            if(((CheckBox)node).isSelected()){
                selected = optionPane.getChildren().indexOf(node);
                break;
            }
        }
        ass.selectAnswer(question.getQuestionNumber(),selected);
        setupQuestion(questionIndex++);
        if(questionIndex == ass.getQuestions().size()-1){
            nextQuestionButton.setText("Finish exam");
        }else{
            nextQuestionButton.setText("Next question");
        }
    }

    private void loadMainmenu(){
        try {
            FXMLLoader.load(Controller.class.getResource("view.fxml"));
        }catch(IOException e){
            e.printStackTrace();
        }
    }

    @FXML
    public void previousQuestionHandler(ActionEvent event){
        if(questionIndex <= 0){
            previousQuestionButton.setDisable(true);
        }else {
            setupQuestion(questionIndex--);
        }
    }


}
