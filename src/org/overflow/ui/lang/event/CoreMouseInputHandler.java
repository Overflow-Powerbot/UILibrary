package org.overflow.ui.lang.event;

import org.overflow.ui.lang.Widget;

import java.awt.event.MouseEvent;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 17:38
 */
public class CoreMouseInputHandler extends InputHandler.Mouse<Widget> {

    @Override
    protected boolean execute(Widget widget, MouseEvent event) {
        widget.setHovered(widget.contains(event.getX(), event.getY()));
        widget.setFocused(event.getID() == MouseEvent.MOUSE_PRESSED ? widget.isHovered() : widget.isFocused());
        widget.setPressed(((event.getID() != MouseEvent.MOUSE_EXITED) && widget.isPressed() && event.getID() != MouseEvent.MOUSE_RELEASED) || widget.isHovered() && event.getID() == MouseEvent.MOUSE_PRESSED);
        return false;
    }

    public static CoreMouseInputHandler getCoreMouseHandler() {
        return CORE_MOUSE_HANDLER;
    }

    private static final CoreMouseInputHandler CORE_MOUSE_HANDLER = new CoreMouseInputHandler();

}
