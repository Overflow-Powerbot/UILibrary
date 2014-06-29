package org.overflow.ui.lang.graphics.skin;

import java.util.Properties;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 01:40
 */
public abstract class Skin extends Properties {

    public Skin(){
        loadSkin();
    }

    public final void loadSkin() {
        loadResources();
        loadColors();
        loadFonts();
    }

    public final  <T> T getValue(final String key, final Class type) {
        final Object o = get(key);
        return o != null && type.isInstance(o) ? (T) o : null;
    }

    protected abstract void loadResources();

    protected abstract void loadColors();

    protected abstract void loadFonts();
}
