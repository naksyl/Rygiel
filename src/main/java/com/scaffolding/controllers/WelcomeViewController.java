package com.scaffolding.controllers;

import com.scaffolding.interfaces.IApplicationManager;
import javafx.event.ActionEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class WelcomeViewController {

    private IApplicationManager applicationManager;

    @Autowired
    public void setApplicationManager(IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    public void openExampleDatabase(ActionEvent actionEvent) {

        System.out.println("Open eXAMPLE");
        applicationManager.openExampleDatabase();
    }
}
