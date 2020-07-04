package com.scaffolding.managers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;
import com.scaffolding.factories.ResourceMapper;
import com.scaffolding.interfaces.*;
import com.scaffolding.model.Contractor;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;
import java.util.Stack;

@Component("applicationManager")
public class ApplicationManager implements IApplicationManager {

    public static final String APPLICATION_NAME = "Rygiel 3.8";

    private Stage primaryStage;
    private final Properties applicationProperties;
    private ApplicationAspect aspect;
    private final Stack<Stage> stages = new Stack<>();
    private final IViewManager viewFactory;
    private final IAspectManager aspectManager;
    private final IHibernateSessionManager sessionManager;
    private final IStorageManager storageManager;

    private FileChooser fileChooser;
    private File openedFile;

    @Autowired
    public ApplicationManager(IViewManager viewFactory, IAspectManager aspectManager, IHibernateSessionManager sessionManager, IStorageManager storageManager) {
        this.viewFactory = viewFactory;
        this.aspectManager = aspectManager;
        this.sessionManager = sessionManager;
        this.storageManager = storageManager;
        applicationProperties = ResourceMapper.getApplicationProperties();
        setupFileChooser();

    }


    @Override
    public Properties getApplicationProperties() {
        return applicationProperties;
    }

    @Override
    public ApplicationAspect getApplicationAspect() {
        return aspect;
    }

    @Override
    public void setApplicationAspect(ApplicationAspect aspect) {
        aspectManager.changeApplicationAspect(aspect);
        this.aspect = aspect;
    }

    @Override
    public void run(Stage stage) {
        this.primaryStage = stage;
        primaryStage.setOnCloseRequest(windowEvent -> {
            if (windowEvent.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
                close();
            }
        });
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setTitle(APPLICATION_NAME);
        stages.push(primaryStage);
        showView(ViewType.MAIN_VIEW);
        setApplicationAspect(ApplicationAspect.WELCOME);
        stage.show();
    }

    @Override
    public void showView(ViewType view) {
        Scene scene = new Scene(viewFactory.createView(ViewType.MAIN_VIEW));
        primaryStage.setScene(scene);
    }

    @Override
    public Stage showWindow(ViewType view, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Parent parent = viewFactory.createView(view);
        Scene scene = new Scene(parent);
        stage.setScene(scene);
        stages.push(stage);
        stage.show();
        return stage;
    }

    @Override
    public void showWarning(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setHeaderText("Informacja");
        alert.setContentText(message);
        alert.setTitle(APPLICATION_NAME);
        alert.show();
    }

    @Override
    public Stage getActiveWindow() {
        return stages.peek();
    }

    @Override
    public void closeActiveWindow() {
        Stage stage = stages.pop();
        stage.close();
    }

    @Override
    public void openFile() {
        fileChooser.setTitle("Otwórz bazę danych");
        if (hasOpenedFile()) {
            sessionManager.closeSession();
            openedFile = null;
        }
        File temp = fileChooser.showOpenDialog(stages.peek());
        if (temp != null)
            if (Files.exists(temp.toPath())) {
                System.out.println("Otwieram baze " + temp.getAbsolutePath());
                if (sessionManager.openSession(temp, "")) {
                    openedFile = temp;
                    updateTitle();
                    setApplicationAspect(ApplicationAspect.STATISTICS);
                }
            }
    }

    @Override
    public void openExampleDatabase() {
        openedFile = new File("./empty");
        if (sessionManager.openSession(openedFile, "")) {
            setApplicationAspect(ApplicationAspect.STATISTICS);
        }
    }

    @Override
    public boolean hasOpenedFile() {
        return (openedFile != null);
    }

    @Override
    public void newFile() {
        if (hasOpenedFile()) {
            sessionManager.closeSession();
            openedFile = null;
        }
        openedFile = null;
        fileChooser.setTitle("Wybierz nazwę nowego pliku");
        File newFile = fileChooser.showSaveDialog(stages.peek());
        if (newFile != null) {
            System.out.println("Otwieram baze " + newFile.getAbsolutePath());
            if (sessionManager.openSession(newFile, "")) {
                openedFile = newFile;
                updateTitle();
                setApplicationAspect(ApplicationAspect.STATISTICS);
            }
        }

    }

    @Override
    public void saveAs() {
        File saved = fileChooser.showSaveDialog(stages.peek());
        System.out.println(saved);
    }

    @Override
    public void close() {
        sessionManager.closeSession();
        Platform.exit();
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(applicationProperties.getProperty("database.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Baza danych rygla", "*.mv.db"));
        fileChooser.setTitle("Otwórz bazę danych");
    }

    private void updateTitle() {
        primaryStage.setTitle(APPLICATION_NAME + " [" + openedFile + "]");
    }
}
