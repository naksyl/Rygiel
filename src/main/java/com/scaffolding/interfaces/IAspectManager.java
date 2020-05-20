package com.scaffolding.interfaces;

import com.scaffolding.enums.ApplicationAspect;

public interface IAspectManager {
    void changeApplicationAspect(ApplicationAspect aspect);
    ApplicationAspect getApplicationAspect();
}
