import ct414.Assessment;
import ct414.NoMatchingAssessment;
import ct414.UnauthorizedAccess;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;

public class Controller {

    @FXML
    public Button startQuizButton;
    @FXML
    public Button loginButton;
    @FXML
    public TextField usernameField;
    @FXML
    public PasswordField passwordField;
    public int studentID;
    public List<String> exams;
    @FXML
    public ListView<String> examList;
    @FXML
    public GridPane frame;

    @FXML
    public void initialize(){
        Scene scene = new Scene(frame);
        Main.primaryStage.setScene(scene);
        examList.getItems().addAll("example1","example2","example3");
        examList.getSelectionModel().selectedItemProperty().addListener((a,oldVal,newVal)->{
            if(newVal != null){
                startQuizButton.setDisable(false);
            }else{
                startQuizButton.setDisable(true);
            }
        });
    }

    @FXML
    public String getUsername(ActionEvent event){
        return usernameField.getText();
    }

    @FXML public String getPassword(ActionEvent event){
        return passwordField.getText();
    }

    @FXML public void loginHandler(ActionEvent event) throws RemoteException, NoMatchingAssessment {
        try {
            studentID = Integer.parseInt(usernameField.getText());
            Main.connectToken = Main.server.login(studentID, passwordField.getText());
       exams = Main.server.getAvailableSummary(Main.connectToken,studentID);
       examList.getItems().addAll(exams);
        }catch(UnauthorizedAccess e){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setContentText("No such credentials");
        }
    }

    @FXML public void startQuizHandler(ActionEvent event) throws NoMatchingAssessment, UnauthorizedAccess, IOException {
        String exam = examList.getSelectionModel().getSelectedItem();
        Assessment ass = Main.server.getAssessment(Main.connectToken,studentID,exam);
        FXMLLoader loader = new FXMLLoader();
        loader.load(QuizPane.class.getResource("view.fxml"));
        loader.<QuizPane>getController().setAssessment(ass);
    }
}
