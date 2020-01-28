package com.saburi.common.controllers;

import com.saburi.common.dbaccess.JournalEntryDA;
import static com.saburi.common.utils.FXUIUtils.errorMessage;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.control.MenuItem;
import com.saburi.common.utils.CommonEnums;

public class JournalEntryViewController extends AbstractViewController {

    @FXML
    private TableView<JournalEntryDA> tblJournalEntry;

    @FXML
    private MenuItem cmiPost;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            this.tableView = tblJournalEntry;
            initSearchEvents();

            cmiPost.setOnAction(e -> {
                try {
                    JournalEntryDA selectedItemEntry = tblJournalEntry.getSelectionModel().getSelectedItem();
                    selectedItemEntry.post();
                    this.list = new JournalEntryDA().get();
                    this.loadTable();
                } catch (Exception ex) {
                    errorMessage(ex);
                }
            });
        } catch (Exception e) {
            errorMessage(e);
        } finally {
        }
    }

    @Override
    protected void viewContextMenuShowing() {
        super.viewContextMenuShowing();
        JournalEntryDA journalEntryDA = tblJournalEntry.getSelectionModel().getSelectedItem();
        if (journalEntryDA == null) {
            cmiPost.visibleProperty().set(false);
        } else {
            cmiPost.visibleProperty().set(journalEntryDA.getPostStatus().equals(CommonEnums.PostStatus.Pending));
        }
    }

}
