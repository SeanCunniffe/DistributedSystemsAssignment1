import ct414.ExamServer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Main extends Application {
    public static ExamServer server;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("view.fxml"));
        primaryStage.setTitle("Client");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        int registryport = 20345;

        if (args.length > 0)
            registryport = Integer.parseInt(args[0]);

        System.out.println("RMIRegistry port = " + registryport);

        if (System.getSecurityManager() == null) {
            System.setSecurityManager(new SecurityManager());
        }
        try{
            String name = "ExamServer";
            Registry registry = LocateRegistry.getRegistry(registryport);
            server = (ExamServer) registry.lookup(name);
        }catch(Exception e){
            e.printStackTrace();
        }
        launch(args);
    }
}
