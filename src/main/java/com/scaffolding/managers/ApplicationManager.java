package com.scaffolding.managers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;
import com.scaffolding.factories.ResourceMapper;
import com.scaffolding.interfaces.*;
import com.scaffolding.model.Contractor;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
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
            if(windowEvent.getEventType() == WindowEvent.WINDOW_CLOSE_REQUEST) {
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

    }

    @Override
    public void openExampleDatabase() {
        openedFile = ResourceMapper.getExampleDatabaseFile();
        if(sessionManager.openSession(openedFile, ""))
            setApplicationAspect(ApplicationAspect.STATISTICS);
        sessionManager.getCurrentSession().beginTransaction();
        Contractor contractor = new Contractor("sgf", "sfgsrf", "igv", "isfsfi", "sid");
        sessionManager.getCurrentSession().save(contractor);
        System.out.println(sessionManager.getCurrentSession().createQuery("from Contractor ").list().get(0));
        sessionManager.getCurrentSession().getTransaction().commit();
    }

    @Override
    public boolean hasOpenedFile() {
        return (openedFile != null);
    }

    @Override
    public void saveFile() {

    }

    @Override
    public void close() {
        sessionManager.closeSession();
        Platform.exit();
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(applicationProperties.getProperty("database.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Baza danych rygla", "*.db"));
        fileChooser.setTitle("Otwórz bazę danych");
    }
}
