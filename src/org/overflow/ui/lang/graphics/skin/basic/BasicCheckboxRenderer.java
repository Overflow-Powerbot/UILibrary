package org.overflow.ui.lang.graphics.skin.basic;

import org.overflow.ui.component.WCheckbox;
import org.overflow.ui.lang.graphics.skin.renderer.CheckboxRenderer;

import java.awt.*;

/**
 * Author: Tom
 * Date: 14/06/14
 * Time: 01:41
 */
public class BasicCheckboxRenderer implements CheckboxRenderer {

    @Override
    public void renderWidget(WCheckbox checkbox, Graphics2D g) {
        g.setPaint(checkbox.getBackgroundPainter());
        g.fill(checkbox.getBounds());
        if (checkbox.isSelected()) {
            g.setPaint(checkbox.getForegroundPainter());
            g.drawLine(checkbox.getX(), checkbox.getY(), checkbox.getX() + checkbox.getWidth(), checkbox.getY() + checkbox.getHeight());
            g.drawLine(checkbox.getX(), checkbox.getY()+checkbox.getHeight(), checkbox.getX() + checkbox.getWidth(), checkbox.getY());
        }
    }

    @Override
    public Dimension getMinimumSize(final WCheckbox checkbox) {
        return new Dimension(15, 15);
    }

    @Override
    public boolean contains(final WCheckbox checkbox, final int x, final int y) {
        return checkbox.getBounds().contains(x, y);
    }
}
