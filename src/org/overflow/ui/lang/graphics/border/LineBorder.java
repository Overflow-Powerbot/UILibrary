package org.overflow.ui.lang.graphics.border;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 01:33
 */
public class LineBorder implements Border {

    private final Inset inset;
    private final int thickness;

    public LineBorder(int thickness, Inset inset) {
        this.thickness = Math.max(1, thickness);
        this.inset = inset;
    }

    public LineBorder(int thickness) {
        this(thickness, new Inset(Math.max(thickness, 1) + 2));
    }

    @Override
    public void renderBorder(Widget widget, Graphics2D g2) {
        Color color = widget.getBorderColor();
        Rectangle r = new Rectangle(widget.getBounds());
        for (int i = 0; i < thickness; i++) {
            g2.setPaint(color);
            g2.draw(r);
            r.translate(1, 1);
            color = color.darker();
        }
    }

    @Override
    public Inset getInset() {
        return inset;
    }
}
