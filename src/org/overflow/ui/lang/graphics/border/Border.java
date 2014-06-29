package org.overflow.ui.lang.graphics.border;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 01:32
 */
public interface Border {
    public void renderBorder(final Widget widget, final Graphics2D g2);
    public Inset getInset();
}
