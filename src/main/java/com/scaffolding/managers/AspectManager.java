package com.scaffolding.managers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IApplicationManager;
import com.scaffolding.interfaces.IAspectAware;
import com.scaffolding.interfaces.IAspectManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class AspectManager implements IAspectManager {


    private IApplicationManager applicationManager;
    private List<IAspectAware> aspectAwareControllers = new ArrayList<>();


    @Autowired
    public void setApplicationManager(@Lazy IApplicationManager applicationManager) {
        this.applicationManager = applicationManager;
    }

    @Autowired
    public void setAspectAwareControllers(@Lazy List<IAspectAware> aspectAwareControllers) {
        this.aspectAwareControllers = aspectAwareControllers;
    }

    public void changeApplicationAspect(ApplicationAspect aspect) {
        for(IAspectAware controller: aspectAwareControllers)
            controller.beforeApplicationContextChange(aspect);
        for(IAspectAware controller: aspectAwareControllers)
            controller.onApplicationAspectChanged(aspect);
    }

    @Override
    public ApplicationAspect getApplicationAspect() {
        return applicationManager.getApplicationAspect();
    }
}
