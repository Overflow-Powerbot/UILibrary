package org.overflow.ui.lang;

import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 16:28
 */
public interface UIComponent extends MouseListener,MouseMotionListener,KeyListener{

    /**
     * Gets the current width of the UIComponent
     */
    public int getWidth();

    /**
     * Gets the current height of the UIComponent
     */
    public int getHeight();

    /**
     * Renders the current UIComponent using the supplied Graphics2D parameter
     */
    public void render(final Graphics2D g2);

    /**
     * Disposes of the UI component
     */
    public void dispose();
}
