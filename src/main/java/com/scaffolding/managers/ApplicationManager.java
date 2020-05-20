package com.scaffolding.managers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;
import com.scaffolding.factories.DAOFactory;
import com.scaffolding.factories.ResourceMapper;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IAspectManager;
import com.scaffolding.interfaces.IHibernateSessionManager;
import com.scaffolding.interfaces.IViewManager;
import com.scaffolding.model.Contractor;
import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.sound.midi.Soundbank;
import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.Stack;

@Component("applicationManager")
public class ApplicationManager implements IApplicationManager {

    public static final String APPLICATION_NAME = "Rygiel 3.8";

    private final Properties applicationProperties;
    private Stage primaryStage;
    private final Stack<Stage> stages = new Stack<>();
    private ApplicationAspect aspect;
    private final IViewManager viewManager;
    private final IAspectManager aspectManager;
    private final IHibernateSessionManager sessionManager;
    private FileChooser fileChooser;

    @Autowired
    public ApplicationManager(IViewManager viewManager, IAspectManager aspectManager, IHibernateSessionManager sessionManager) {
        this.viewManager = viewManager;
        this.aspectManager = aspectManager;
        this.sessionManager = sessionManager;
        applicationProperties = ResourceMapper.getApplicationProperties();
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
        stage.setMinHeight(480);
        stage.setMinWidth(640);
        stage.setTitle(APPLICATION_NAME);
        stages.push(primaryStage);
        showView(ViewType.MAIN_VIEW);
        stage.show();
    }

    @Override
    public void showView(ViewType view) {
        Scene scene = new Scene(viewManager.createView(ViewType.MAIN_VIEW));
        primaryStage.setScene(scene);
    }

    @Override
    public Stage showWindow(ViewType view, String title) {
        Stage stage = new Stage();
        stage.setTitle(title);
        Parent parent = viewManager.createView(view);
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
        setupFileChooser();
        File file = new File("C:\\Users\\snako\\.scaffolding\\example");
        sessionManager.openSession(file,"");
        Contractor contractor = new Contractor("UVU","lsbfv","ksfslk","938473","874654945");
        sessionManager.getContractorDAO().saveOrUpdate(contractor);
        List<Contractor> contractorList = sessionManager.getContractorDAO().findAll();
        Contractor id1 = sessionManager.getContractorDAO().findById(1);
        System.out.println("Count="+contractorList.size()+" name = " + id1.getName());
        for(Contractor c: contractorList)
            System.out.println(c.getName());
        sessionManager.closeSession();
    }



    @Override
    public void saveFile() {

    }

    @Override
    public void close() {
        Platform.exit();
    }

    private void setupFileChooser() {
        fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File(applicationProperties.getProperty("database.home")));
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Baza danych rygla", "*.db"));
        fileChooser.setTitle("Otwórz bazę danych");
    }
}
