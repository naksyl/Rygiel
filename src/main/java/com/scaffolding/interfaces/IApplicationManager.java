package com.scaffolding.interfaces;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;
import javafx.stage.Stage;

import java.util.Properties;

public interface IApplicationManager {

    Properties getApplicationProperties();
    ApplicationAspect getApplicationAspect();
    void setApplicationAspect(ApplicationAspect aspect);
    void run(Stage stage);
    void showView(ViewType view);
    Stage showWindow(ViewType view, String title);
    Stage getActiveWindow();
    void closeActiveWindow();
    void openFile();
    void saveFile();
    void close();
}
