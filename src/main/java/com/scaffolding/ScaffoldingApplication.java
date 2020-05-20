package com.scaffolding;

import com.scaffolding.configuration.SpringConfiguration;
import com.scaffolding.managers.ApplicationManager;
import javafx.application.Application;
import javafx.stage.Stage;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ScaffoldingApplication extends Application {

    private AnnotationConfigApplicationContext context;
    private ApplicationManager manager;

    @Override
    public void start(Stage stage) {
        manager.run(stage);
    }

    @Override
    public void init() throws Exception {
        super.init();
        context = new AnnotationConfigApplicationContext(SpringConfiguration.class);
        manager = context.getBean(ApplicationManager.class);
    }

    @Override
    public void stop() throws Exception {
        System.out.println("Application closing");
        context.close();
        super.stop();
    }
}
