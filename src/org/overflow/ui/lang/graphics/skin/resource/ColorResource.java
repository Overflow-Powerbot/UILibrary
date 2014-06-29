package org.overflow.ui.lang.graphics.skin.resource;

import java.awt.*;
import java.awt.color.ColorSpace;

/**
 * Author: Tom
 * Date: 14/06/14
 * Time: 02:35
 */
public class ColorResource extends Color implements SkinResource {
    public ColorResource(int r, int g, int b) {
        super(r, g, b);
    }

    public ColorResource(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public ColorResource(int rgb) {
        super(rgb);
    }

    public ColorResource(int rgba, boolean hasalpha) {
        super(rgba, hasalpha);
    }

    public ColorResource(float r, float g, float b) {
        super(r, g, b);
    }

    public ColorResource(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public ColorResource(ColorSpace cspace, float[] components, float alpha) {
        super(cspace, components, alpha);
    }
}
