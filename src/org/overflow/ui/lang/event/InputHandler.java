package org.overflow.ui.lang.event;

import org.overflow.ui.lang.Widget;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 16:40
 */
public abstract class InputHandler<T extends Widget, E extends InputEvent> implements Comparable<InputHandler> {

    private final int priority;

    /**
     * Default constructor for all user made event handlers
     */
    private InputHandler(int priority) {
        this.priority = Math.max(priority, 1);
    }

    /**
     * Private constructor for core event handlers to ensure priority
     * is maintained.
     */
    InputHandler() {
        this.priority = 0;
    }

    protected final boolean isCoreHandler() {
        return priority == 0;
    }

    /**
     * Asseses and executes the handler.
     */
    public final void handleEvent(final T widget, final E event) {
        if (valid(widget, event) && execute(widget, event)) {
            event.consume();
        }
    }

    /**
     * Assesses the passed event in respect to the supplied widget to see if
     * the handler should be executed.
     */
    abstract boolean valid(final T widget, final E event);

    /**
     * Executes the handlers response if @valid returns true.
     */
    protected abstract boolean execute(final T widget, final E event);

    @Override
    public final int compareTo(InputHandler o) {
        return Integer.compare(priority, o.priority);
    }

    public static abstract class Key<W extends Widget> extends InputHandler<W, KeyEvent> {
        public Key(int priority) {
            super(priority);
        }

        Key() {
        }

        @Override
        boolean valid(W widget, KeyEvent event) {
            return isCoreHandler() || (!event.isConsumed() && widget.isFocused());
        }

        @Override
        protected abstract boolean execute(W widget, KeyEvent event);
    }

    public static abstract class Mouse<W extends Widget> extends InputHandler<W, MouseEvent> {
        public Mouse(int priority) {
            super(priority);
        }

        Mouse() {
        }

        @Override
        boolean valid(W widget, MouseEvent event) {
            return isCoreHandler() || (!event.isConsumed() && widget.contains(event.getX(), event.getY()));
        }

        @Override
        protected abstract boolean execute(W widget, MouseEvent event);
    }
}
