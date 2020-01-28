/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.io.IOException;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hp
 */
public class FXLaunch extends Application{
    private String title;
    public  FXLaunch(String title){
        this.title = title;
    }

   
     @Override
    public void start(Stage stage) throws Exception {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("/ui/LoginEdit.fxml"));

            Scene scene = new Scene(root);
            scene.getStylesheets().add("/css/StyleControls.css");
            stage.setTitle(title);
            stage.setScene(scene);
            stage.show();
            stage.setOnCloseRequest(e -> System.exit(0));
            
        } catch (IOException e) {
            FXUIUtils.errorMessage(e);
        } catch (Exception e) {
            FXUIUtils.errorMessage(e);
        }
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
