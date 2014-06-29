package org.overflow.ui.lang.graphics.skin.resource;

import org.overflow.ui.lang.graphics.skin.resource.SkinResource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.awt.image.ColorModel;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 03:01
 */
public class PaintResource<T extends Paint> implements SkinResource, Paint {

    private final T delegate;

    public PaintResource(final T delegate) {
        this.delegate = delegate;
    }

    public T getDelegate() {
        return delegate;
    }

    @Override
    public PaintContext createContext(ColorModel cm, Rectangle deviceBounds, Rectangle2D userBounds, AffineTransform xform, RenderingHints hints) {
        return delegate.createContext(cm, deviceBounds, userBounds, xform, hints);
    }

    @Override
    public int getTransparency() {
        return delegate.getTransparency();
    }
}
