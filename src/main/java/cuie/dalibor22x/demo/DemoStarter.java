package cuie.dalibor22x.demo;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class DemoStarter extends Application {

    @Override
    public void start(Stage primaryStage)  {
        PresentationModel pm = new PresentationModel();

        Pane rootPane = new DemoPane(pm);

        Scene scene = new Scene(rootPane);

        primaryStage.setTitle("WindPark Business Control Demo");
        primaryStage.setScene(scene);

        primaryStage.show();

 //       ScenicView.show(scene);
 //       CSSFX.start();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
