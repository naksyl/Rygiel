package com.scaffolding.interfaces;

import com.scaffolding.enums.ViewType;
import javafx.scene.Parent;

public interface IViewManager {

    Parent createView(ViewType view);
}
