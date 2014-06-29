package org.overflow.ui.lang.graphics.skin.resource.border;

import org.overflow.ui.lang.graphics.border.LineBorder;
import org.overflow.ui.lang.graphics.skin.resource.SkinResource;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 02:12
 */
public class LineBorderResource extends LineBorder implements SkinResource {
    public LineBorderResource(int thickness, Inset inset) {
        super(thickness, inset);
    }

    public LineBorderResource(int thickness) {
        super(thickness);
    }
}
