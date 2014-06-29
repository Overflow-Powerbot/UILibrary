package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WButton;
import org.overflow.ui.lang.graphics.skin.renderer.ButtonRenderer;

import java.awt.*;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 23:54
 */
public class BasicButtonRenderer implements ButtonRenderer {
    @Override
    public void renderWidget(WButton widget, Graphics2D g) {
        g.setPaint(widget.isHovered() ? widget.getForegroundPainter() : widget.getBackgroundPainter());
        g.fill(widget.getBounds());
    }

    @Override
    public Dimension getMinimumSize(WButton wPanel) {
        return new Dimension(50, 25);
    }

    @Override
    public boolean contains(WButton wPanel, int x, int y) {
        return wPanel.getBounds().contains(x, y);
    }
}
