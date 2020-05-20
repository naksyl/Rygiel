package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.factories.ResourceMapper;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IAspectAwareController;
import com.scaffolding.interfaces.IViewManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Accordion;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.net.URL;
import java.util.ResourceBundle;

@Component
public class MainViewController implements Initializable, IAspectAwareController {

    @FXML
    private HBox aspectBox;

    @FXML
    private Accordion accordion;

    private IApplicationManager applicationManager;
    private IViewManager viewManager;

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setViewManager(IViewManager viewManager) {
        this.viewManager = viewManager;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        setupAccordion();
    }

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {
        Parent parent = viewManager.createView(ResourceMapper.getAspectViewType(applicationAspect));
        HBox.setHgrow(parent, Priority.ALWAYS);
        aspectBox.getChildren().set(1, parent);
    }

    private void setupAccordion() {
        accordion.expandedPaneProperty().addListener((observableValue, oldPane, newPane) -> {
            if (newPane != null) {
                System.out.println(newPane.getText());
                int aspectNumber = accordion.getPanes().indexOf(newPane);
                ApplicationAspect applicationAspect = ApplicationAspect.values()[aspectNumber];
                applicationManager.setApplicationAspect(applicationAspect);
            } else {
                applicationManager.setApplicationAspect(ApplicationAspect.WELCOME);
            }
        });
        accordion.setExpandedPane(accordion.getPanes().get(0));
    }

    public void closeApplication() {
        applicationManager.close();
    }

    public void openFile() {
        applicationManager.openFile();
    }
}
