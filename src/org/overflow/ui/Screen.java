package org.overflow.ui;

import org.overflow.ui.lang.UIComponent;
import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.graphics.skin.Skin;
import org.overflow.ui.lang.graphics.skin.basic.BasicSkin;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.util.*;
import java.util.Queue;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 16:31
 */
public class Screen implements UIComponent {

    private final LinkedList<Widget> validWidgets = new LinkedList<Widget>();

    private Skin currentSkin = null;
    private Component currentComponent = null;

    /**
     * Attach the component on which the screen will be rendered
     */
    public final Screen attachComponent(final Component component) {
        this.currentComponent = component;
        return this;
    }

    /**
     * Submits the passed widgets to the current container and
     * sets the widgets container
     */
    public final Screen submitWidget(final Widget... widgets) {
        for (Widget widget : widgets) {
            if (!validWidgets.contains(widget))
                validWidgets.add(widget.setScreen(this));
        }
        return this.ensureOrder();
    }

    public final Screen revokeWidget(final Widget... widgets) {
        for (Widget w : widgets) {
            validWidgets.remove(w);
        }
        return this;
    }

    public final Screen ensureOrder() {
        Collections.sort(validWidgets);
        return this;
    }


    public final synchronized Skin getCurrentSkin() {
        if (this.currentSkin == null) {
            this.currentSkin = new BasicSkin();
        }
        return currentSkin;
    }

    public final Screen setSkin(Skin skin) {
        if (this.currentSkin != skin) {
            this.currentSkin = skin;
            Queue<Widget> openList = new PriorityQueue<Widget>(validWidgets);
            while (!openList.isEmpty()) {
                Widget w = openList.poll().invalidate();
                if (w instanceof WidgetContainer) openList.addAll(((WidgetContainer) w).getChildren());
            }
        }
        return this;
    }

    @Override
    public final int getWidth() {
        if (currentComponent == null) {
            throw INVALID_COMPONENT_ERROR;
        }
        return currentComponent.getWidth();
    }

    @Override
    public final int getHeight() {
        if (currentComponent == null) {
            throw INVALID_COMPONENT_ERROR;
        }
        return currentComponent.getHeight();
    }

    @Override
    public void dispose() {
        for (Widget w : validWidgets) {
            w.dispose();
        }
    }

    @Override
    public final void render(Graphics2D g2) {
        g2.setRenderingHints(RENDER_HINTS);
        for (Widget w : validWidgets) {
            w.render(g2);
        }
    }

    @Override
    public final void keyTyped(KeyEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().keyTyped(e);
        }
    }

    @Override
    public final void keyPressed(KeyEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().keyPressed(e);
        }

    }

    @Override
    public final void keyReleased(KeyEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().keyReleased(e);
        }

    }

    @Override
    public final void mouseClicked(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseClicked(e);
        }

    }

    @Override
    public final void mousePressed(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mousePressed(e);
        }

    }

    @Override
    public final void mouseReleased(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseReleased(e);
        }

    }

    @Override
    public final void mouseEntered(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseEntered(e);
        }

    }

    @Override
    public final void mouseExited(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseExited(e);
        }

    }

    @Override
    public final void mouseDragged(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseDragged(e);
        }

    }

    @Override
    public final void mouseMoved(MouseEvent e) {
        final Iterator<Widget> reverseIter = validWidgets.descendingIterator();
        while (reverseIter.hasNext()) {
            reverseIter.next().mouseMoved(e);
        }
    }

    private final static Error INVALID_COMPONENT_ERROR = new Error("Invalid / null component attached to the screen. Unable to get dimensions");

    private static final Map<RenderingHints.Key, Object> RENDER_HINTS = new HashMap<RenderingHints.Key, Object>() {{
        this.put(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        this.put(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
    }};

}
