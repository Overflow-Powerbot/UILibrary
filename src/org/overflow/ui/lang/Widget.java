package org.overflow.ui.lang;

import org.overflow.ui.Screen;
import org.overflow.ui.lang.action.UIAction;
import org.overflow.ui.lang.event.CoreMouseInputHandler;
import org.overflow.ui.lang.event.InputHandler;
import org.overflow.ui.lang.graphics.border.Border;
import org.overflow.ui.lang.graphics.renderer.Painter;
import org.overflow.ui.lang.graphics.skin.Skin;
import org.overflow.ui.lang.graphics.skin.renderer.WidgetRenderer;
import org.overflow.ui.lang.graphics.skin.resource.ColorResource;
import org.overflow.ui.lang.graphics.skin.resource.FontType;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.graphics.skin.resource.SkinResource;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.Queue;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Tom
 * Date: 12/06/14
 * Time: 16:41
 */
public abstract class Widget<T extends Widget> implements UIComponent, Comparable<Widget> {

    /**
     * Single threaded scheduled executor for which UIActions are submitted.
     *
     * @see UIAction;
     * @see #submitAction(UIAction)
     */
    private static final ScheduledExecutorService UPDATE_SERVICE = Executors.newSingleThreadScheduledExecutor();

    /**
     * Singular instance of AtomicInteger used to assign each widgets unique
     * identifier.
     *
     * @see #identifier
     * @see #hashCode()
     */
    private static final AtomicInteger ID_GEN = new AtomicInteger();


    /**
     * The current components object lock. Used during
     */
    private final Object treeLock = new Object();
    private final int identifier;

    private final Queue<InputHandler.Mouse> mouseHandlers = new PriorityQueue<InputHandler.Mouse>();
    private final Queue<InputHandler.Key> keyHandlers = new PriorityQueue<InputHandler.Key>();

    private final HashMap<UIAction, ScheduledFuture> futureActionMap = new HashMap<UIAction, ScheduledFuture>();

    private final ResourceLocale resourceLocale;

    private Screen screen;
    private WidgetContainer parent;

    private BufferedImage image;

    private int x;
    private int y;
    private int z;

    private int cachedParentX;
    private int cachedParentY;

    private int xOffset;
    private int yOffset;

    private int width;
    private int height;

    private Dimension minSize;
    private Dimension prefSize;
    private Dimension maxSize;

    private boolean minSizeSet;
    private boolean prefSizeSet;
    private boolean maxSizeSet;

    private WidgetRenderer widgetRenderer;

    private Font font;
    private FontType fontType;

    private Border border;
    private Color borderColor;

    private Paint backgroundPainter;
    private Paint foregroundPainter;

    private Rectangle bounds;

    private boolean valid;

    private boolean enabled = true;
    private boolean visible = true;

    private boolean hovered = false;
    private boolean pressed = false;
    private boolean focused = false;

    public Widget(ResourceLocale locale) {
        this.resourceLocale = locale;
        this.identifier = ID_GEN.getAndIncrement();
        this.submitMouseHandler(CoreMouseInputHandler.getCoreMouseHandler());
    }

    /**
     * Helper method to return the current component without casts
     */
    protected abstract T get();

    /**
     * Get the root widget for the current widgets component tree
     */
    public final Widget getRoot() {
        return parent == null ? this : parent.getRoot();
    }

    /**
     * Get the root lock of the component
     */
    public final Object getRootLock() {
        return getRoot().treeLock;
    }

    /**
     * Submits mouse handlers to the component
     */
    public final T submitMouseHandler(InputHandler.Mouse... handlers) {
        Collections.addAll(this.mouseHandlers, handlers);
        return get();
    }

    /**
     * Revokes mouse handlers from the component
     */
    public final T removeMouseHandler(InputHandler.Mouse... handlers) {
        this.mouseHandlers.removeAll(Arrays.asList(handlers));
        return get();
    }

    /**
     * Submits key handlers to the component
     */
    public final T submitKeyHandler(InputHandler.Key... handlers) {
        Collections.addAll(this.keyHandlers, handlers);
        return get();
    }

    /**
     * Revokes key handlers from the component
     */
    public final T removeKeyHandler(InputHandler.Key... handlers) {
        this.keyHandlers.removeAll(Arrays.asList(handlers));
        return get();
    }

    public final T submitAction(final UIAction action) {
        futureActionMap.put(action, UPDATE_SERVICE.schedule(new Runnable() {
            @Override
            public void run() {
                try {
                    action.update();
                    if (action.shouldRepeat()) {
                        submitAction(action);
                    } else futureActionMap.remove(action);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }, action.getUpdateRate(), TimeUnit.MILLISECONDS));
        return get();
    }

    public final T removeAction(final UIAction action) {
        final ScheduledFuture future = futureActionMap.get(action);
        if (future != null) future.cancel(true);
        return get();
    }

    public void dispose() {
        for (Map.Entry<UIAction, ScheduledFuture> entry : futureActionMap.entrySet()) {
            entry.getKey().setRepeat(false);
            removeAction(entry.getKey());
        }
    }

    /**
     * Gets the screen container for the current component tree.
     */
    public final Screen getScreen() {
        if (screen == null && parent != null) {
            this.screen = parent.getScreen();
        }
        return screen;
    }

    /**
     * Sets the screen container for the current component tree.
     */
    public final T setScreen(Screen screen) {
        this.screen = screen;
        return get();
    }

    /**
     * Gets the parent widget for the component
     */
    public final Widget getParent() {
        return parent;
    }

    /**
     * Sets the current widgets parent for the component tree
     */
    public final T setParent(WidgetContainer parent) {
        if (this.parent != parent) {
            this.parent = parent;
            this.invalidate();
        }
        return get();
    }

    /**
     * Gets the global x location of the current component
     */
    public final int getX() {
        if (parent != null && parent.getX() != cachedParentX) {
            this.x = (this.cachedParentX = this.parent.getX()) + this.getXOffset() + this.parent.getInset().getLeft();
        }
        return x;
    }

    /**
     * Gets the global y location of the current component
     */
    public final int getY() {
        if (parent != null && parent.getY() != cachedParentY) {
            this.y = (this.cachedParentY = this.parent.getY()) + this.getYOffset() + this.parent.getInset().getTop();
        }
        return y;
    }

    /**
     * Gets the z location of the current component. Used to prioritise the components
     * during mouse events ands rendering.
     */
    public final int getZ() {
        return z;
    }

    /**
     * Sets the z location of the current component
     */
    public final T setZ(int z) {
        if (this.z != z) {
            this.z = z;
            if (this.parent != null) {
                this.parent.children.remove(this);
                this.parent.children.add(this);
            } else if (this.screen != null) {
                this.screen.ensureOrder();
            }
            this.invalidate();
        }
        return get();
    }

    /**
     * Gets the x offset of the component
     */
    public final int getXOffset() {
        return xOffset;
    }

    /**
     * Sets the x offset of the component
     */
    public final T setXOffset(int xOffset) {
        if (xOffset >= 0 && this.xOffset != xOffset) {
            this.xOffset = xOffset;
            this.invalidate();
        }
        return get();
    }

    /**
     * Gets the y offset of the component
     */
    public final int getYOffset() {
        return yOffset;
    }

    /**
     * Sets the y offset of the component
     */
    public final T setYOffset(int yOffset) {
        if (yOffset >= 0 && this.yOffset != yOffset) {
            this.yOffset = yOffset;
            this.invalidate();
        }
        return get();
    }

    public final int getWidth() {
        return width;
    }

    public final int getHeight() {
        return height;
    }

    /**
     * Gets the minimum dimensions of the current component
     */
    public final Dimension getMinimumSize() {
        return minSize;
    }

    /**
     * Sets the minimum dimensions of the current component
     */
    public final T setMinimumSize(Dimension minSize) {
        if (this.minSize != minSize) {
            this.minSize = minSize;
            this.minSizeSet = minSize != null;
            this.invalidate();
        }
        return get();
    }

    /**
     * Calculates the minimum dimensions of the current component
     */
    protected Dimension calculateMinimumSize() {
        return widgetRenderer != null ? widgetRenderer.getMinimumSize(this) : new Dimension(getWidth(), getHeight());
    }

    /**
     * Gets the preffered dimensions of the current component
     */
    public final Dimension getPrefferedSize() {
        return prefSize;
    }

    /**
     * Sets the preffered dimensions of the current component
     */
    public final T setPrefferedSize(Dimension prefSize) {
        if (this.prefSize != prefSize) {
            this.prefSize = prefSize;
            this.prefSizeSet = prefSize != null;
            this.invalidate();
        }
        return get();
    }

    /**
     * Calculates the preffered dimensions of the current component
     */
    protected Dimension calculatePreferredSize() {
        Dimension d = null;
        if (this.widgetRenderer != null) {
            d = this.widgetRenderer.getMinimumSize(this);
        }
        return d != null ? d : getMinimumSize();
    }

    /**
     * Gets the maximum dimensions of the current component
     */
    public final Dimension getMaximumSize() {
        return maxSize;
    }

    /**
     * Sets the maximum dimensions of the current component
     */
    public final T setMaximumSize(Dimension maxSize) {
        if (this.maxSize != maxSize) {
            this.maxSize = maxSize;
            this.maxSizeSet = maxSize != null;
            this.invalidate();
        }
        return get();
    }

    /**
     * Calculates the maximum dimensions of the current component
     */
    protected Dimension calculateMaximumSize() {
        return new Dimension(Short.MAX_VALUE, Short.MAX_VALUE);
    }

    /**
     * Gets the current components Inset
     */
    public final Inset getInset() {
        return border == null ? Inset.getDefaultInset() : border.getInset();
    }

    public final Font getFont() {
        return font;
    }

    public final T setFont(Font font) {
        if (this.font != font) {
            this.font = font;
            this.invalidate();
        }
        return get();
    }

    public final FontType getFontType() {
        return fontType == null ? FontType.TEXT : fontType;
    }

    public final T setFontType(FontType fontType) {
        if (this.fontType != fontType) {
            this.fontType = fontType;
            this.invalidate();
        }
        return get();
    }

    public final Color getBorderColor() {
        return borderColor;
    }

    public final T setBorderColor(Color borderColor) {
        if (this.borderColor != borderColor) {
            this.borderColor = borderColor;
            this.invalidate();
        }
        return get();
    }

    public final Paint getBackgroundPainter() {
        return backgroundPainter;
    }

    public final T setBackgroundPainter(Paint backgroundPainter) {
        if (this.backgroundPainter != backgroundPainter) {
            this.backgroundPainter = backgroundPainter;
            this.invalidate();
        }
        return get();
    }

    public final Paint getForegroundPainter() {
        return foregroundPainter;
    }

    public final T setForegroundPainter(Paint foregroundPainter) {
        if (this.foregroundPainter != foregroundPainter) {
            this.foregroundPainter = foregroundPainter;
            this.invalidate();
        }
        return get();
    }

    /**
     * Sets the current components Inset
     */
    public final T setBorder(Border border) {
        if (this.border != border) {
            this.border = border;
            this.invalidate();
        }
        return get();
    }

    /**
     * Gets the current bounds of the component
     */
    public final Rectangle getBounds() {
        if (bounds == null) {
            this.bounds = new Rectangle();
        }
        this.bounds.setBounds(x, y, width, height);
        return bounds;
    }

    /**
     * Checks whether the specified co-ordinates are within the bounds of the component
     */
    public final boolean contains(int x, int y) {
        return widgetRenderer != null ? widgetRenderer.contains(this, x, y) : getBounds().contains(x, y);
    }

    /**
     * Checks whether the current component is valid
     */
    public final boolean isValid() {
        return valid;
    }

    /**
     * Checks whether the current component is enabled
     */
    public final boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets whether the current component is enabled
     */
    public final T setEnabled(boolean enabled) {
        if (this.enabled != enabled) {
            this.enabled = enabled;
            this.invalidate();
        }
        return get();
    }

    /**
     * Checks whether the current component is visible
     */
    public final boolean isVisible() {
        return visible;
    }

    /**
     * Sets whether the current component is visible
     */
    public final T setVisible(boolean visible) {
        if (!(this.visible = visible)) this.invalidate();
        return get();
    }

    /**
     * Toggles the component visiblity
     */
    public final T toggleVisibility() {
        if (this.visible = !visible) this.invalidate();
        return get();
    }

    /**
     * Checks whether the current component is hovered
     */
    public final boolean isHovered() {
        return hovered;
    }

    /**
     * Sets whether the current component is hovered
     */
    public final T setHovered(boolean hovered) {
        if (this.hovered != hovered) {
            this.hovered = hovered;
            this.invalidate();
        }
        return get();
    }

    /**
     * Checks whether the current component is pressed
     */
    public final boolean isPressed() {
        return pressed;
    }

    /**
     * Sets whether the current component is pressed
     */
    public final T setPressed(boolean pressed) {
        if (this.pressed != pressed) {
            this.pressed = pressed;
            this.invalidate();
        }
        return get();
    }

    /**
     * Checks whether the current component is focused
     */
    public final boolean isFocused() {
        return focused;
    }

    /**
     * Sets whether the current component is focused
     */
    public final T setFocused(boolean hasFocus) {
        if (this.focused != hasFocus) {
            this.focused = hasFocus;
            this.invalidate();
        }
        return get();
    }

    /**
     * Invalidates the current component
     */
    public final T invalidate() {
        if (this.valid) {
            this.minSize = minSizeSet ? minSize : null;
            this.prefSize = prefSizeSet ? prefSize : null;
            this.maxSize = maxSizeSet ? maxSize : null;
            if (this.parent != null) parent.invalidate();
            this.valid = false;
        }
        return get();
    }

    /**
     * Validates the current component
     */
    public final void validate() {
        if (this.valid) return;
        synchronized (this.getRootLock()) {
            Skin currentSkin = getScreen().getCurrentSkin();
            if (this.resourceLocale != null) {
                this.widgetRenderer = currentSkin.getValue(resourceLocale.getRenderLocale(), WidgetRenderer.class);
                if (this.widgetRenderer != null) {
                    if (font == null || font instanceof SkinResource) {
                        setFont(currentSkin.<Font>getValue(resourceLocale.getFontLocale(getFontType()), Font.class));
                    }
                    if (border == null || border instanceof SkinResource) {
                        setBorder(currentSkin.<Border>getValue(resourceLocale.getBorderLocale(), Border.class));
                    }
                    if (borderColor == null || borderColor instanceof SkinResource) {
                        setBorderColor(currentSkin.<ColorResource>getValue(resourceLocale.getBorderColorLocale(), ColorResource.class));
                    }
                    if (foregroundPainter == null || foregroundPainter instanceof SkinResource) {
                        final Painter painter = currentSkin.<Painter>getValue(resourceLocale.getForegroundPainterLocale(), Painter.class);
                        if (painter != null) setForegroundPainter(painter.get(this));
                    }
                    if (backgroundPainter == null || backgroundPainter instanceof SkinResource) {
                        final Painter painter = currentSkin.<Painter>getValue(resourceLocale.getBackgroundPainterLocale(), Painter.class);
                        if (painter != null) setBackgroundPainter(painter.get(this));
                    }
                }
            }
            this.x = Math.max(0, getXOffset() + (parent != null ? parent.getX() + parent.getInset().getLeft() : 0));
            this.y = Math.max(0, getYOffset() + (parent != null ? parent.getY() + parent.getInset().getTop() : 0));
            this.validateChildren();
            this.minSize = (minSize == null) ? minSize = calculateMinimumSize() : minSize;
            this.prefSize = (prefSize == null) ? prefSize = calculatePreferredSize() : prefSize;
            this.maxSize = (maxSize == null) ? maxSize = calculateMaximumSize() : maxSize;
            this.width = Math.min(Math.max(minSize.width, prefSize.width), maxSize.width);
            this.height = Math.min(Math.max(minSize.height, prefSize.height), maxSize.height);
            this.image = null;
            this.valid = true;
        }
    }

    void validateChildren() {
    }

    public final void render(Graphics2D g2) {
        synchronized (getRootLock()) {
            if (this.isVisible()) {
                this.validate();
                this.renderComponent(g2);
                this.renderChildren(g2);
                this.renderBorder(g2);
            }
        }
    }

    private final void renderComponent(final Graphics2D graphics) {
        if (widgetRenderer == null) {
            return;
        }
        if ((image == null || image.getWidth() != getWidth() || image.getHeight() != getHeight()) && (getHeight() > 0 && getWidth() > 0)) {
            image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TRANSLUCENT);
            Graphics2D g2 = (Graphics2D) image.getGraphics();
            try {
                g2.translate(-getX(), -getY());
                widgetRenderer.renderWidget(this, g2);
            } finally {
                g2.dispose();
            }
        }
        if (image != null) graphics.drawImage(image, getX(), getY(), null);
    }

    void renderChildren(final Graphics2D graphics) {
    }

    protected final void renderBorder(final Graphics2D graphics) {
        if (border == null) {
            return;
        }
        Graphics2D g2 = (Graphics2D) graphics.create();
        try {
            border.renderBorder(this, g2);
        } finally {
            g2.dispose();
        }
    }

    protected boolean processAWTEvent(AWTEvent event) {
        if (!this.enabled || !this.visible) {
            return false;
        }
        switch (event.getID()) {
            case MouseEvent.MOUSE_CLICKED:
            case MouseEvent.MOUSE_PRESSED:
            case MouseEvent.MOUSE_RELEASED:
            case MouseEvent.MOUSE_MOVED:
            case MouseEvent.MOUSE_DRAGGED:
            case MouseEvent.MOUSE_EXITED:
            case MouseEvent.MOUSE_ENTERED:
                processMouseEvent((MouseEvent) event);
                break;
            case KeyEvent.KEY_PRESSED:
            case KeyEvent.KEY_RELEASED:
            case KeyEvent.KEY_TYPED:
                processKeyEvent((KeyEvent) event);
                break;
        }
        return true;
    }

    private final void processMouseEvent(MouseEvent e) {
        for (InputHandler.Mouse handler : mouseHandlers) {
            handler.handleEvent(this, e);
        }
    }

    private final void processKeyEvent(KeyEvent e) {
        for (InputHandler.Key handler : keyHandlers) {
            handler.handleEvent(this, e);
        }
    }


    public final void keyTyped(KeyEvent e) {
        this.processAWTEvent(e);
    }

    public final void keyPressed(KeyEvent e) {
        this.processAWTEvent(e);
    }

    public final void keyReleased(KeyEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseClicked(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mousePressed(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseReleased(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseEntered(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseExited(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseDragged(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final void mouseMoved(MouseEvent e) {
        this.processAWTEvent(e);
    }

    public final int compareTo(Widget o) {
        return Integer.compare(o.z, z);
    }

    public final boolean equals(Object o) {
        return o == this;
    }

    public final int hashCode() {
        return identifier;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(getClass().getSimpleName()).append("{");
        sb.append("x=").append(x);
        sb.append(", y=").append(y);
        sb.append(", z=").append(z);
        sb.append(", width=").append(width);
        sb.append(", height=").append(height);
        sb.append(", xOffset=").append(xOffset);
        sb.append(", yOffset=").append(yOffset);
        sb.append('}');
        return sb.toString();
    }
}
