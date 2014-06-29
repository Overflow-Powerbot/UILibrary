package org.overflow.ui.lang.graphics.renderer;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.PaintResource;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 03:08
 */
public class TexturePainter implements Painter {

    private BufferedImage image;

    public TexturePainter(BufferedImage image) {
        this.image = image;
    }

    @Override
    public PaintResource get(Widget widget) {
        return new PaintResource(new TexturePaint(image,widget.getBounds()));
    }
}
