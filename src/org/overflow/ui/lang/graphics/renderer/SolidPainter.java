package org.overflow.ui.lang.graphics.renderer;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.PaintResource;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 03:07
 */
public class SolidPainter implements Painter {

    private final PaintResource resource;

    public SolidPainter(Color color) {
        this.resource = new PaintResource(color);
    }

    @Override
    public final PaintResource get(Widget widget) {
        return resource;
    }
}
