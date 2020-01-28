/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.saburi.common.utils;

import java.io.IOException;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Hp
 */
public class LoginHelper {

    public static void showStage(String title) {
        try {
            Scene scene = new Scene(CommonNavigate.loadFXML("LoginEdit"));
            scene.getStylesheets().add(Navigation.styleControls);
            Stage stage = new Stage();
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

}
