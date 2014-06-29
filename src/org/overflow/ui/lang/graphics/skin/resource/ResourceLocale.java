package org.overflow.ui.lang.graphics.skin.resource;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 02:12
 */
public enum ResourceLocale {
    NIL("nil"),
    LABEL("label"),
    PANEL("panel"),
    CHECKBOX("checkbox"),
    BUTTON("button"),
    ICON("icon"),
    ARROW("arrow"),
    PROGRESS_BAR("progress");


    private final String locale;
    private final String resourceLocale;

    private final String borderLocale;
    private final String borderColorLocale;

    private final String foregroundPainterLocale;
    private final String backgroundPainterLocale;

    ResourceLocale(String resourceLocale) {
        this.locale = resourceLocale;
        this.resourceLocale = "resource.".concat(resourceLocale);
        this.borderLocale = resourceLocale.concat(".border");
        this.borderColorLocale = borderLocale.concat(".color");
        this.foregroundPainterLocale = resourceLocale.concat(".foreground");
        this.backgroundPainterLocale = resourceLocale.concat(".background");
    }

    public String getRenderLocale() {
        return resourceLocale;
    }

    public String getFontLocale(FontType type) {
        return locale.concat(".").concat(type.getLocale());
    }

    public String getBorderLocale() {
        return borderLocale;
    }

    public String getBorderColorLocale() {
        return borderColorLocale;
    }

    public String getForegroundPainterLocale() {
        return foregroundPainterLocale;
    }

    public String getBackgroundPainterLocale() {
        return backgroundPainterLocale;
    }
}
