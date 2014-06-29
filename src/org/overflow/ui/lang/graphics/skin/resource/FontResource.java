package org.overflow.ui.lang.graphics.skin.resource;

import java.awt.*;
import java.text.AttributedCharacterIterator;
import java.util.Map;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 18:33
 */
public class FontResource extends Font implements SkinResource {
    public FontResource(String name, int style, int size) {
        super(name, style, size);
    }

    public FontResource(Map<? extends AttributedCharacterIterator.Attribute, ?> attributes) {
        super(attributes);
    }

    public FontResource(Font font) {
        super(font);
    }
}
