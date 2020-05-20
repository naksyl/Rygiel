package com.scaffolding.controllers;

import com.scaffolding.enums.ApplicationAspect;
import com.scaffolding.interfaces.IAspectAwareController;
import org.springframework.stereotype.Component;

@Component
public class DatabaseViewController implements IAspectAwareController {

    @Override
    public void beforeApplicationContextChange(ApplicationAspect applicationAspect) {

    }

    @Override
    public void onApplicationAspectChanged(ApplicationAspect applicationAspect) {
        System.out.println("Aspects works");
    }
}
