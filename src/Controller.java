import ct414.UnauthorizedAccess;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

import java.rmi.RemoteException;

public class Controller {

    public Button startQuizButton;
    public Button loginButton;
    public TextField usernameField;
    public PasswordField passwordField;

    @FXML
    public String getUsername(ActionEvent event){
        return usernameField.getText();
    }

    @FXML public String getPassword(ActionEvent event){
        return passwordField.getText();
    }

    @FXML public void loginHandler(ActionEvent event) throws UnauthorizedAccess, RemoteException {
        Main.server.login(Integer.parseInt(usernameField.getText()),passwordField.getText());
    }
}
