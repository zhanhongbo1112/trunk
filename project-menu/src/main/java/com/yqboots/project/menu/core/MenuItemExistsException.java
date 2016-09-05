package com.yqboots.project.menu.core;

/**
 * The exception thrown when the menu item exists.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public class MenuItemExistsException extends RuntimeException {
    /**
     * Constructs a new runtime exception with the specified detail message.
     * The cause is not initialized, and may subsequently be initialized by a
     * call to {@link #initCause}.
     *
     * @param message the detail message. The detail message is saved for
     *                later retrieval by the {@link #getMessage()} method.
     */
    public MenuItemExistsException(final String message) {
        super(message);
    }
}
