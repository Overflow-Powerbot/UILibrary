package org.overflow.ui.component;

import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 20:12
 */
public class WPanel extends WidgetContainer<WPanel> {

    public WPanel(ResourceLocale locale) {
        super(locale);
    }

    public WPanel() {
        this(ResourceLocale.PANEL);
    }

    @Override
    protected WPanel get() {
        return this;
    }
}
