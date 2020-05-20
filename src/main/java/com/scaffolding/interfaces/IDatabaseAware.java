package com.scaffolding.interfaces;

import java.io.File;

public interface IDatabaseAware {

    void onDatabaseOpen(File file);
    void onDatabaseClose();
}
