package com.saburi.common.controllers;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import com.saburi.common.utils.FXUIUtils;

import com.saburi.common.utils.CommonSearchTree;
import static com.saburi.common.utils.Navigation.loadSearchEngine;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


/**
 * FXML Controller class
 *
 * @author ClinicMaster13
 */
public class ParentSceneController extends MainSceneController{


    /**
     * Initializes the controller class.
     *
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try{
           init();
           loadSearchEngine(mnuSearchEngine, new CommonSearchTree().getTreeItems(), true);

        } catch (IOException ex) {
            FXUIUtils.errorMessage(ex);
        }
    }

}
