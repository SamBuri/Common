package com.saburi.common.main;

import com.saburi.common.utils.CommonNavigate;
import com.saburi.common.utils.FXUIUtils;
import com.saburi.common.utils.Navigation;
import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * JavaFX App
 */
public class App  extends Application{

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        try {
        Navigation.parentScene = CommonNavigate.mainClass;
        Navigation.parentFXMl = "MainScene";
        scene = new Scene(CommonNavigate.loadFXML("LoginEdit"));
         stage.setScene(scene);
        stage.show();
        } catch (IOException e) {
            FXUIUtils.errorMessage(e);
        }
    }

   
    
    public static void main(String[] args) {
        launch();
        System.out.println("The Application Runs");
    }

    

}