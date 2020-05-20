package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IAspectAware;
import org.springframework.stereotype.Component;

@Component
public class TableDetailsViewController implements IAspectAware {

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {

    }
}
