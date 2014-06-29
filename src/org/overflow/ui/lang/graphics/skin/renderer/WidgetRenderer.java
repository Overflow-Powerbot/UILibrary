package org.overflow.ui.lang.graphics.skin.renderer;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.SkinResource;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 01:25
 */
public interface WidgetRenderer<W extends Widget> extends SkinResource {

    public void renderWidget(final W widget, final Graphics2D g);

    public Dimension getMinimumSize(final W w);

    public boolean contains(final W w, final int x, final int y);
}
