package org.overflow.ui.component;

import org.overflow.ui.lang.Widget;
import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.event.DragInputHandler;
import org.overflow.ui.lang.event.InputHandler;
import org.overflow.ui.lang.graphics.skin.resource.FontType;
import org.overflow.ui.lang.layout.Orientation;
import org.overflow.ui.lang.layout.manager.FlowLayout;
import org.overflow.ui.lang.layout.manager.LayoutManager;

import java.awt.event.MouseEvent;

/**
 * Author: Tom
 * Date: 19/06/14
 * Time: 00:42
 */
public class WFrame extends WidgetContainer<WFrame> {

    private final WLabel title;
    private final WButton titleButton;
    private final WPanel contentPanel;

    private Orientation orientation;

    public WFrame() {
        super(null);
        super.setLayoutManager(new FlowLayout(FlowLayout.VERTICAL_FLOW, 0));
        super.submitWidget(titleButton = new WButton().setLayoutManager(new FlowLayout(FlowLayout.HORIZONTAL_FLOW, 0)).submitWidget(title = new WLabel().setFontType(FontType.TITLE)));
        super.submitWidget(contentPanel = new WPanel().setLayoutManager(new FlowLayout(FlowLayout.VERTICAL_FLOW, 5)));
        this.titleButton.submitMouseHandler(new InputHandler.Mouse<WButton>(1) {
            @Override
            protected boolean execute(WButton button, MouseEvent event) {
                if (event.getID() == MouseEvent.MOUSE_CLICKED && event.getButton() == MouseEvent.BUTTON1) {
                    contentPanel.toggleVisibility();
                    return true;
                }
                return false;
            }
        });
        this.titleButton.submitMouseHandler(new DragInputHandler(this));
        this.setOrientation(Orientation.TOP);
    }

    @Override
    protected final WFrame get() {
        return this;
    }

    public final WLabel getTitle() {
        return title;
    }

    public final WButton getTitleButton() {
        return titleButton;
    }

    public final WPanel getContentPanel() {
        return contentPanel;
    }

    @Override
    public final WFrame submitWidget(Widget widget, Object... args) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    @Override
    public final WFrame removeChildren(Widget... widgets) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    @Override
    public final WFrame setLayoutManager(LayoutManager manager) {
        throw UNSUPPORTED_METHOD_ERROR;
    }

    public final WFrame setOrientation(Orientation orientation) {
        if (this.orientation != orientation) {
            this.orientation = orientation;
            switch (orientation) {
                case TOP:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    ((FlowLayout) this.titleButton.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    this.titleButton.setZ(1);
                    this.contentPanel.setZ(0);
                    break;
                case BOTTOM:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    ((FlowLayout) this.titleButton.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    this.titleButton.setZ(0);
                    this.contentPanel.setZ(1);
                    break;
                case LEFT:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    ((FlowLayout) this.titleButton.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    this.titleButton.setZ(1);
                    this.contentPanel.setZ(0);
                    break;
                case RIGHT:
                    ((FlowLayout) super.getLayoutManager()).setFlowType(FlowLayout.HORIZONTAL_FLOW);
                    ((FlowLayout) this.titleButton.getLayoutManager()).setFlowType(FlowLayout.VERTICAL_FLOW);
                    this.titleButton.setZ(0);
                    this.contentPanel.setZ(1);
                    break;
            }
            this.invalidate();
        }
        return get();
    }

    private final static Error UNSUPPORTED_METHOD_ERROR = new Error("Unsupported method");
}
