package com.scaffolding.controllers;

import com.scaffolding.interfaces.IApplicationManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

@Component
public class OptionsViewController {

    private IApplicationManager applicationManager;

    public void closeWindow() {
        applicationManager.closeActiveWindow();
    }

    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }
}
