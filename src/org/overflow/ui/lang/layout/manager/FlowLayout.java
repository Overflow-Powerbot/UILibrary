package org.overflow.ui.lang.layout.manager;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;
import java.util.Iterator;

/**
 * Author: Tom
 * Date: 14/06/14
 * Time: 00:27
 */
public class FlowLayout implements org.overflow.ui.lang.layout.manager.LayoutManager {

    public static final int HORIZONTAL_FLOW = 0;
    public static final int VERTICAL_FLOW = 1;

    private int flowType = HORIZONTAL_FLOW;
    private int spacer;

    public FlowLayout(int flowType, int spacer) {
        this.setFlowType(flowType);
        this.spacer = spacer;
    }

    public FlowLayout(int flowType) {
        this(flowType, 5);
    }

    public FlowLayout() {
        this(HORIZONTAL_FLOW, 5);
    }

    public int getFlowType() {
        return flowType;
    }

    public FlowLayout setFlowType(int flowType) {
        if (this.flowType != flowType && (flowType >= 0 && flowType < 2)) {
            this.flowType = flowType;
        }
        return this;
    }

    public int getSpacer() {
        return spacer;
    }

    public FlowLayout setSpacer(int spacer) {
        if (this.spacer != spacer) {
            this.spacer = spacer;
        }
        return this;
    }

    @Override
    public void addComponent(Widget c, Object... args) {
    }

    @Override
    public void removeComponent(Widget c) {
    }

    @Override
    public void layout(WidgetContainer container) {
        synchronized (container.getRootLock()) {
            Iterator<Widget> iter = container.getChildren().iterator();
            int xOffset = 0;
            int yOffset = 0;
            while (iter.hasNext()) {
                Widget cur = iter.next();
                if (!cur.isVisible())
                    continue;
                cur.setXOffset(xOffset).setYOffset(yOffset);
                switch (flowType) {
                    case HORIZONTAL_FLOW:
                        xOffset += cur.getWidth() + spacer;
                        break;
                    case VERTICAL_FLOW:
                        yOffset += cur.getHeight() + spacer;
                        break;
                }
            }
        }
    }

    @Override
    public Dimension getPrefferedSize(WidgetContainer container) {
        synchronized (container.getRootLock()) {
            Iterator<Widget> iter = container.getChildren().iterator();
            final Inset inset = container.getInset();
            int componentWidth = 0;
            int componentHeight = 0;
            while (iter.hasNext()) {
                Widget cur = iter.next();
                if (!cur.isVisible())
                    continue;
                switch (flowType) {
                    case HORIZONTAL_FLOW:
                        componentWidth += cur.getWidth() + (iter.hasNext() ? spacer : 0);
                        componentHeight = Math.max(componentHeight, cur.getHeight());
                        break;
                    case VERTICAL_FLOW:
                        componentHeight += cur.getHeight() + (iter.hasNext() ? spacer : 0);
                        componentWidth = Math.max(componentWidth, cur.getWidth());
                        break;
                }
            }
            return new Dimension(inset.getHorizontalTotal() + componentWidth, inset.getVerticalTotal() + componentHeight);
        }
    }
}
