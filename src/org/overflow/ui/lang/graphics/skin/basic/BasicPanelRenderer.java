package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WPanel;
import org.overflow.ui.lang.graphics.skin.renderer.PanelRenderer;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 20:18
 */
public class BasicPanelRenderer implements PanelRenderer {
    @Override
    public void renderWidget(WPanel widget, Graphics2D g) {
        g.setPaint(widget.getBackgroundPainter());
        g.fill(widget.getBounds());
    }

    @Override
    public Dimension getMinimumSize(WPanel wPanel) {
        return new Dimension(0, 0);
    }

    @Override
    public boolean contains(WPanel wPanel, int x, int y) {
        return wPanel.getBounds().contains(x, y);
    }
}
