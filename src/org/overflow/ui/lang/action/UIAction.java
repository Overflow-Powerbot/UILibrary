package org.overflow.ui.lang.action;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Tom
 * Date: 17/06/14
 * Time: 20:20
 */
public abstract class UIAction {

    private AtomicLong updateRate;
    private AtomicBoolean repeat;

    public UIAction(long updateRate, boolean repeat) {
        this.updateRate = new AtomicLong(updateRate);
        this.repeat = new AtomicBoolean(repeat);
    }

    public final long getUpdateRate() {
        return updateRate.get();
    }

    public final UIAction setUpdateRate(long updateRate) {
        if (this.updateRate.get() != updateRate && updateRate > 50) {
            this.updateRate.set(updateRate);
        }
        return this;
    }

    public final boolean shouldRepeat() {
        return repeat.get();
    }

    public final UIAction setRepeat(boolean repeat) {
        this.repeat.set(repeat);
        return this;
    }

    public abstract void update();

}
