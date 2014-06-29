package org.overflow.ui.lang.graphics.renderer;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.PaintResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 18:29
 */
public class GradientPainter implements Painter<Widget> {

    public static final int VERTICAL = 1;
    public static final int HORIZONTAL = 2;

    private final Color primary;
    private final Color secondary;
    private final int direction;
    private final boolean highlight;

    public GradientPainter(final Color primary, final Color secondary, final int direction, final boolean highlight) {
        this.primary = primary;
        this.secondary = secondary;
        this.direction = direction;
        this.highlight = highlight;
    }

    @Override
    public PaintResource get(Widget widget) {
        final boolean temp = (highlight && widget.isHovered());
        return new PaintResource(new GradientPaint(widget.getX(), widget.getY(), (temp) ? primary.brighter() : primary, widget.getX() + ((direction & HORIZONTAL) == HORIZONTAL ? widget.getWidth() : 0), widget.getY() + ((direction & VERTICAL) == VERTICAL ? widget.getHeight() : 0), (temp) ? secondary.brighter() : secondary));
    }

}
