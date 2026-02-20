package prototype.amidoinggood;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class App extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Chargement du fichier FXML
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("dashboard.fxml"));

        // Création de la scène (320x500 pixels)
        Scene scene = new Scene(fxmlLoader.load(), 350, 600);

        stage.setTitle("Am I Doing Good?");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}