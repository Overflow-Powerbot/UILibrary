package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

import java.awt.image.BufferedImage;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 00:05
 */
public class WIcon extends Widget<WIcon> {

    private BufferedImage image;

    public WIcon() {
        super(ResourceLocale.ICON);
    }

    @Override
    protected final WIcon get() {
        return this;
    }

    public final BufferedImage getImage() {
        return image;
    }

    public final WIcon setImage(BufferedImage image) {
        if (image != null && (this.image == null || this.image.getWidth() != image.getHeight() || this.image.getHeight() != image.getHeight())) {
            this.image = image;
            this.invalidate();
        }
        return get();
    }

}
