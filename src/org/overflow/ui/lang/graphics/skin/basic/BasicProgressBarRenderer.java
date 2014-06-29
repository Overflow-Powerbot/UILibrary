package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WProgressBar;
import org.overflow.ui.lang.graphics.skin.renderer.ProgressBarRenderer;
import org.overflow.util.profiling.SystemProfiler;

import java.awt.*;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 22:01
 */
public class BasicProgressBarRenderer implements ProgressBarRenderer {
    @Override
    public void renderWidget(WProgressBar widget, Graphics2D g) {
        g.setPaint(widget.getBackgroundPainter());
        g.fill(widget.getBounds());
        g.setPaint(widget.getForegroundPainter());
        g.fillRect(widget.getX(), widget.getY(), (int) (widget.getWidth() * (widget.getPercentage() / 100d)), widget.getHeight());
    }

    @Override
    public Dimension getMinimumSize(WProgressBar wPanel) {
        return new Dimension(300, 25);
    }

    @Override
    public boolean contains(WProgressBar wPanel, int x, int y) {
        return wPanel.getBounds().contains(x, y);
    }
}
