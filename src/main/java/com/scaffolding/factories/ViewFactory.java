package com.scaffolding.factories;

import com.scaffolding.enums.ViewType;
import com.scaffolding.interfaces.IViewManager;
import javafx.scene.Parent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ViewFactory implements IViewManager {

    private final SpringFXMLLoader springFXMLLoader;

    @Autowired
    public ViewFactory(SpringFXMLLoader springFXMLLoader) {
        this.springFXMLLoader = springFXMLLoader;
    }

    @Override
    public Parent createView(ViewType view) {
        return springFXMLLoader.loadFXML(view);
    }
}
