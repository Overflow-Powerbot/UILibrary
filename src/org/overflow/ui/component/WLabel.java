package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 16:41
 */
public class WLabel extends Widget<WLabel> {

    private String text = "";

    public WLabel() {
        super(ResourceLocale.LABEL);
    }

    @Override
    protected final WLabel get() {
        return this;
    }

    public final String getText() {
        return text;
    }

    public final WLabel setText(String text) {
        if (text == null || !this.text.equals(text)) {
            this.text = text;
            this.invalidate();
        }
        return get();
    }
}
