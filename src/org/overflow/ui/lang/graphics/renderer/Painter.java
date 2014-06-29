package org.overflow.ui.lang.graphics.renderer;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.graphics.skin.resource.PaintResource;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 01:03
 */
public interface Painter<T extends Widget> {
    public PaintResource get(T widget);
}
