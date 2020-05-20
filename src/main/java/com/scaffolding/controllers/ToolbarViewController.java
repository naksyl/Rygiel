package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IAspectAware;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import org.springframework.stereotype.Component;

@Component
public class ToolbarViewController implements IAspectAware {

    @FXML
    private ChoiceBox filterChoiceBox;
    @FXML
    private TextField filterTextField;
    @FXML
    private Button clearFilterButton;

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {

    }
}
