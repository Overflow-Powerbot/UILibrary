package org.overflow.ui.component;

import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.graphics.skin.resource.FontType;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.layout.manager.FlowLayout;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 23:50
 */
public class WButton extends WidgetContainer<WButton> {

    public WButton() {
        super(ResourceLocale.BUTTON);
        this.setLayoutManager(new FlowLayout());
    }

    @Override
    protected final WButton get() {
        return this;
    }
}
