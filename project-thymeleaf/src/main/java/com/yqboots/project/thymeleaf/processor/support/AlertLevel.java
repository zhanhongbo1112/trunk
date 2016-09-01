package com.yqboots.project.thymeleaf.processor.support;

/**
 * Alert level.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public enum AlertLevel {
    SUCCESS("success"), INFO("info"), WARNING("warning"), ERROR("danger");

    private final String level;

    private AlertLevel(final String level) {
        this.level = level;
    }

    /**
     * @param level the alert level
     * @return the correspondence style class
     */
    public static String getStyleClass(String level) {
        return "alert alert-" + level;
    }
}
