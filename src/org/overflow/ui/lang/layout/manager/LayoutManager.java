package org.overflow.ui.lang.layout.manager;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 18:15
 */
public interface LayoutManager {

    public void addComponent(final Widget c, final Object... args);

    public void removeComponent(final Widget c) ;

    public void layout(final WidgetContainer container);

    public Dimension getPrefferedSize(final WidgetContainer container);
}
