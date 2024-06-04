package sae.saezelda;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sae.saezelda.controleur.Controleur;

import java.io.IOException;

public class Main extends Application {
    //methode principale pour le chargement de l'application BLABLABLA
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("vue.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 320);

        Controleur controleur = fxmlLoader.getController();


        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch();
    }
}