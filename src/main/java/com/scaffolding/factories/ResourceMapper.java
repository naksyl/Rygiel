package com.scaffolding.factories;

import com.scaffolding.ScaffoldingApplication;
import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.enums.ViewType;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

public class ResourceMapper {

    public static URL getViewFXMLResource(ViewType viewType) {
        String path = "/com/scaffolding/view/" + viewType.toString() + ".fxml";
        return ScaffoldingApplication.class.getResource(path);
    }

    public static URL getResource(String resource) {
        String path = "/com/scaffolding/" + resource;
        return ScaffoldingApplication.class.getResource(path);
    }

    public static ViewType getAspectViewType(ApplicationAspect aspect) {
        switch (aspect) {
            case WELCOME:
                return ViewType.WELCOME_VIEW;
            case OPTIONS:
                return ViewType.OPTIONS_VIEW;
            case STATISTICS:
                return ViewType.STATISTIC_VIEW;
            default:
                return ViewType.DATABASE_VIEW;
        }
    }

    public static File getExampleDatabaseFile() {
        String path = "/com/scaffolding/example/example.mv.db";
        return new File(ScaffoldingApplication.class.getResource(path).getFile());
    }

    public static Properties getApplicationProperties() {
        String path = "/com/scaffolding/configuration/scaffolding.properties";
        try {
            Properties properties = new Properties();
            properties.load(ScaffoldingApplication.class.getResourceAsStream(path));
            return properties;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
