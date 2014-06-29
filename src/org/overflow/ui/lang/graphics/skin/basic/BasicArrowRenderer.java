package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WArrow;
import org.overflow.ui.lang.graphics.skin.renderer.ArrowRenderer;
import org.overflow.ui.lang.layout.Orientation;

import java.awt.*;
import java.awt.geom.AffineTransform;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 22:38
 */
public class BasicArrowRenderer implements ArrowRenderer {

    @Override
    public void renderWidget(WArrow widget, Graphics2D g) {
        g.setPaint(widget.getBackgroundPainter());
        g.fill(widget.getBounds());
        g.setPaint(widget.getForegroundPainter());
        g.translate(widget.getX() + 10, widget.getY() + 10);
        g.fill(widget.getDirection().getPolygon());
    }

    @Override
    public Dimension getMinimumSize(WArrow wArrow) {
        return new Dimension(20, 20);
    }

    @Override
    public boolean contains(WArrow wArrow, int x, int y) {
        return wArrow.getBounds().contains(x, y);
    }
}
