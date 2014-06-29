package org.overflow.ui.component;

import org.overflow.ui.lang.WidgetContainer;
import org.overflow.ui.lang.graphics.skin.resource.ResourceLocale;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 18:37
 */
public class WProgressBar extends WidgetContainer<WProgressBar> {

    private final AtomicInteger percentage = new AtomicInteger(0);

    public WProgressBar() {
        super(ResourceLocale.PROGRESS_BAR);
    }

    @Override
    protected WProgressBar get() {
        return this;
    }

    public int getPercentage() {
        return percentage.get();
    }

    public WProgressBar setPercentage(int percentage) {
        if (this.percentage.get() != percentage && percentage >= 0 && percentage <= 100) {
            this.percentage.set(percentage);
            this.invalidate();
        }
        return get();
    }

}
