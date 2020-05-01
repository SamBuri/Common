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
        Navigation.parentScene = CommonNavigate.MAIN_CLASS;
        Navigation.parentFXMl = "MainScene";
        Navigation.persistenceUnit = "com.saburi.mysql.common";
        Navigation.appTitle="Common";
        Navigation.image = Navigation.getImage(CommonNavigate.MAIN_CLASS);
        stage.getIcons().add(Navigation.image);
        scene = new Scene(CommonNavigate.loadFXML("LoginEdit"));
        scene.getStylesheets().add(Navigation.styleControls);
         stage.setScene(scene);
         stage.setTitle(Navigation.appTitle);
         
         
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