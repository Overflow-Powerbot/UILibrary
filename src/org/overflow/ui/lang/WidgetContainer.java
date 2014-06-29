package org.overflow.ui.lang;

import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.layout.Inset;
import org.overflow.ui.lang.layout.manager.LayoutManager;

import java.awt.*;
import java.util.Collection;
import java.util.Collections;
import java.util.PriorityQueue;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 18:15
 */
public abstract class WidgetContainer<T extends WidgetContainer> extends Widget<T> {

    final PriorityQueue<Widget> children = new PriorityQueue<Widget>();

    private LayoutManager layoutManager;

    public WidgetContainer(ResourceLocale locale) {
        super(locale);
    }

    public T submitWidget(Widget widget, Object... args) {
        this.children.add(widget.setParent(this));
        if (layoutManager != null) this.layoutManager.addComponent(widget, args);
        return get();
    }

    public T removeChildren(Widget... widgets) {
        final boolean remove = layoutManager != null;
        for (Widget w : widgets) {
            this.children.remove(w);
            if (remove) this.layoutManager.removeComponent(w);
        }
        return get();
    }

    public T setLayoutManager(LayoutManager manager) {
        if (this.layoutManager != manager) {
            this.layoutManager = manager;
            this.invalidate();
        }
        return get();
    }

    public final LayoutManager getLayoutManager() {
        return layoutManager;
    }

    public final Collection<Widget> getChildren() {
        return Collections.unmodifiableCollection(children);
    }

    @Override
    public final void dispose() {
        for (Widget w : children) {
            w.dispose();
        }
        super.dispose();
    }

    @Override
    protected final Dimension calculateMinimumSize() {
        if (layoutManager == null) {
            final Inset i = getInset();
            int maxX = 0;
            int maxY = 0;
            for (Widget w : children) {
                if (!w.isVisible()) {
                    continue;
                }
                maxX = Math.max(maxX, (w.getX() + w.getWidth()));
                maxY = Math.max(maxY, (w.getY() + w.getHeight()));
            }
            return new Dimension(maxX - getX() + (i != null ? i.getHorizontalTotal() : 0), maxY - getY() + (i != null ? i.getVerticalTotal() : 0));
        }
        return layoutManager.getPrefferedSize(this);
    }

    final void validateChildren() {
        for (Widget w : children) {
            w.validate();
        }
        if (this.layoutManager != null) this.layoutManager.layout(this);
    }

    @Override
    final void renderChildren(Graphics2D graphics) {
        for (Widget w : children) {
            w.render(graphics);
        }
    }


    @Override
    protected final boolean processAWTEvent(AWTEvent event) {
        if (this.isVisible() && this.isEnabled()) {
            for (Widget w : children) {
                w.processAWTEvent(event);
            }
            return super.processAWTEvent(event);
        }
        return false;

    }
}
