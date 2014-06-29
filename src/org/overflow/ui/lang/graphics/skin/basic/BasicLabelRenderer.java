package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WLabel;
import org.overflow.ui.lang.graphics.skin.renderer.WidgetRenderer;
import org.overflow.ui.lang.layout.Inset;
import sun.font.FontDesignMetrics;

import javax.swing.*;
import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 16:40
 */
public class BasicLabelRenderer implements WidgetRenderer<WLabel> {

    private int descent = -1;

    @Override
    public void renderWidget(WLabel widget, Graphics2D g) {
        g.setFont(widget.getFont());
        final int x = widget.getX() + widget.getInset().getLeft();
        final int y = widget.getY() + widget.getHeight() - descent;
        g.setColor(Color.BLACK);
        g.drawString(widget.getText(), x + 1, y + 1);
        g.setPaint(widget.getForegroundPainter());
        g.drawString(widget.getText(), x, y);
    }

    @Override
    public Dimension getMinimumSize(final WLabel label) {
        if (label.getFont() == null) {
            return new Dimension(0, 0);
        }
        final Inset i = label.getInset();
        final FontDesignMetrics metrics = FontDesignMetrics.getMetrics(label.getFont());
        this.descent = metrics.getMaxDescent();
        return new Dimension(SwingUtilities.computeStringWidth(metrics, label.getText()) + i.getHorizontalTotal(), metrics.getHeight() + i.getVerticalTotal() + 1);
    }

    @Override
    public boolean contains(final WLabel label, final int x, final int y) {
        return label.getBounds().contains(x, y);
    }
}
