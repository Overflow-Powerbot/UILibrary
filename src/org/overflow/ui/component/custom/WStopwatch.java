package org.overflow.ui.component.custom;

import org.overflow.ui.component.WLabel;
import org.overflow.ui.lang.action.UIAction;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Author: Tom
 * Date: 19/06/14
 * Time: 02:35
 */
public class WStopwatch extends WLabel {

    private final AtomicLong time;

    public WStopwatch(final AtomicLong time) {
        this.time = time;
        this.submitAction(new UIAction(1000, true) {
            @Override
            public void update() {
                setText(WStopwatch.formatTime(System.currentTimeMillis() - WStopwatch.this.time.get()));
            }
        });
    }

    public WStopwatch() {
        this(new AtomicLong(System.currentTimeMillis()));
    }

    public AtomicLong getTime() {
        return time;
    }

    private static String formatTime(long runtime) {
        final StringBuilder sb = new StringBuilder("");
        runtime = Math.abs(runtime / 1000);
        if (runtime > 86400) {
            long temp = Math.round(Math.floor(runtime / 86400));
            sb.append(String.format("%d", temp)).append(temp > 0 ? " Days " : " Day ");
        }
        sb.append(String.format("%d", Math.round(Math.floor((runtime % 86400) / 3600)))).append(":");
        sb.append(String.format("%d", Math.round(Math.floor((runtime % 3600) / 60)))).append(":");
        sb.append(String.format("%d", runtime % 60));
        return sb.toString();
    }

}
