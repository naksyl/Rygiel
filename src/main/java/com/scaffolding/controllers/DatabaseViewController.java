package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IAspectAware;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

@Component
public class DatabaseViewController implements IAspectAware {

    @FXML
    TableView tableView;

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {
//        if(tableView != null)
//            tableView.getColumns().clear();
    }
}
