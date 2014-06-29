package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.event.InputHandler;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

import java.awt.event.MouseEvent;

/**
 * Author: Tom
 * Date: 14/06/14
 * Time: 01:39
 */
public class WCheckbox extends Widget<WCheckbox> {

    public static final int LEADING = 0;
    public static final int TRAILING = 1;

    private int allignment = 0;
    private boolean selected = false;

    public WCheckbox() {
        super(ResourceLocale.CHECKBOX);
        this.submitMouseHandler(SelectionHandler.HANDLER_INSTANCE);
    }

    @Override
    protected final WCheckbox get() {
        return this;
    }

    public final boolean isSelected() {
        return selected;
    }

    public final WCheckbox setSelected(boolean selected) {
        if (this.selected != selected) {
            this.selected = selected;
            this.invalidate();
        }
        return get();
    }

    public final int getAllignment() {
        return allignment;
    }

    public final WCheckbox setAllignment(int allignment) {
        if (this.allignment != allignment) {
            switch (allignment) {
                case LEADING:
                case TRAILING:
                    this.allignment = allignment;
                    this.invalidate();
                    break;
                default:
                    throw new RuntimeException(String.format("Invalid allignment value => %s", allignment));
            }
        }
        return get();
    }

    private static class SelectionHandler extends InputHandler.Mouse<WCheckbox> {
        static final SelectionHandler HANDLER_INSTANCE = new SelectionHandler();

        private SelectionHandler() {
            super(1);
        }

        @Override
        protected final boolean execute(WCheckbox widget, MouseEvent event) {
            if (event.getID() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseEvent.BUTTON1) {
                widget.setSelected(!widget.isSelected());
                return true;
            }
            return false;
        }
    }
}
