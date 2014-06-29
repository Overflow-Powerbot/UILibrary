package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WIcon;
import org.overflow.ui.lang.graphics.skin.renderer.IconRenderer;

import java.awt.*;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 00:06
 */
public class BasicIconRenderer implements IconRenderer {
    @Override
    public void renderWidget(WIcon widget, Graphics2D g) {
        if (widget.getImage() != null) {
            g.drawImage(widget.getImage(), widget.getX(), widget.getY(), null);
        }
    }

    @Override
    public Dimension getMinimumSize(WIcon wIcon) {
        final Image image = wIcon.getImage();
        return image == null ? new Dimension(0, 0) : new Dimension(image.getWidth(null), image.getHeight(null));
    }

    @Override
    public boolean contains(WIcon wIcon, int x, int y) {
        return wIcon.getBounds().contains(x, y);
    }
}
