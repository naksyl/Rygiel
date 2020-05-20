package com.scaffolding.interfaces;

import com.scaffolding.enums.ApplicationAspect;

public interface IAspectAware {
    void beforeApplicationContextChange(ApplicationAspect applicationAspect);
    void onApplicationAspectChanged(ApplicationAspect applicationAspect);
}
