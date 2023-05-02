package group3.mindfactory_administration;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class AdministrationApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(AdministrationApplication.class.getResource("navigation-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setWidth(850);
        stage.setHeight(700);
        stage.setMinWidth(850);
        stage.setMinHeight(700);
        stage.setTitle("Mind Factory Administration");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}