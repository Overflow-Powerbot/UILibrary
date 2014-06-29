package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.event.InputHandler;
import org.overflow.ui.lang.graphics.skin.resource.FontType;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.layout.Orientation;
import org.overflow.ui.lang.layout.manager.FlowLayout;
import org.overflow.ui.lang.layout.manager.LayoutManager;

import java.awt.event.MouseEvent;
import java.util.HashMap;

/**
 * Author: Tom
 * Date: 18/06/14
 * Time: 00:50
 */
public class WTabPanel extends WidgetContainer<WTabPanel> {

    private final HashMap<WButton, WPanel> tabMap = new HashMap<WButton, WPanel>();

    private final WPanel tabPanel;
    private final WPanel contentPanel;

    private WPanel visible;

    private Orientation orientation;

    public WTabPanel() {
        super(ResourceLocale.NIL);
        super.setLayoutManager(new FlowLayout(FlowLayout.VERTICAL_FLOW, 0));
        super.submitWidget(tabPanel = new WPanel(ResourceLocale.NIL).setLayoutManager(new FlowLayout(FlowLayout.HORIZONTAL_FLOW, 0)));
        super.submitWidget(contentPanel = new WPanel(ResourceLocale.NIL).setLayoutManager(new FlowLayout(FlowLayout.HORIZONTAL_FLOW, 0)));
        this.setOrientation(Orientation.LEFT);
    }

    @Override
    protected final WTabPanel get() {
        return this;
    }

    public final WTabPanel submitTab(String tab, final WPanel panel) {
        final WButton button = new WButton().submitWidget(new WLabel().setText(tab));
        button.submitMouseHandler(new InputHandler.Mouse(1) {

            @Override
            protected boolean execute(Widget widget, MouseEvent event) {
                if (event.getID() == MouseEvent.MOUSE_CLICKED) {
                    if (panel.setVisible(true) != visible && visible != null) {
                        visible.setVisible(false);
                    }
                    visible = panel;
                    return true;
                }
                return false;
            }
        });
        this.tabPanel.submitWidget(button);
        this.contentPanel.submitWidget(panel);
        if (this.visible == null) this.visible = panel;
        this.tabMap.put(button, panel.setVisible(this.visible == panel));
        return this;
    }

    @Override
    public final WTabPanel submitWidget(Widget widget, Object... args) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    @Override
    public final WTabPanel removeChildren(Widget... widgets) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    @Override
    public final WTabPanel setLayoutManager(LayoutManager manager) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    public final WTabPanel setOrientation(Orientation orientation) {
        if (this.orientation != orientation) {
            this.orientation = orientation;
            switch (orientation) {
                case TOP:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    ((FlowLayout) tabPanel.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    tabPanel.setZ(1);
                    contentPanel.setZ(0);
                    break;
                case BOTTOM:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    ((FlowLayout) tabPanel.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    tabPanel.setZ(0);
                    contentPanel.setZ(1);
                    break;
                case LEFT:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    ((FlowLayout) tabPanel.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    tabPanel.setZ(1);
                    contentPanel.setZ(0);
                    break;
                case RIGHT:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    ((FlowLayout) tabPanel.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    tabPanel.setZ(0);
                    contentPanel.setZ(1);
                    break;
            }
            this.invalidate();
        }
        return get();
    }

    private final static Error UNSUPPORTED_METHOD_ERROR = new Error("Unsupported method");
}
