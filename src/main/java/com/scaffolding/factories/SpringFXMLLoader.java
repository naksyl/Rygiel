package com.scaffolding.factories;

import com.scaffolding.enums.ViewType;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URL;

@Component
public class SpringFXMLLoader implements ApplicationContextAware {

    private ApplicationContext context;

    public Parent loadFXML(ViewType viewType) {
        try {
            FXMLLoader loader = new FXMLLoader();
            URL location = ResourceMapper.getViewFXMLResource(viewType);
            loader.setLocation(location);
            loader.setControllerFactory(context::getBean);
            return loader.load();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}