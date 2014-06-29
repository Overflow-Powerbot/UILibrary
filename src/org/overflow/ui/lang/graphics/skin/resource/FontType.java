package org.overflow.ui.lang.graphics.skin.resource;

/**
 * Author: Tom
 * Date: 25/06/14
 * Time: 00:44
 */
public enum FontType {
    TITLE("title"),
    HEADING("heading"),
    TEXT("text");

    private final String locale;

    FontType(String locale) {
        this.locale = locale.concat(".font");
    }

    public String getLocale() {
        return locale;
    }
}
