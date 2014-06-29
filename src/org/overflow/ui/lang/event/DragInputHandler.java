package org.overflow.ui.lang.event;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;

import java.awt.*;
import java.awt.event.MouseEvent;

/**
 * Author: Tom
 * Date: 14/06/14
 * Time: 22:08
 */
public final class DragInputHandler extends InputHandler.Mouse<Widget> {

    private WidgetContainer parent;
    private Point pressedPoint;

    private int initialXOffset;
    private int initialYOffset;

    public DragInputHandler(final WidgetContainer container) {
        super(1);
        this.parent = container;
    }

    public DragInputHandler() {
        this(null);
    }

    @Override
    boolean valid(Widget widget, MouseEvent event) {
        return super.valid(widget, event) || (!event.isConsumed() && widget.isPressed());
    }

    @Override
    protected boolean execute(Widget widget, MouseEvent event) {
        final Widget c = parent != null ? parent : widget;
        switch (event.getID()) {
            case MouseEvent.MOUSE_EXITED:
            case MouseEvent.MOUSE_RELEASED:
                pressedPoint = null;
                initialXOffset = 0;
                initialYOffset = 0;
                break;
            case MouseEvent.MOUSE_PRESSED:
                if (event.getButton() == MouseEvent.BUTTON3) {
                    pressedPoint = widget.isPressed() ? event.getPoint() : null;
                    initialXOffset = c.getXOffset();
                    initialYOffset = c.getYOffset();
                    event.consume();
                    return true;
                }
                return false;
            case MouseEvent.MOUSE_DRAGGED:
                if (pressedPoint == null) {
                    break;
                }
                c.setXOffset(initialXOffset + (event.getX() - pressedPoint.x));
                c.setYOffset(initialYOffset + (event.getY() - pressedPoint.y));
                event.consume();
                c.invalidate();
                return true;
        }
        return false;
    }
}
