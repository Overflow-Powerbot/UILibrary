package org.overflow.ui.lang.graphics.skin.basic;


import org.overflow.ui.lang.graphics.renderer.SolidPainter;
import org.overflow.ui.lang.graphics.skin.Skin;
import org.overflow.ui.lang.graphics.skin.resource.ColorResource;
import org.overflow.ui.lang.graphics.skin.resource.FontResource;
import org.overflow.ui.lang.graphics.skin.resource.FontType;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;
import org.overflow.ui.lang.graphics.skin.resource.border.LineBorderResource;
import org.overflow.ui.lang.layout.Inset;

import java.awt.*;

/**
 * Author: Tom
 * Date: 13/06/14
 * Time: 02:19
 */
public class BasicSkin extends Skin {

    @Override
    protected void loadResources() {
        this.put(ResourceLocale.LABEL.getRenderLocale(), new BasicLabelRenderer());
        this.put(ResourceLocale.PANEL.getRenderLocale(), new BasicPanelRenderer());
        this.put(ResourceLocale.BUTTON.getRenderLocale(), new BasicButtonRenderer());
        this.put(ResourceLocale.CHECKBOX.getRenderLocale(), new BasicCheckboxRenderer());
        this.put(ResourceLocale.PROGRESS_BAR.getRenderLocale(), new BasicProgressBarRenderer());
        this.put(ResourceLocale.ICON.getRenderLocale(), new BasicIconRenderer());
        this.put(ResourceLocale.ARROW.getRenderLocale(), new BasicArrowRenderer());
    }

    @Override
    protected void loadColors() {
        //Label resources
        this.put(ResourceLocale.LABEL.getForegroundPainterLocale(), new SolidPainter(new Color(0x97C0CB)));
        //Panel resources
        this.put(ResourceLocale.PANEL.getBackgroundPainterLocale(), new SolidPainter(Color.DARK_GRAY.darker()));
        this.put(ResourceLocale.PANEL.getBorderLocale(), new LineBorderResource(2));
        this.put(ResourceLocale.PANEL.getBorderColorLocale(), new ColorResource(Color.GRAY.getRGB()));
        //Button Bar resource
        this.put(ResourceLocale.BUTTON.getBackgroundPainterLocale(), new SolidPainter(Color.DARK_GRAY.darker()));
        this.put(ResourceLocale.BUTTON.getForegroundPainterLocale(), new SolidPainter(Color.DARK_GRAY));
        this.put(ResourceLocale.BUTTON.getBorderLocale(), new LineBorderResource(2));
        this.put(ResourceLocale.BUTTON.getBorderColorLocale(), new ColorResource(Color.GRAY.getRGB()));
        //Checkbox resource
        this.put(ResourceLocale.CHECKBOX.getBackgroundPainterLocale(), new SolidPainter(Color.DARK_GRAY.darker()));
        this.put(ResourceLocale.CHECKBOX.getForegroundPainterLocale(), new SolidPainter(Color.GRAY.darker()));
        this.put(ResourceLocale.CHECKBOX.getBorderLocale(), new LineBorderResource(2));
        this.put(ResourceLocale.CHECKBOX.getBorderColorLocale(), new ColorResource(Color.GRAY.getRGB()));
        //Progress Bar resource
        this.put(ResourceLocale.PROGRESS_BAR.getBackgroundPainterLocale(), new SolidPainter(Color.RED.darker()));
        this.put(ResourceLocale.PROGRESS_BAR.getForegroundPainterLocale(), new SolidPainter(Color.GREEN.darker()));
        this.put(ResourceLocale.PROGRESS_BAR.getBorderLocale(), new LineBorderResource(2,new Inset(2,2,3,3)));
        this.put(ResourceLocale.PROGRESS_BAR.getBorderColorLocale(), new ColorResource(Color.GRAY.getRGB()));
        //Arrow resource
        this.put(ResourceLocale.ARROW.getBackgroundPainterLocale(), new SolidPainter(Color.DARK_GRAY.darker()));
        this.put(ResourceLocale.ARROW.getForegroundPainterLocale(), new SolidPainter(Color.LIGHT_GRAY));
        this.put(ResourceLocale.ARROW.getBorderLocale(), new LineBorderResource(1, Inset.getDefaultInset()));
        this.put(ResourceLocale.ARROW.getBorderColorLocale(), new ColorResource(Color.GRAY.getRGB()));
    }

    @Override
    protected void loadFonts() {
        this.put(ResourceLocale.LABEL.getFontLocale(FontType.TITLE), new FontResource(Font.SERIF, Font.PLAIN, 18));
        this.put(ResourceLocale.LABEL.getFontLocale(FontType.HEADING), new FontResource(Font.SERIF, Font.PLAIN, 16));
        this.put(ResourceLocale.LABEL.getFontLocale(FontType.TEXT), new FontResource(Font.SERIF, Font.PLAIN, 14));
    }
}
