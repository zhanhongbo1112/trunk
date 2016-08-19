package com.yqboots.project.menu.autoconfigure;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.nio.file.Path;

/**
 * Created by Administrator on 2016-08-15.
 */
@ConfigurationProperties(prefix = "yqboots.project.menu")
public class MenuItemProperties {
    private Path exportFileLocation;

    private String exportNamePrefix;

    private boolean importEnabled;

    private String importFileLocation;

    public Path getExportFileLocation() {
        return exportFileLocation;
    }

    public void setExportFileLocation(final Path exportFileLocation) {
        this.exportFileLocation = exportFileLocation;
    }

    public String getExportNamePrefix() {
        return exportNamePrefix;
    }

    public void setExportNamePrefix(final String exportNamePrefix) {
        this.exportNamePrefix = exportNamePrefix;
    }

    public boolean isImportEnabled() {
        return importEnabled;
    }

    public void setImportEnabled(final boolean importEnabled) {
        this.importEnabled = importEnabled;
    }

    public String getImportFileLocation() {
        return importFileLocation;
    }

    public void setImportFileLocation(final String importFileLocation) {
        this.importFileLocation = importFileLocation;
    }
}
