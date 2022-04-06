package put.projdbdict.dbdictproj;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class DBApplication extends Application {
    //DBUtil db = new DBUtil();

    @Override
    public void start(Stage stage) throws IOException {
        MainWindowController.stage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(DBApplication.class.getResource("MainWindow.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        MainWindowController.stage.setTitle("DBProj work in progress");
        MainWindowController.stage.setScene(scene);
        MainWindowController.stage.show();
        DBUtil.obj = new DBUtil();
        DBUtil.obj.connect();

    }

    public static void main(String[] args) {
        launch();
    }
}